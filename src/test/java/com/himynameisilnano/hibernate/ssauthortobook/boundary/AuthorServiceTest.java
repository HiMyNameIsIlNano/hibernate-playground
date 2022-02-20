package com.himynameisilnano.hibernate.ssauthortobook.boundary;

import com.himynameisilnano.hibernate.JpaTransactionManagerTestSupplier;
import com.himynameisilnano.hibernate.ssauthortobook.entity.Author;
import com.himynameisilnano.hibernate.ssauthortobook.entity.Book;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.hibernate.testing.transaction.TransactionUtil.doInJPA;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AuthorServiceTest {

    @Test
    void save_author_shouldBeFoundInDb() {
        JpaTransactionManagerTestSupplier supplier = new JpaTransactionManagerTestSupplier("h2-dev");
        Author joe = doInJPA(supplier::getFactory, entityManager -> {
            AuthorService testSubject = new AuthorService(entityManager);

            Author _author = new Author("Joe");
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

            Author _author = new Author("Joe");
            Book nice_book = new Book("XYZ-123", "Nice book");
            _author.addBook(nice_book);

            testSubject.save(_author);
            return _author;
        });

        assertNotNull(authorWithOneBook.getId());
        assertEquals("Joe", authorWithOneBook.getName());
        assertNotNull(authorWithOneBook.getBooks());
        assertEquals(1L, authorWithOneBook.getBooks().size());
    }

    @Test
    @Disabled(value = "The author has a set of books and not a list. Change the collection type and reactivate this test")
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

        assertNotNull(authorWithThreeBooks.getId());
        assertEquals("Joe", authorWithThreeBooks.getName());
        assertNotNull(authorWithThreeBooks.getBooks());
        assertEquals(3L, authorWithThreeBooks.getBooks().size());
    }

    @Test
    void of_authorWithDuplicatedBook_shouldThrowIllegalArgumentExceptionWhenBookDuplicated() {
        assertThrows(IllegalArgumentException.class, () -> Set.of(new Book("XYZ-123", "Nice book"),
                new Book("XYZ-456", "Awesome book"),
                new Book("XYZ-123", "Nice book"))
        );
    }

    @Test
    void save_authorWithTwoBooks_shouldSaveBooksCorrectlyWhenCollectionIsSet() {
        JpaTransactionManagerTestSupplier supplier = new JpaTransactionManagerTestSupplier("h2-dev");
        Author authorWithThreeBooks = doInJPA(supplier::getFactory, entityManager -> {
            AuthorService testSubject = new AuthorService(entityManager);

            Author _author = new Author("Joe");
            // It is not possible to build a set with duplicate values.
            Set<Book> books = Set.of(
                    new Book("XYZ-456", "Awesome book"),
                    new Book("XYZ-123", "Nice book"));
            _author.addBooks(books);

            testSubject.save(_author);
            return _author;
        });

        assertNotNull(authorWithThreeBooks.getId());
        assertEquals("Joe", authorWithThreeBooks.getName());
        assertNotNull(authorWithThreeBooks.getBooks());
        assertEquals(2L, authorWithThreeBooks.getBooks().size());
    }

    @Test
    void save_authorWithADuplicatedBook_shouldNotSaveDuplicatedBookTwice() {
        JpaTransactionManagerTestSupplier supplier = new JpaTransactionManagerTestSupplier("h2-dev");
        Author authorWithThreeBooks = doInJPA(supplier::getFactory, entityManager -> {
            AuthorService testSubject = new AuthorService(entityManager);

            Author _author = new Author("Joe");

            Book awesomeBook = new Book("XYZ-456", "Awesome book");
            Book niceBook = new Book("XYZ-123", "Nice book");
            _author.addBook(awesomeBook);
            _author.addBook(niceBook);
            // This addBook here is skipped by the Java API as the element is already in the Set.
            _author.addBook(niceBook);

            testSubject.save(_author);
            return _author;
        });

        assertNotNull(authorWithThreeBooks.getId());
        assertEquals("Joe", authorWithThreeBooks.getName());
        assertNotNull(authorWithThreeBooks.getBooks());
        assertEquals(2L, authorWithThreeBooks.getBooks().size());
    }
}