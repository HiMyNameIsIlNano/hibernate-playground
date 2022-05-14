package com.himynameisilnano.hibernate.ssauthortobook.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

import java.util.Objects;

@Entity
@Table(name = "BOOK", indexes = {
        @Index(name = "book_name_idx", columnList = "name")
})
public class Book {

    @Id
    @SequenceGenerator(name = "BOOK_ID_GEN")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BOOK_ID_GEN")
    private Long id;

    private String isbn;

    private String name;

    protected Book() {
        // Do not remove. For JPA.
    }

    public Book(String isbn, String name) {
        this.isbn = isbn;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return isbn.equals(book.isbn) && name.equals(book.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(isbn, name);
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", isbn='" + isbn + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}