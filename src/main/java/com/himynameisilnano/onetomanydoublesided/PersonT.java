package com.himynameisilnano.onetomanydoublesided;

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
@Table(name = "T_PERSON", indexes = {
        @Index(name = "T_PERSON_SSN_ID_IDX", columnList = "SSN")
})
public class PersonT {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "T_PERSON_ID_GEN")
    @SequenceGenerator(name = "T_PERSON_ID_GEN")
    @Column(name = "PERS_ID")
    private Long id;

    @NaturalId
    @Column(name = "SSN")
    private UUID ssn;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true)
    private Collection<PetT> pets;

    public PersonT(UUID ssn) {
        this.ssn = ssn;
        this.pets = new ArrayList<>(0);
    }

    public Long getId() {
        return id;
    }

    public UUID getSsn() {
        return ssn;
    }

    public Collection<PetT> getPets() {
        return pets.stream()
                .toList();
    }

    public void addPet(PetT pet) {
        pet.setOwner(this);
        this.pets.add(pet);
    }

    protected PersonT() {
        // Do not remove. For JPA.
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PersonT person = (PersonT) o;
        return Objects.equals(ssn, person.ssn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ssn);
    }
}