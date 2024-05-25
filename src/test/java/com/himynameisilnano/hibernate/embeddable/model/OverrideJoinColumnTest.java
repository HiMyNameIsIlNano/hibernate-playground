package com.himynameisilnano.hibernate.embeddable.model;

import com.himynameisilnano.hibernate.JdkLoggingConfigReaderHelper;
import com.himynameisilnano.hibernate.JpaTransactionManagerTestSupplier;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.assertj.core.api.Assertions.*;
import static org.hibernate.testing.transaction.TransactionUtil.doInJPA;

@ExtendWith(JdkLoggingConfigReaderHelper.class)
class OverrideJoinColumnTest {

    @Test
    void can_Override_Join_Column() {
        JpaTransactionManagerTestSupplier supplier = new JpaTransactionManagerTestSupplier("h2-dev");
        doInJPA(supplier::getFactory, entityManager -> {
            TLeftEntityToRightEntity tLeftEntityToRightEntity = new TLeftEntityToRightEntity();

            assertThat(tLeftEntityToRightEntity).isNotNull();
        });
    }
}