package com.example.emt_advanced.service.impl;

import com.example.emt_advanced.model.InverterProduct;
import com.example.emt_advanced.model.InverterProductSpecification;
import com.example.emt_advanced.model.dto.ProductDTO;
import com.example.emt_advanced.repository.InverterProductRepository;
import com.example.emt_advanced.repository.InverterSpecificationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class InverterProductServiceImplTest {

    @Mock
    private InverterProductRepository inverterProductRepository;

    @Mock
    private InverterSpecificationRepository inverterSpecificationRepository;

    private InverterProductServiceImpl service;

    @BeforeEach
    void setUp() {
        service = new InverterProductServiceImpl(
                inverterProductRepository,
                inverterSpecificationRepository,
                7000
        );
    }

    @Test
    void findByIdMapsProductAndSpecificationsToDto() {
        InverterProduct product = inverterProduct(
                1L,
                "NEPTUN",
                "Beko inverter 12000 BTU",
                28999,
                "https://example.com/inverter.png"
        );
        InverterProductSpecification firstSpec = inverterSpecification(21L, "Инвертер клима уред");
        InverterProductSpecification secondSpec = inverterSpecification(22L, "Енергетска класа A+++");

        when(inverterProductRepository.findById(1L)).thenReturn(Optional.of(product));
        when(inverterSpecificationRepository.findAllByProductId(1L)).thenReturn(List.of(firstSpec, secondSpec));

        ProductDTO dto = service.findById(1L);

        assertEquals(1L, dto.id());
        assertEquals("NEPTUN", dto.website());
        assertEquals("Beko inverter 12000 BTU", dto.productName());
        assertEquals(28999, dto.price());
        assertEquals("https://example.com/inverter.png", dto.ImageUrl());
        assertIterableEquals(List.of(firstSpec, secondSpec), dto.specificationList());
        verify(inverterProductRepository).findById(1L);
        verify(inverterSpecificationRepository).findAllByProductId(1L);
    }

    @Test
    void findSimilarUsesConfiguredPriceWindow() {
        List<InverterProduct> similarProducts = List.of(
                inverterProduct(2L, "NEPTUN", "Another inverter", 25999, "https://example.com/other-inverter.png")
        );

        when(inverterProductRepository.findProductsWithinPriceWindowOfChosen(1L, 7000))
                .thenReturn(similarProducts);

        List<? extends com.example.emt_advanced.model.Product> result = service.findSimilar(1L);

        assertSame(similarProducts, result);
        verify(inverterProductRepository).findProductsWithinPriceWindowOfChosen(1L, 7000);
    }

    private InverterProduct inverterProduct(Long id, String website, String productName, Integer priceMkd, String imageUrl) {
        InverterProduct product = new InverterProduct();
        product.setId(id);
        product.setWebsite(website);
        product.setProductName(productName);
        product.setPriceMkd(priceMkd);
        product.setImageUrl(imageUrl);
        product.setSpecifications(List.of());
        return product;
    }

    private InverterProductSpecification inverterSpecification(Long id, String specificationText) {
        InverterProductSpecification specification = new InverterProductSpecification();
        specification.setId(id);
        specification.setSpecificationText(specificationText);
        return specification;
    }
}
