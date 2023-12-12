package com.himynameisilnano.onetomanydoublesided;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

import java.util.Objects;

/**
 * This class represents the PET table for the onetomanysinglesided domain.
 */
@Entity
@Table(name = "T_PET")
public class PetT {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PET_ID_GEN")
    @SequenceGenerator(name = "PET_ID_GEN")
    @Column(name = "PET_ID")
    private Long id;

    @Column(name = "NAME")
    private String name;

    @ManyToOne
    @JoinColumn(name = "PERS_ID")
    private PersonT owner;

    protected PetT() {
        // Do not remove. For JPA.
    }

    public PetT(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PetT pet = (PetT) o;
        return Objects.equals(name, pet.name) && Objects.equals(owner, pet.owner);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, owner);
    }

    public void setOwner(PersonT owner) {
        this.owner = owner;
    }
}