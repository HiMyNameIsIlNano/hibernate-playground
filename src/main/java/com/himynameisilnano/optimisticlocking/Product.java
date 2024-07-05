package com.himynameisilnano.optimisticlocking;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Version;

/**
 * This class represents the PRODUCT table for the optimisticlocking domain.
 */
@Entity
@Table(name = "PRODUCT")
public class Product {

    @Id
    private Long id;

    @Version
    private Long version;

    @Column(name = "DESCRIPTION")
    private String description;

    protected Product() {
        // Do not remove. For JPA.
    }

    public Product(Long id, String description) {
        this.id = id;
        this.description = description;
    }

    public Long getVersion() {
        return version;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}