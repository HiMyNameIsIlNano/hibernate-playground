package com.himynameisilnano.hibernate.boundary;

import com.himynameisilnano.hibernate.JpaTransactionManagerTestSupplier;
import com.himynameisilnano.hibernate.entity.Author;
import com.himynameisilnano.hibernate.entity.Book;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.hibernate.testing.transaction.TransactionUtil.doInJPA;

class AuthorServiceTest {

    @Test
    void saveJoe() {
        JpaTransactionManagerTestSupplier supplier = new JpaTransactionManagerTestSupplier("h2-dev");
        Author joe = doInJPA(supplier::getFactory, entityManager -> {
            CustomerService testSubject = new CustomerService(entityManager);

            Author _author = new Author("Joe");
            testSubject.save(_author);

            return _author;
        });

        Assertions.assertNotNull(joe.getId());
        Assertions.assertEquals("Joe", joe.getName());
    }

    @Test
    void saveAuthorWithOneBook() {
        JpaTransactionManagerTestSupplier supplier = new JpaTransactionManagerTestSupplier("h2-dev");
        Author authorWithOneBook = doInJPA(supplier::getFactory, entityManager -> {
            CustomerService testSubject = new CustomerService(entityManager);

            Author _author = new Author("Joe");
            Book nice_book = new Book("Nice book", _author);
            _author.addBook(nice_book);

            testSubject.save(_author);
            entityManager.persist(nice_book);
            return _author;
        });

        Assertions.assertNotNull(authorWithOneBook.getId());
        Assertions.assertEquals("Joe", authorWithOneBook.getName());
        Assertions.assertNotNull(authorWithOneBook.getBooks());
        Assertions.assertEquals(1L, authorWithOneBook.getBooks().size());
    }
}