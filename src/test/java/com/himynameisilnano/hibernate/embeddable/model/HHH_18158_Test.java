package com.himynameisilnano.hibernate.embeddable.model;

import jakarta.persistence.Persistence;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hibernate.testing.transaction.TransactionUtil.doInJPA;

//@ExtendWith(JdkLoggingConfigReaderHelper.class)
class HHH18158Test {

    @Test
    void can_Override_Join_Column() {
        doInJPA(() -> Persistence.createEntityManagerFactory("h2-dev"), entityManager -> {
            MainEntity mainEntity = new MainEntity();

            assertThat(mainEntity).isNotNull();
        });
    }
}