package com.himynameisilnano.shopping.entity;

import org.hibernate.annotations.NaturalId;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "BOOK", indexes = {
        @Index(name = "name_idx", columnList = "name")
})
public class Book {

    @Id
    @SequenceGenerator(name = "BOOK_ID_GEN")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BOOK_ID_GEN")
    private Long id;

    @NaturalId
    private String name;

    @ManyToOne
    @JoinColumn(name = "ASD_ID")
    private Author author;

    public Book(String name, Author author) {
        this.name = name;
        this.author = author;
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

    public Author getAuthor() {
        return author;
    }
}