package com.himynameisilnano.onetomanydoublesided;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "H_PERSON", indexes = {
        @Index(name = "H_PERSON_SSN_ID_IDX", columnList = "SSN")
})
public class PersonH {

    @Id
    @ManyToOne
    @JoinColumn(name = "PERS_ID")
    private PersonT tEntity;

    @NaturalId
    @Column(name = "SSN")
    private UUID ssn;

    /*@OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true)
    private Collection<PetT> pets;*/

    public PersonH(PersonT personT) {
        this.tEntity = personT;
        this.ssn = personT.getSsn();
        //this.pets = new ArrayList<>(personT.getPets());
    }

    public Long getId() {
        return tEntity.getId();
    }

    public UUID getSsn() {
        return ssn;
    }

    public Collection<PetT> getPets() {
        return tEntity.getPets()
                .stream()
                .toList();
    }

    public void addPet(PetT pet) {
        this.tEntity.addPet(pet);
    }

    protected PersonH() {
        // Do not remove. For JPA.
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PersonH person = (PersonH) o;
        return Objects.equals(ssn, person.ssn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ssn);
    }
}