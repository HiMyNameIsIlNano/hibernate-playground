package com.himynameisilnano.hibernate;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JpaTransactionManagerTestSupplier {

    private final String persistenceUnitName;

    public JpaTransactionManagerTestSupplier(final String persistenceUnitName) {
        this.persistenceUnitName = persistenceUnitName;
    }

    public EntityManagerFactory getFactory() {
        return Persistence.createEntityManagerFactory(persistenceUnitName);
    }
}