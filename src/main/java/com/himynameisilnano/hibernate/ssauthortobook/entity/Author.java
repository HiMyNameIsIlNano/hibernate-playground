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
    private Set<Book> books;

    @Transient
    private Supplier<Set<Book>> lazy = () -> this.books;

    protected Author() {
        // Do not remove. For Jpa.
        new Author(null, () -> this.books);
    }

    private Author(String name, Supplier<Set<Book>> lazy) {
        this.name = name;
        this.lazy = lazy;
    }

    public static Author of(Author from) {
        return new Author(from.getName(), from::getBooks);
    }

    public static Author of(String name, Book book) {
        return Author.of(name, () -> Set.of(book));
    }

    public static Author of(String name, Supplier<Set<Book>> books) {
        return new Author(name, books);
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
        books = lazy.get();
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