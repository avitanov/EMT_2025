package com.example.emt_advanced.service.impl;

import com.example.emt_advanced.model.FriziderProduct;
import com.example.emt_advanced.model.FriziderProductSpecification;
import com.example.emt_advanced.model.dto.ProductDTO;
import com.example.emt_advanced.repository.FriziderProductRepository;
import com.example.emt_advanced.repository.FriziderSpecificationRepository;
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
class FriziderProductServiceImplTest {

    @Mock
    private FriziderProductRepository friziderProductRepository;

    @Mock
    private FriziderSpecificationRepository friziderSpecificationRepository;

    private FriziderProductServiceImpl service;

    @BeforeEach
    void setUp() {
        service = new FriziderProductServiceImpl(
                friziderProductRepository,
                friziderSpecificationRepository,
                5000
        );
    }

    @Test
    void findByIdMapsProductAndSpecificationsToDto() {
        FriziderProduct product = friziderProduct(1L, "NEPTUN", "BEKO RS 9051 PN", 9499, "https://example.com/frizider.png");
        FriziderProductSpecification firstSpec = friziderSpecification(11L, "Фрижидер");
        FriziderProductSpecification secondSpec = friziderSpecification(12L, "MinFrost");

        when(friziderProductRepository.findById(1L)).thenReturn(Optional.of(product));
        when(friziderSpecificationRepository.findAllByProductId(1L)).thenReturn(List.of(firstSpec, secondSpec));

        ProductDTO dto = service.findById(1L);

        assertEquals(1L, dto.id());
        assertEquals("NEPTUN", dto.website());
        assertEquals("BEKO RS 9051 PN", dto.productName());
        assertEquals(9499, dto.price());
        assertEquals("https://example.com/frizider.png", dto.ImageUrl());
        assertIterableEquals(List.of(firstSpec, secondSpec), dto.specificationList());
        verify(friziderProductRepository).findById(1L);
        verify(friziderSpecificationRepository).findAllByProductId(1L);
    }

    @Test
    void findSimilarUsesConfiguredPriceWindow() {
        List<FriziderProduct> similarProducts = List.of(
                friziderProduct(2L, "NEPTUN", "BEKO LS9052WN", 8499, "https://example.com/other.png")
        );

        when(friziderProductRepository.findProductsWithinPriceWindowOfChosen(1L, 5000))
                .thenReturn(similarProducts);

        List<? extends com.example.emt_advanced.model.Product> result = service.findSimilar(1L);

        assertSame(similarProducts, result);
        verify(friziderProductRepository).findProductsWithinPriceWindowOfChosen(1L, 5000);
    }

    private FriziderProduct friziderProduct(Long id, String website, String productName, Integer priceMkd, String imageUrl) {
        FriziderProduct product = new FriziderProduct();
        product.setId(id);
        product.setWebsite(website);
        product.setProductName(productName);
        product.setPriceMkd(priceMkd);
        product.setImageUrl(imageUrl);
        product.setSpecifications(List.of());
        return product;
    }

    private FriziderProductSpecification friziderSpecification(Long id, String specificationText) {
        FriziderProductSpecification specification = new FriziderProductSpecification();
        specification.setId(id);
        specification.setSpecificationText(specificationText);
        return specification;
    }
}
