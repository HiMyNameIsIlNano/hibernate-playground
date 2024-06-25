package com.himynameisilnano.hibernate.embeddable.model;

import jakarta.persistence.Embeddable;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

/**
 * This class represents the id for the embeddable domain.
 */
@Embeddable
public final class GenericEmbeddedIdOk<T> implements Serializable {

    @Serial
    private static final long serialVersionUID = 0L;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "REFERENCED_ENTITY_ID")
    private T referencedEntity;

    protected GenericEmbeddedIdOk() {
        // Do not remove. For JPA.
    }

    /**
     *
     */
    public GenericEmbeddedIdOk(T referencedEntity) {
        this.referencedEntity = referencedEntity;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "REFERENCED_ENTITY_ID")
    public T getReferencedEntity() {
        return referencedEntity;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (GenericEmbeddedIdOk) obj;
        return Objects.equals(this.referencedEntity, that.referencedEntity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(referencedEntity);
    }

    @Override
    public String toString() {
        return "GenericEmbeddedIdOk[" +
                "referencedEntity=" + referencedEntity + ']';
    }

}
