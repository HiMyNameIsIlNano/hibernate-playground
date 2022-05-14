package com.himynameisilnano.hibernate.ssreceipttocustomer.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import org.hibernate.annotations.NaturalId;

import java.util.Objects;

@Entity
@Table(name = "CUSTOMER", indexes = {
        @Index(name = "custromer_dummy_idx", columnList = "naturalId")
})
public class Customer {

    @Id
    @SequenceGenerator(name = "CUSTOMER_ID_GEN")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CUSTOMER_ID_GEN")
    private Long id;

    @NaturalId
    private String naturalId;

    @Column
    private String name;

    protected Customer() {

    }

    public Customer(String naturalId, String name) {
        this.naturalId = naturalId;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getNaturalId() {
        return naturalId;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return Objects.equals(id, customer.id) && Objects.equals(naturalId, customer.naturalId) && Objects.equals(name, customer.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, naturalId, name);
    }
}