package com.himynameisilnano.hibernate.embeddable.model;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;

/**
 * This class represents the T_DUMMY table for the embeddable domain.
 */
@Entity
public class MainEntity {

    @EmbeddedId
    private GenericEmbeddedId<ReferencedEntity> id;

    @Column(name = "DESCRIPTION")
    private String description;

}