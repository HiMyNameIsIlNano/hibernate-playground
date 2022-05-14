package com.himynameisilnano.hibernate.ssreceipttocustomer.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import org.hibernate.annotations.NaturalId;

import java.util.Objects;

@Entity
@Table(name = "RECEIPT", indexes = {
        @Index(name = "receipt_dummy_idx", columnList = "naturalId")
})
public class Receipt {

    @Id
    @SequenceGenerator(name = "RECEIPT_ID_GEN")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RECEIPT_ID_GEN")
    private Long id;

    @NaturalId
    private String naturalId;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    private Customer customer;

    protected Receipt() {
    }

    public Receipt(String naturalId, Customer customer) {
        this.naturalId = naturalId;
        this.customer = customer;
    }

    public Long getId() {
        return id;
    }

    public String getNaturalId() {
        return naturalId;
    }

    public Customer getCustomer() {
        return customer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Receipt receipt = (Receipt) o;
        return Objects.equals(id, receipt.id) && Objects.equals(naturalId, receipt.naturalId) && Objects.equals(customer, receipt.customer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, naturalId, customer);
    }
}