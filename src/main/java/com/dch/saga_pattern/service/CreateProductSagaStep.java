package com.dch.saga_pattern.service;

import com.dch.saga_pattern.exception.ProductAlreadyExistException;
import com.dch.saga_pattern.model.ProductDto;
import com.dch.saga_pattern.storage.entity.ProductEntity;
import com.dch.saga_pattern.storage.repository.ProductRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class CreateProductSagaStep implements SagaStep<ProductDto, ProductEntity> {
    private final ProductRepository productRepository;

    public CreateProductSagaStep(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public ProductEntity execute(ProductDto data) {
        if (productRepository.findByProductId(data.getProductId()).isPresent()) {
            throw new ProductAlreadyExistException("Product with productId " + data.getProductId() + " already exists but did not.");
        }
        ProductEntity productEntity = new ProductEntity(data.getProductId(), data.getName(), data.getPrice(), data.getType());
        return productRepository.save(productEntity);
    }

    @Override
    @Transactional
    public void compensate(ProductDto data) {
        productRepository.deleteByProductId(data.getProductId());
    }
}