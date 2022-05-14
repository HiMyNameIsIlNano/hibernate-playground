package com.himynameisilnano.hibernate.ssauthortobook.boundary;

import com.himynameisilnano.hibernate.ssauthortobook.entity.Author;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

import java.util.Optional;
import java.util.logging.Logger;

@Transactional
public class AuthorService {

    private final EntityManager entityManager;

    private static final Logger LOG = Logger.getLogger(AuthorService.class.toString());

    public AuthorService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Author save(Author author) {
        entityManager.persist(author);
        return author;
    }

    public Author update(Author author) {
        return entityManager.merge(author);
    }

    public Optional<Author> findAuthorById(long customerId) {
        Author Author = entityManager.find(Author.class, customerId);
        return Optional.ofNullable(Author);
    }

    public void saveNewCopy(Long id) {
        Optional<Author> author = findAuthorById(id);
        author.ifPresentOrElse(Author::getBooks, () -> {
            throw new IllegalArgumentException(String.format("No Author with ID: %d found", id));
        });
    }
}
