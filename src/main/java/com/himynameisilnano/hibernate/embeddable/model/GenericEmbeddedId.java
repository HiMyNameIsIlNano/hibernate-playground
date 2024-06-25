package com.himynameisilnano.hibernate.embeddable.model;

import jakarta.persistence.Embeddable;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.io.Serializable;

/**
 * This class represents the id for the embeddable domain.
 */
@Embeddable
public record GenericEmbeddedId<T>(@ManyToOne(fetch = FetchType.EAGER)
                                   @JoinColumn(name = "REFERENCED_ENTITY_ID")
                                   T referencedEntity) implements Serializable {
}
