package com.example.emt_advanced.web;

import com.example.emt_advanced.model.FriziderProduct;
import com.example.emt_advanced.model.FriziderProductSpecification;
import com.example.emt_advanced.model.InverterProduct;
import com.example.emt_advanced.model.dto.ProductDTO;
import com.example.emt_advanced.service.GeminiService;
import com.example.emt_advanced.service.impl.FriziderProductServiceImpl;
import com.example.emt_advanced.service.impl.InverterProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest {

    @Mock
    private FriziderProductServiceImpl friziderProductService;

    @Mock
    private InverterProductServiceImpl inverterProductService;

    @Mock
    private GeminiService geminiService;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        ProductController controller = new ProductController(friziderProductService, inverterProductService, geminiService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void getFriziderByIdReturnsDtoContract() throws Exception {
        FriziderProductSpecification specification = new FriziderProductSpecification();
        specification.setId(11L);
        specification.setSpecificationText("Фрижидер");

        ProductDTO dto = new ProductDTO(
                1L,
                "NEPTUN",
                "BEKO RS 9051 PN",
                9499,
                "https://example.com/frizider.png",
                List.of(specification)
        );

        when(friziderProductService.findById(1L)).thenReturn(dto);

        mockMvc.perform(get("/api/products/frizideri/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.website").value("NEPTUN"))
                .andExpect(jsonPath("$.productName").value("BEKO RS 9051 PN"))
                .andExpect(jsonPath("$.price").value(9499))
                .andExpect(jsonPath("$.ImageUrl").value("https://example.com/frizider.png"))
                .andExpect(jsonPath("$.specificationList[0].specificationText").value("Фрижидер"));
    }

    @Test
    void getAllInverteriReturnsSerializedProducts() throws Exception {
        InverterProduct product = new InverterProduct();
        product.setId(2L);
        product.setWebsite("NEPTUN");
        product.setProductName("Beko inverter");
        product.setPriceMkd(28999);
        product.setImageUrl("https://example.com/inverter.png");
        product.setSpecifications(List.of());

        when(inverterProductService.findAll()).thenReturn(List.of(product));

        mockMvc.perform(get("/api/products/inverteri"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(2))
                .andExpect(jsonPath("$[0].priceMkd").value(28999))
                .andExpect(jsonPath("$[0].imageUrl").value("https://example.com/inverter.png"));
    }

    @Test
    void findSimilarFrizideriDelegatesToGeminiWithExpectedCategory() throws Exception {
        FriziderProduct similarProduct = new FriziderProduct();
        similarProduct.setId(3L);
        similarProduct.setWebsite("RIVERSOFT");
        similarProduct.setProductName("Similar fridge");
        similarProduct.setPriceMkd(7999);
        similarProduct.setImageUrl("https://example.com/similar-fridge.png");
        similarProduct.setSpecifications(List.of());

        when(geminiService.findSimilarProducts(1L, "frizideri")).thenReturn(List.of(similarProduct));

        mockMvc.perform(get("/api/products/frizideri/1/findSimilar"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(3))
                .andExpect(jsonPath("$[0].productName").value("Similar fridge"));

        verify(geminiService).findSimilarProducts(1L, "frizideri");
    }
}
