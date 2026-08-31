package io.github.mfaisalkhatri.logging;

import org.slf4j.Logger;

public final class FrameworkLifeCycleLogger {

    private static final Logger LOGGER = FrameworkLogger.getLogger (FrameworkLifeCycleLogger.class);

    private FrameworkLifeCycleLogger () {

    }

    public static void executionStarted () {

        final String separator = "=".repeat (60);

        LOGGER.info (separator);
        LOGGER.info ("TEST EXECUTION STARTED");
        LOGGER.info (separator);
    }

    public static void loadingConfiguration () {
        LOGGER.info ("Loading framework configuration");
    }

    public static void configurationLoaded (final String environment, final String baseUrl) {

        LOGGER.info ("Active environment: {}", environment);

        LOGGER.info ("Base URL: {}", baseUrl);
    }

    public static void initializingApiClient () {
        LOGGER.info ("Initializing API request context");
        LOGGER.info ("Initializing Rest-Assured client");
    }

    public static void executionCompleted () {

        final String separator = "=".repeat (60);

        LOGGER.info (separator);
        LOGGER.info ("TEST EXECUTION COMPLETED");
        LOGGER.info (separator);
    }
}

