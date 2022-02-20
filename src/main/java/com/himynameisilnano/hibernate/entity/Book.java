package com.himynameisilnano.hibernate.entity;

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
@Table(name = "Book", indexes = {
        @Index(name = "book_name_idx", columnList = "name")
})
public class Book {

    @Id
    @SequenceGenerator(name = "BOOK_ID_GEN")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BOOK_ID_GEN")
    private Long id;

    @NaturalId
    private String name;

    @ManyToOne
    @JoinColumn(name = "ASD_ID") // This is not necessary, but it is nice to be able to define a decent name for the foreign key
    private Author author;

    protected Book() {
        // Do not remove. For JPA.
    }

    public Book(String name, Author author) {
        this.name = name;
        this.author = author;
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