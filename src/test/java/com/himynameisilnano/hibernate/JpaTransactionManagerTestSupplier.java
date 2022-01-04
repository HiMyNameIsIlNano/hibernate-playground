package com.himynameisilnano.hibernate;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JpaTransactionManagerTestSupplier {

    private final String persistenceUnitName;

    public JpaTransactionManagerTestSupplier(final String persistenceUnitName) {
        this.persistenceUnitName = persistenceUnitName;
    }

    public EntityManagerFactory getFactory() {
        return Persistence.createEntityManagerFactory(persistenceUnitName);
    }
}