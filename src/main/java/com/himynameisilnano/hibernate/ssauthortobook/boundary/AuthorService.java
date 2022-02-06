package com.himynameisilnano.hibernate.ssauthortobook.boundary;

import com.himynameisilnano.hibernate.ssauthortobook.entity.Author;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.util.Optional;

@Transactional
public class AuthorService {

    private final EntityManager entityManager;

    public AuthorService(@NotNull EntityManager entityManager) {
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
