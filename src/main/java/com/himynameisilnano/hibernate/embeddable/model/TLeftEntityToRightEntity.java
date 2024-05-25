package com.himynameisilnano.hibernate.embeddable.model;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import org.hibernate.annotations.NaturalId;

/**
 * This class represents the T_DUMMY table for the embeddable domain.
 */
@Entity
@Table(name = "T_DUMMY", indexes = {
        @Index(name = "DUMMY_NATURAL_ID_IDX", columnList = "NATURAL_ID")
})
public class TLeftEntityToRightEntity {

    @EmbeddedId
    private LeftToRightId<LeftEntity, RightEntity> id;

    @NaturalId
    @Column(name = "NATURAL_ID")
    private String naturalId;

}