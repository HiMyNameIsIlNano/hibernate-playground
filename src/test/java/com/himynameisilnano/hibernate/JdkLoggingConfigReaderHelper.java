package com.himynameisilnano.hibernate;

import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class JdkLoggingConfigReaderHelper implements BeforeAllCallback {

    private static final Logger LOG = Logger.getLogger(JdkLoggingConfigReaderHelper.class.toString());
    private static final String JDK_LOGGER_CONFIGURATION_PATH = "src/main/resources/jdk.logger";

    @Override
    public void beforeAll(ExtensionContext context) {
        try (InputStream is = new FileInputStream(JDK_LOGGER_CONFIGURATION_PATH)) {
            LogManager.getLogManager().readConfiguration(is);
        } catch (IOException e) {
            LOG.warning("Logger configuration file not found: " + JDK_LOGGER_CONFIGURATION_PATH);
        }
    }
}
