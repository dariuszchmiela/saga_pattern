package com.dch.saga_pattern.service;

import com.dch.saga_pattern.model.Product;
import com.dch.saga_pattern.model.ProductDto;
import com.dch.saga_pattern.storage.entity.ProductEntity;
import com.dch.saga_pattern.storage.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final SagaOrchestrator<ProductDto, ProductEntity> sagaOrchestrator;

    public ProductService(ProductRepository productRepository, CreateProductSagaStep createProductSagaStep) {
        this.productRepository = productRepository;
        this.sagaOrchestrator = new SagaOrchestrator<>();
        this.sagaOrchestrator.addStep(createProductSagaStep);
    }

    public Optional<Product> getProductById(UUID productId) {
        Objects.requireNonNull(productId, "ProductId cannot be null");
        return productRepository.findByProductId(productId).map(this::toProduct);
    }

    public Product createProduct(ProductDto dto) {
        Objects.requireNonNull(dto, "ProductDto cannot be null");
        return toProduct(sagaOrchestrator.execute(dto));
    }

    @Transactional
    public void deleteProduct(UUID productId) {
        Objects.requireNonNull(productId, "ProductId cannot be null");
        productRepository.deleteByProductId(productId);
    }

    private Product toProduct(ProductEntity entity) {
        return new Product(entity.getId(), entity.getProductId(), entity.getName(), entity.getPrice(), entity.getType());
    }
}