package com.himynameisilnano.onetomanydoublesided;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.util.Objects;

/**
 * This class represents the PET table for the onetomanysinglesided domain.
 */
@Entity
@Table(name = "H_PET")
public class PetH {

    @Id
    @ManyToOne
    @JoinColumn(name = "PET_ID")
    private PetT pet;

    @Column(name = "NAME")
    private String name;

    /*@ManyToOne
    @JoinColumn(name = "DUMMY_ID")
    private PersonT owner;*/

    protected PetH() {
        // Do not remove. For JPA.
    }

    public PetH(PetT petT) {
        this.name = petT.getName();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PetH petH = (PetH) o;
        return Objects.equals(pet, petH.pet) && Objects.equals(name, petH.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pet, name);
    }
}