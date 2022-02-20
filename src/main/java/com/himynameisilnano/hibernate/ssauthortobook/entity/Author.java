package com.himynameisilnano.hibernate.ssauthortobook.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "AUTHOR")
public class Author {

    @Id
    @GeneratedValue
    @Column(name = "ID")
    @NotNull
    private Long id;

    @Column(name = "NAME")
    @NotNull
    private String name;

    /**
     * The join column will be created in the {@link Book} class.
     * Mapped by is only necessary when the relationship is bidirectional.
     */
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "AUTHOR")
    //private List<Book> books;
    private Set<Book> books;

    public Author() {
        // Do not remove. For Construction DI.
        books = new HashSet<>(0);
    }

    public Author(String name) {
        this();
        this.name = name;
    }

    @NotNull
    public Long getId() {
        return id;
    }

    @NotNull
    public String getName() {
        return name;
    }

    public void setName(@NotNull String name) {
        this.name = name;
    }

    public Set<Book> getBooks() {
        return books;
    }

    public void addBook(Book book) {
        this.books.add(book);
    }

    public void addBooks(Set<Book> books) {
        this.books.addAll(books);
    }

    public void deleteBook(Book book) {
        this.books.remove(book);
    }

    public void deleteAllBooks() {
        this.books = new HashSet<>(0);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Author author = (Author) o;
        return name.equals(author.name) && books.equals(author.books);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, books);
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
