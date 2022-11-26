package com.himynameisilnano.hibernate.ssauthortobook.boundary;

import com.himynameisilnano.hibernate.JdkLoggingConfigReaderHelper;
import com.himynameisilnano.hibernate.JpaTransactionManagerTestSupplier;
import com.himynameisilnano.hibernate.ssauthortobook.entity.Author;
import com.himynameisilnano.hibernate.ssauthortobook.entity.Book;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Optional;
import java.util.Set;
import java.util.logging.Logger;

import static org.hibernate.testing.transaction.TransactionUtil.doInJPA;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(JdkLoggingConfigReaderHelper.class)
class AuthorServiceTest {

    private static final Logger LOG = Logger.getLogger(AuthorServiceTest.class.toString());

    @Test
    void save_author_shouldBeFoundInDb() {
        JpaTransactionManagerTestSupplier supplier = new JpaTransactionManagerTestSupplier("h2-dev");
        Author joe = doInJPA(supplier::getFactory, entityManager -> {
            AuthorService testSubject = new AuthorService(entityManager);

            Author _author = Author.of("Joe");
            testSubject.save(_author);

            return _author;
        });

        assertNotNull(joe.getId());
        assertEquals("Joe", joe.getName());
    }

    @Test
    void save_author_shouldHaveOnlyOneBook() {
        JpaTransactionManagerTestSupplier supplier = new JpaTransactionManagerTestSupplier("h2-dev");
        Author authorWithOneBook = doInJPA(supplier::getFactory, entityManager -> {
            AuthorService testSubject = new AuthorService(entityManager);

            Book nice_book = new Book("XYZ-123", "Nice book");
            Author _author = Author.of("Joe", nice_book);

            return testSubject.save(_author);
        });

        assertNotNull(authorWithOneBook.getId());
        assertEquals("Joe", authorWithOneBook.getName());
        assertNotNull(authorWithOneBook.getBooks());
        assertEquals(1L, authorWithOneBook.getBooks().size());
    }

    @Test
    void findById_author_shouldFindFooWithThreeBooks() {
        JpaTransactionManagerTestSupplier supplier = new JpaTransactionManagerTestSupplier("h2-with-author");

        doInJPA(supplier::getFactory, entityManager -> {
            AuthorService testSubject = new AuthorService(entityManager);

            Optional<Author> _optionalAuthor = testSubject.findAuthorById(-1);

            Author author = _optionalAuthor.orElseThrow();
            assertNotNull(author);

            assertEquals("Foo", author.getName());
            assertEquals(3, author.getBooks().size());
            assertEquals(Set.of(new Book("XYZ-123", "A Book"),
                    new Book("XYZ-456", "B Book"),
                    new Book("XYZ-789", "C Book")), author.getBooks());
        });
    }

    @Test
    void save_author_shouldNotTriggerSelectForBooks() {
        JpaTransactionManagerTestSupplier supplier = new JpaTransactionManagerTestSupplier("h2-with-author");

        doInJPA(supplier::getFactory, entityManager -> {
            AuthorService testSubject = new AuthorService(entityManager);

            Optional<Author> _optionalAuthor = testSubject.findAuthorById(-1);
            Author author = _optionalAuthor.orElseThrow();
            assertNotNull(author);

            Author copyOfAuthor = Author.of(author);
            assertEquals(Set.of(
                    new Book("XYZ-123", "A Book"),
                    new Book("XYZ-456", "B Book"),
                    new Book("XYZ-789", "C Book")), copyOfAuthor.getBooks());
        });
    }
}