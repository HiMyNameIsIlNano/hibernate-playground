package com.himynameisilnano;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.Map;

public final class EntityManagerTestUtils {

    public static EntityManagerFactory forPersistenceUnitWithProperties(String persistenceUnitName) {
        return Persistence.createEntityManagerFactory(persistenceUnitName);
    }

    public static EntityManagerFactory forPersistenceUnitWithProperties(String persistenceUnitName, Map<String, String> properties) {
        return Persistence.createEntityManagerFactory(persistenceUnitName, properties);
    }
}
