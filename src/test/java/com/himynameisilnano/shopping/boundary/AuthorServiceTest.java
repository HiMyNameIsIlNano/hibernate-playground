package com.himynameisilnano.shopping.boundary;

import com.himynameisilnano.shopping.entity.Author;
import com.himynameisilnano.shopping.JpaTransactionManagerTestSupplier;
import com.himynameisilnano.shopping.entity.Book;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.hibernate.testing.transaction.TransactionUtil.doInJPA;

class AuthorServiceTest {

    @Test
    void saveJoe() {
        JpaTransactionManagerTestSupplier supplier = new JpaTransactionManagerTestSupplier("h2-dev");
        Author joe = doInJPA(supplier::getFactory, entityManager -> {
            CustomerService testSubject = new CustomerService(entityManager);

            Author _author = new Author();
            _author.setName("Joe");
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

            Author _author = new Author();
            _author.setName("Joe");
            _author.addBook(new Book("Nice book"));

            testSubject.save(_author);

            return _author;
        });

        Assertions.assertNotNull(authorWithOneBook.getId());
        Assertions.assertEquals("Joe", authorWithOneBook.getName());
        Assertions.assertNotNull(authorWithOneBook.getBooks());
        Assertions.assertEquals(1L, authorWithOneBook.getBooks().size());
    }
}