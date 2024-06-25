package com.himynameisilnano.hibernate.embeddable.model;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

/**
 * This class represents the T_DUMMY table for the embeddable domain.
 */
@Entity
public class EntityWithGenericEmbeddedId {

    @EmbeddedId
    private GenericEmbeddedId<ReferencedEntity> id;

    @Column(name = "DESCRIPTION")
    private String description;

}