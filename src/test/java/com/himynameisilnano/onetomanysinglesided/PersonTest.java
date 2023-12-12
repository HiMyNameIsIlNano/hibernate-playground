package com.himynameisilnano.onetomanysinglesided;

import com.himynameisilnano.hibernate.JpaTransactionManagerTestSupplier;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hibernate.testing.transaction.TransactionUtil.doInJPA;

class PersonTest {
    @Test
    void save_person_with_pets() {
        JpaTransactionManagerTestSupplier supplier = new JpaTransactionManagerTestSupplier("h2-dev");
        Person joe = doInJPA(supplier::getFactory, entityManager -> {
            Person person = new Person(UUID.randomUUID());
            Pet coco = new Pet("Coco", person);
            Pet poldo = new Pet("Poldo", person);

            person.addPet(coco);
            person.addPet(poldo);

            entityManager.persist(person);

            return person;
        });

        assertThat(joe.getPets()).isUnmodifiable();
    }

}

