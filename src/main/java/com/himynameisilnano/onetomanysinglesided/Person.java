package com.himynameisilnano.onetomanysinglesided;

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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.UUID;

/**
 * This class represents the PERSON table for the onetomanysinglesided domain.
 */
@Entity
@Table(name = "PERSON", indexes = {
        @Index(name = "PERSON_SSN_ID_IDX", columnList = "SSN")
})
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PERSON_ID_GEN")
    @SequenceGenerator(name = "PERSON_ID_GEN")
    private Long id;

    @NaturalId
    @Column(name = "SSN")
    private UUID ssn;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private Collection<Pet> pets;

    public Person(UUID ssn) {
        this.ssn = ssn;
        this.pets = new ArrayList<>(0);
    }

    public Long getId() {
        return id;
    }

    public UUID getSsn() {
        return ssn;
    }

    public Collection<Pet> getPets() {
        return pets.stream()
                .toList();
    }

    public void addPet(Pet pet) {
        this.pets.add(pet);
    }

    protected Person() {
        // Do not remove. For JPA.
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(ssn, person.ssn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ssn);
    }
}