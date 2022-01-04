package com.himynameisilnano.hibernate;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

/**
 * A class to manually handle transactions in tests
 *
 * @deprecated use {@link JpaTransactionManagerTestSupplier}
 */
@Deprecated
public class ManualTransactionManagerTestHelper {

    public static final Logger LOGGER = LogManager.getLogger(ManualTransactionManagerTestHelper.class);

    private final EntityManager entityManager;

    private ManualTransactionManagerTestHelper(final String persistenceUnitName) {
        entityManager = Persistence
                .createEntityManagerFactory(persistenceUnitName)
                .createEntityManager();
    }

    public static ManualTransactionManagerTestHelper createEntityManagerFromPersistenceUnit(
            final String persistenceUnitName) {
        return new ManualTransactionManagerTestHelper(persistenceUnitName);
    }

    public void startTransaction() {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        LOGGER.info("Transaction started");
    }

    public void commitTransaction() {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.commit();
        LOGGER.info("Transaction committed");
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }
}