package com.himynameisilnano.hibernate.embeddable.model;

import com.himynameisilnano.hibernate.JdkLoggingConfigReaderHelper;
import com.himynameisilnano.hibernate.JpaTransactionManagerTestSupplier;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.UUID;

import static org.hibernate.testing.transaction.TransactionUtil.doInJPA;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(JdkLoggingConfigReaderHelper.class)
class HTeacherTest {

    @Test
    void can_Override_Join_Column() {
        JpaTransactionManagerTestSupplier supplier = new JpaTransactionManagerTestSupplier("h2-dev");
        HTeacher persistedTeacher = doInJPA(supplier::getFactory, entityManager -> {
            TTeacher tTeacher = new TTeacher(UUID.randomUUID().toString());
            entityManager.persist(tTeacher);

            HTeacher teacher = new HTeacher(tTeacher, tTeacher.getNaturalId());

            entityManager.persist(teacher);
            return teacher;
        });

        assertNotNull(persistedTeacher.getTObject());
    }
}