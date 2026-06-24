package com.example.emt_advanced.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verifyNoInteractions;

@ExtendWith(MockitoExtension.class)
class GeminiServiceImplTest {

    @Mock
    private InverterProductServiceImpl inverterProductService;

    @Mock
    private FriziderProductServiceImpl friziderProductService;

    @Test
    void returnsEmptyListWhenApiKeyIsMissing() {
        GeminiServiceImpl service = new GeminiServiceImpl(
                inverterProductService,
                friziderProductService,
                "   ",
                "gemini-test-model"
        );

        List<?> result = service.findSimilarProducts(1L, "frizideri");

        assertEquals(List.of(), result);
        verifyNoInteractions(inverterProductService, friziderProductService);
    }
}
