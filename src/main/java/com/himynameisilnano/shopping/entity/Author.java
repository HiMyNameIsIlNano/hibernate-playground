package com.himynameisilnano.shopping.entity;

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
import java.util.List;

/**
 * @author imssbora
 */
@Entity
@Table(name = "CUSTOMER")
public class Author {

    @Id
    @GeneratedValue
    @Column(name = "ID")
    @NotNull
    private Long id;

    @Column(name = "NAME")
    @NotNull
    private String name;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "AUTHOR_ID")
    private List<Book> books;

    public Author() {
        // Do not remove. For Construction DI.
        books = new ArrayList<>(0);
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

    public List<Book> getBooks() {
        return books;
    }

    public void addBook(Book book) {
        this.books.add(book);
    }

    public void addBooks(List<Book> books) {
        this.books.addAll(books);
    }

    public void deleteBook(Book book) {
        this.books.remove(book);
    }

    public void deleteAllBooks() {
        this.books = new ArrayList<>(0);
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}