package com.example.emt_advanced.service.impl;
import com.example.emt_advanced.model.Product;
import com.example.emt_advanced.model.dto.ProductDTO;
import com.example.emt_advanced.service.GeminiService;
import com.google.genai.Client;
import com.google.genai.types.Content;
import com.google.genai.types.GenerateContentConfig;
import com.google.genai.types.GenerateContentResponse;
import com.google.genai.types.Part;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class GeminiServiceImpl implements GeminiService {
    private final Client geminiClient;
    private final FriziderProductServiceImpl friziderProductService;
    private final InverterProductServiceImpl inverterProductService;
    private final String modelName;

    public GeminiServiceImpl(InverterProductServiceImpl inverterProductService,
                             FriziderProductServiceImpl friziderProductService,
                             @Value("${gemini.api.key}") String apiKey,
                             @Value("${gemini.model}") String modelName) {
        this.friziderProductService = friziderProductService;
        this.inverterProductService = inverterProductService;
        this.modelName = modelName;
        this.geminiClient = StringUtils.hasText(apiKey)
                ? Client.builder().apiKey(apiKey.trim()).build()
                : null;
    }

    @Override
    public List<Product> findSimilarProducts(Long chosenProductId, String category) {
        if (geminiClient == null) {
            System.err.println("GEMINI_API_KEY is not configured; returning no similar products.");
            return Collections.emptyList();
        }

        var service = "frizideri".equals(category)
                ? friziderProductService
                : inverterProductService;
        ProductDTO chosen = service.findById(chosenProductId);
        List<? extends Product> allProducts = service.findSimilar(chosenProductId);
        StringBuilder sb = new StringBuilder();
        sb.append("Given the following chosen product:\n");
        sb.append("- Product ID: ").append(chosen.id()).append("\n");
        sb.append("  Name: ").append(chosen.productName()).append("\n");
        sb.append("  Characteristics: ").append(chosen.specificationList()).append("\n\n");
        sb.append("And a list of available products with their characteristics:\n");
        for (Product p : allProducts) {
            var dto = service.findById(p.getId());
            sb.append("- Product ID: ").append(dto.id()).append("\n");
            sb.append("  Name: ").append(dto.productName()).append("\n");
            sb.append("  Characteristics: ").append(dto.specificationList()).append("\n");
        }
        sb.append("\nFrom the available products, identify ONLY the products that are similar in characteristics to the chosen product. Return only a list of product ids except the id of the chosen product, e.g. [1,2,3]. If no similar products are found, return an empty list [].\n");
        String prompt = sb.toString();

        List<Product> results = new ArrayList<>();
        try {
            Content content = Content.fromParts(Part.fromText(prompt));
            GenerateContentConfig config = GenerateContentConfig.builder()
                    .responseMimeType("application/json")
                    .build();
            GenerateContentResponse resp = geminiClient.models.generateContent(
                    modelName, content, config
            );
            String text = resp.text().trim();
            if (text.startsWith("\"") && text.endsWith("\"")) {
                text = text.substring(1, text.length() - 1).trim();
            }
            List<Long> ids;
            if (text.startsWith("[") && text.endsWith("]")) {
                String body = text.substring(1, text.length() - 1).trim();
                ids = body.isEmpty()
                        ? Collections.emptyList()
                        : Arrays.stream(body.split(","))
                        .map(String::trim)
                        .map(Long::valueOf)
                        .collect(Collectors.toList());
            } else {
                System.err.println("WARNING: unexpected format from Gemini: " + text);
                ids = Collections.emptyList();
            }
            ids.remove(chosenProductId);
            for (Long id : ids) {
                try {
                    results.add(service.findProduct(id));
                } catch (Exception e) {
                    System.err.println("Skipping missing product id=" + id + ": " + e.getMessage());
                }
            }
        } catch (Exception e) {
            System.err.println("Error calling Gemini API: " + e.getMessage());
        }
        return results;
    }
}
