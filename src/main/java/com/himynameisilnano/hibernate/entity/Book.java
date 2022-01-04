package com.himynameisilnano.hibernate.entity;

import org.hibernate.annotations.NaturalId;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "BOOK", indexes = {
        @Index(name = "book_name_idx", columnList = "name")
})
public class Book {

    @Id
    @SequenceGenerator(name = "BOOK_ID_GEN")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BOOK_ID_GEN")
    private Long id;

    @NaturalId
    private String name;

    public Book(String name) {
        this.name = name;
    }

    public Book() {
        // Do not remove. For JPA.
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

}