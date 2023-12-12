package com.himynameisilnano.onetomanysinglesided;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

import java.util.Objects;

/**
 * This class represents the PET table for the onetomanysinglesided domain.
 */
@Entity
@Table(name = "PET")
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PET_ID_GEN")
    @SequenceGenerator(name = "PET_ID_GEN")
    private Long id;

    @Column(name = "NAME")
    private String name;

    protected Pet() {
        // Do not remove. For JPA.
    }

    public Pet(String name, Person owner) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pet pet = (Pet) o;
        return Objects.equals(name, pet.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}