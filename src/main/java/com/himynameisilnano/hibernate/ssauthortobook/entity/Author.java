package com.himynameisilnano.hibernate.ssauthortobook.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotNull;

import java.util.Objects;
import java.util.Set;
import java.util.function.Supplier;
import java.util.logging.Logger;

@Entity
@Table(name = "AUTHOR")
public class Author {

    private static final Logger LOG = Logger.getLogger(Author.class.toString());

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
    private Set<Book> books;

    @Transient
    private Supplier<Set<Book>> lazyLoadBooks = () -> this.books;

    protected Author() {
        // Do not remove. For Jpa.
        new Author(null, () -> this.books);
    }

    private Author(String name, Supplier<Set<Book>> lazyLoadBooks) {
        this.name = name;
        this.lazyLoadBooks = lazyLoadBooks;
    }

    public static Author of(Author from) {
        return new Author(from.getName(), from::getBooks);
    }

    public static Author of(String name, Book book) {
        return new Author(name, () -> Set.of(book));
    }

    public static Author of(String name) {
        return new Author(name, Set::of);
    }

    @NotNull
    public Long getId() {
        return id;
    }

    @NotNull
    public String getName() {
        return name;
    }

    public Set<Book> getBooks() {
        books = lazyLoadBooks.get();
        return books;
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