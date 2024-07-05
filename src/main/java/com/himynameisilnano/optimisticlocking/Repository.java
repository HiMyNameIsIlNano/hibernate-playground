package com.himynameisilnano.optimisticlocking;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Version;

import java.util.Objects;

/**
 * This class represents the FOO table for the optimisticlocking domain.
 */
@Entity
@Table(name = "REPOSITORY")
public class Repository {

    @Id
    private Long id;

    @Version
    private Long version;

    protected Repository() {
        this(1L);
    }

    protected Repository(Long id) {
        this.id = id;
    }

    public Long getVersion() {
        return version;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Repository that)) return false;
        return Objects.equals(id, that.id) && Objects.equals(version, that.version);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, version);
    }
}