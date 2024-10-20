package com.dch.saga_pattern.controller;

import com.dch.saga_pattern.model.ProductDto;
import com.dch.saga_pattern.model.ProductType;
import com.dch.saga_pattern.storage.entity.ProductEntity;
import com.dch.saga_pattern.storage.repository.ProductRepository;
import com.dch.saga_pattern.util.ProductTestDataUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ProductControllerTest {
    private static final String BASE_PATH = "/api/products";
    public static final String PRODUCT_A = "Product A";
    public static final BigDecimal PRICE = BigDecimal.TEN;
    private static final ProductDto PRODUCT_DTO = new ProductDto(PRODUCT_A, PRICE, ProductType.A);
    @Autowired
    private MockMvc mvc;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ObjectMapper objectMapper;

    @AfterEach
    void cleanUp() {
        productRepository.deleteAll();
    }

    @Test
    void createProduct() throws Exception {
        // given & when & then
        mvc.perform(post(BASE_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(PRODUCT_DTO)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value(PRODUCT_A))
                .andExpect(jsonPath("$.price").value(PRICE.intValue()))
                .andExpect(jsonPath("$.type").value(ProductType.A.name()))
                .andExpect(jsonPath("$.productId").isNotEmpty());
    }

    @Test
    void deleteProduct() throws Exception {
        // given
        ProductEntity saved = productRepository.save(ProductTestDataUtil.createProduct(PRODUCT_A, PRICE, ProductType.A));

        // when & then
        mvc.perform(delete(BASE_PATH + "/{productId}", saved.getProductId()))
                .andDo(print())
                .andExpect(status().isNoContent());
    }
}