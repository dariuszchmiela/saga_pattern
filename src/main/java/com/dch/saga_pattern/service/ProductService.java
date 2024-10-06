package com.dch.saga_pattern.service;

import com.dch.saga_pattern.exception.ProductAlreadyExistException;
import com.dch.saga_pattern.model.CreateProductDto;
import com.dch.saga_pattern.model.Product;
import com.dch.saga_pattern.storage.entity.ProductEntity;
import com.dch.saga_pattern.storage.repository.ProductRepository;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    public Product createProduct(CreateProductDto dto) {
        if (repository.findByProductId(dto.getProductId()).isPresent()) {
            throw new ProductAlreadyExistException("Product with productId " + dto.getProductId() + " already exists");
        }
        return toDto(repository.save(toEntity(dto)));
    }

    private ProductEntity toEntity(CreateProductDto dto) {
        return new ProductEntity()
                .setProductId(dto.getProductId())
                .setName(dto.getName())
                .setPrice(dto.getPrice())
                .setProductType(dto.getProductType());
    }

    private Product toDto(ProductEntity entity) {
        return new Product(entity.getId(), entity.getProductId(), entity.getName(), entity.getPrice(), entity.getProductType());
    }
}
