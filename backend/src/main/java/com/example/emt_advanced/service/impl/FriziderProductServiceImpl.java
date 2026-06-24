package com.example.emt_advanced.service.impl;

import com.example.emt_advanced.model.FriziderProduct;
import com.example.emt_advanced.model.FriziderProductSpecification;
import com.example.emt_advanced.model.Product;
import com.example.emt_advanced.model.dto.ProductDTO;
import com.example.emt_advanced.repository.FriziderProductRepository;
import com.example.emt_advanced.repository.FriziderSpecificationRepository;
import com.example.emt_advanced.service.ProductService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FriziderProductServiceImpl implements ProductService {
    private final FriziderProductRepository friziderProductRepository;
    private final FriziderSpecificationRepository friziderSpecificationRepository;
    private final Integer similarProductsPriceWindow;

    public FriziderProductServiceImpl(FriziderProductRepository friziderProductRepository,
                                      FriziderSpecificationRepository friziderSpecificationRepository,
                                      @Value("${app.similarity.price-window}") Integer similarProductsPriceWindow) {
        this.friziderProductRepository = friziderProductRepository;
        this.friziderSpecificationRepository = friziderSpecificationRepository;
        this.similarProductsPriceWindow = similarProductsPriceWindow;
    }

    @Override
    public List<FriziderProduct> findAll() {
        return friziderProductRepository.findAll();
    }

    @Override
    public ProductDTO findById(Long id) {
        FriziderProduct product=friziderProductRepository.findById(id).get();
        List<FriziderProductSpecification> specs=friziderSpecificationRepository.findAllByProductId(id);

        return ProductDTO.from(product,specs);
    }

    @Override
    public FriziderProduct findProduct(Long id) {
        return friziderProductRepository.findById(id).get();
    }

    @Override
    public List<? extends Product> findSimilar(Long id) {
        return friziderProductRepository.findProductsWithinPriceWindowOfChosen(id, similarProductsPriceWindow);
    }
}
