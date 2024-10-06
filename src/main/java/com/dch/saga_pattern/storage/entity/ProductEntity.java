package com.dch.saga_pattern.storage.entity;

import com.dch.saga_pattern.model.ProductType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Version;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Entity
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private UUID productId;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private BigDecimal price;
    @Enumerated(EnumType.STRING)
    private ProductType productType;
    @ManyToMany(mappedBy = "products", fetch = FetchType.LAZY)
    private Set<OrderEntity> orders;
    @Version
    @Column(nullable = false)
    private Long version;
    @Column(name = "created_at", updatable = false, nullable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;
    @Column(name = "updated_at", nullable = false)
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public ProductEntity() {
    }

    public ProductEntity(Long id, UUID productId, String name, BigDecimal price, ProductType productType, Set<OrderEntity> orders) {
        this.id = id;
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.productType = productType;
        this.orders = orders;
    }

    public Long getId() {
        return id;
    }

    public UUID getProductId() {
        return productId;
    }

    public ProductEntity setProductId(UUID productId) {
        this.productId = productId;
        return this;
    }

    public String getName() {
        return name;
    }

    public ProductEntity setName(String name) {
        this.name = name;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public ProductEntity setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public ProductType getProductType() {
        return productType;
    }

    public ProductEntity setProductType(ProductType productType) {
        this.productType = productType;
        return this;
    }

    public Set<OrderEntity> getOrders() {
        return orders;
    }

    public ProductEntity setOrders(Set<OrderEntity> orders) {
        this.orders = orders;
        return this;
    }

    public Long getVersion() {
        return version;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}