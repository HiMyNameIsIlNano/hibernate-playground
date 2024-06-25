package com.himynameisilnano.hibernate.embeddable.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;

import java.util.Collection;

/**
 * This class represents the REFERENCED_ENTITY table for the embeddable domain.
 */
@Entity
public class ReferencedEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "REFERENCED_ENTITY_ID_GEN")
    @SequenceGenerator(name = "REFERENCED_ENTITY_ID_GEN")
    @Column(name = "REFERENCED_ENTITY_ID")
    private Long id;

    @OneToMany(mappedBy = "id.referencedEntity", cascade = CascadeType.ALL)
    private Collection<MainEntity> entities;

}