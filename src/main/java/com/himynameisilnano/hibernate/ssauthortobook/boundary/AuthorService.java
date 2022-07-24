package com.himynameisilnano.hibernate.ssauthortobook.boundary;

import com.himynameisilnano.hibernate.ssauthortobook.entity.Author;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.util.Optional;

@Transactional
public class AuthorService {

    private final EntityManager entityManager;

    public AuthorService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Author save(@Valid Author author) {
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
