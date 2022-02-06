package com.himynameisilnano.hibernate.ssauthortobook.boundary;

import com.himynameisilnano.hibernate.JpaTransactionManagerTestSupplier;
import com.himynameisilnano.hibernate.ssauthortobook.entity.Author;
import com.himynameisilnano.hibernate.ssauthortobook.entity.Book;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.hibernate.testing.transaction.TransactionUtil.doInJPA;

class AuthorServiceTest {

    @Test
    void save_author_shouldBeFoundInDb() {
        JpaTransactionManagerTestSupplier supplier = new JpaTransactionManagerTestSupplier("h2-dev");
        Author joe = doInJPA(supplier::getFactory, entityManager -> {
            AuthorService testSubject = new AuthorService(entityManager);

            Author _author = new Author();
            _author.setName("Joe");
            testSubject.save(_author);

            return _author;
        });

        Assertions.assertNotNull(joe.getId());
        Assertions.assertEquals("Joe", joe.getName());
    }

    @Test
    void save_author_shouldHaveOnlyOneBook() {
        JpaTransactionManagerTestSupplier supplier = new JpaTransactionManagerTestSupplier("h2-dev");
        Author authorWithOneBook = doInJPA(supplier::getFactory, entityManager -> {
            AuthorService testSubject = new AuthorService(entityManager);

            Author _author = new Author("Joe");
            Book nice_book = new Book("XYZ-123", "Nice book");
            _author.addBook(nice_book);

            testSubject.save(_author);
            return _author;
        });

        Assertions.assertNotNull(authorWithOneBook.getId());
        Assertions.assertEquals("Joe", authorWithOneBook.getName());
        Assertions.assertNotNull(authorWithOneBook.getBooks());
        Assertions.assertEquals(1L, authorWithOneBook.getBooks().size());
    }

    @Test
    void save_authorWithDuplicatedBook_shouldUpdateBooksToAuthorReferenceTwice() {
        JpaTransactionManagerTestSupplier supplier = new JpaTransactionManagerTestSupplier("h2-dev");
        Author authorWithThreeBooks = doInJPA(supplier::getFactory, entityManager -> {
            AuthorService testSubject = new AuthorService(entityManager);

            Author _author = new Author("Joe");
            // There is just one instance of the book
            Book nice_book = new Book("XYZ-123", "Nice book");
            Set<Book> threeBooksWithOneDuplicate = Set.of(nice_book,
                    new Book("XYZ-456", "Awesome book"),
                    nice_book);
            _author.addBooks(threeBooksWithOneDuplicate);

            testSubject.save(_author);
            return _author;
        });

        Assertions.assertNotNull(authorWithThreeBooks.getId());
        Assertions.assertEquals("Joe", authorWithThreeBooks.getName());
        Assertions.assertNotNull(authorWithThreeBooks.getBooks());
        Assertions.assertEquals(3L, authorWithThreeBooks.getBooks().size());
    }

    @Test
    void save_authorWithDuplicatedBook_shouldSaveBooksTwice() {
        JpaTransactionManagerTestSupplier supplier = new JpaTransactionManagerTestSupplier("h2-dev");
        Author authorWithThreeBooks = doInJPA(supplier::getFactory, entityManager -> {
            AuthorService testSubject = new AuthorService(entityManager);

            Author _author = new Author("Joe");
            // The same book is now two instances
            List<Book> threeBooksWithOneDuplicate = List.of(new Book("XYZ-123", "Nice book"),
                    new Book("XYZ-456", "Awesome book"),
                    new Book("XYZ-123", "Nice book"));
            //_author.addBooks(threeBooksWithOneDuplicate);
            _author.addBooks(Set.of());

            testSubject.save(_author);
            return _author;
        });

        Assertions.assertNotNull(authorWithThreeBooks.getId());
        Assertions.assertEquals("Joe", authorWithThreeBooks.getName());
        Assertions.assertNotNull(authorWithThreeBooks.getBooks());
        Assertions.assertEquals(3L, authorWithThreeBooks.getBooks().size());
    }

    /**
     * When the collection is a set it should be all fine
     */
    @Test
    void save_authorWithDuplicatedBook_shouldSaveBooksCorrectlyWhenCollectionIsSet() {
        JpaTransactionManagerTestSupplier supplier = new JpaTransactionManagerTestSupplier("h2-dev");
        Author authorWithThreeBooks = doInJPA(supplier::getFactory, entityManager -> {
            AuthorService testSubject = new AuthorService(entityManager);

            Author _author = new Author("Joe");
            // The same book is now two instances
            Book nice_book = new Book("XYZ-123", "Nice book");
            Set<Book> threeBooksWithOneDuplicate = Set.of(nice_book,
                    new Book("XYZ-456", "Awesome book"),
                    nice_book);
            _author.addBooks(threeBooksWithOneDuplicate);

            testSubject.save(_author);
            return _author;
        });

        Assertions.assertNotNull(authorWithThreeBooks.getId());
        Assertions.assertEquals("Joe", authorWithThreeBooks.getName());
        Assertions.assertNotNull(authorWithThreeBooks.getBooks());
        Assertions.assertEquals(3L, authorWithThreeBooks.getBooks().size());
    }
}