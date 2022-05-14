package com.himynameisilnano.hibernate.boundary;

import com.himynameisilnano.hibernate.entity.Author;
import jakarta.persistence.EntityManager;

import javax.validation.constraints.NotNull;
import java.util.Optional;

public class CustomerService {

    private final EntityManager entityManager;

    public CustomerService(@NotNull EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Author save(@NotNull Author author) {
        entityManager.persist(author);
        return author;
    }

    public Author update(@NotNull Author author) {
        entityManager.merge(author);
        return author;
    }

    public Optional<Author> findCustomerById(int customerId) {
        Author Author = entityManager.find(Author.class, customerId);
        return Optional.ofNullable(Author);
    }
}
