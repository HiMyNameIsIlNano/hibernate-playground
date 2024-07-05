package com.himynameisilnano;

import java.util.Map;

import static org.hibernate.cfg.JdbcSettings.JAKARTA_JDBC_URL;
import static org.hibernate.cfg.SchemaToolingSettings.JAKARTA_HBM2DDL_DATABASE_ACTION;

public class PersistenceUnitPropertiesHelper {

    // Connection String for multiple connections in one process, database is not removed when all connections are closed (may create a memory leak)
    static final String JDBC_URL_TEMPLATE = "jdbc:h2:mem:%s;DB_CLOSE_DELAY=-1";

    public static Map<String, String> recreateDb(String dbName) {
        return Map.of(
                JAKARTA_HBM2DDL_DATABASE_ACTION, "drop-and-create",
                JAKARTA_JDBC_URL, JDBC_URL_TEMPLATE.formatted(dbName)
        );
    }

    public static Map<String, String> createDb(String dbName) {
        return Map.of(
                JAKARTA_HBM2DDL_DATABASE_ACTION, "create",
                JAKARTA_JDBC_URL, JDBC_URL_TEMPLATE.formatted(dbName)
        );
    }

    public static Map<String, String> keepDb(String dbName) {
        return Map.of(
                JAKARTA_HBM2DDL_DATABASE_ACTION, "none",
                JAKARTA_JDBC_URL, JDBC_URL_TEMPLATE.formatted(dbName)
        );
    }

}
