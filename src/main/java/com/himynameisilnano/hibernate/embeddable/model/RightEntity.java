package com.himynameisilnano.hibernate.embeddable.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import org.hibernate.annotations.NaturalId;

import java.util.Collection;

/**
 * This class represents the RIGHT_ENTITY table for the embeddable domain.
 */
@Entity
@Table(name = "RIGHT_ENTITY", indexes = {
        @Index(name = "RIGHT_ENTITY_NATURAL_ID_IDX", columnList = "NATURAL_ID")
})
public class RightEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RIGHT_ENTITY_ID_GEN")
    @SequenceGenerator(name = "RIGHT_ENTITY_ID_GEN")
    @Column(name = "RIGHT_ENTITY_ID")
    private Long id;

    @NaturalId
    @Column(name = "NATURAL_ID")
    private String naturalId;

    @OneToMany(mappedBy = "id.right", cascade = CascadeType.ALL)
    private Collection<TLeftEntityToRightEntity> fromLeftToRights;

}