package io.github.mfaisalkhatri.config;

import io.github.mfaisalkhatri.config.model.EnvironmentConfig;
import io.github.mfaisalkhatri.config.model.Frameworkconfig;
import io.github.mfaisalkhatri.exceptions.ConfigurationException;
import lombok.Getter;

public class ConfigManager {

    private static final String CONFIG_FILE = "config.json";

    private final Frameworkconfig config;
    @Getter
    private final String          activeEnvironment;

    public ConfigManager () {
        final ConfigProvider configProvider = new JsonConfigProvider (CONFIG_FILE);
        this.config = configProvider.load ();
        this.activeEnvironment = resolveEnvironment ();
        validateConfiguration ();
    }

    public String getBaseUrl () {
        return getEnvironmentConfig ().getBaseUrl ();
    }

    public int getConnectionTimeout () {
        return this.config.getTimeouts ()
            .getConnection ();
    }

    public boolean isLoggingEnabled () {
        return this.config.getLogging ()
            .isEnabled ();
    }

    public boolean isRequestLoggingEnabled () {
        return isLoggingEnabled () && this.config.getLogging ()
            .isRequest ();
    }

    public boolean isResponseLoggingEnabled () {
        return isLoggingEnabled () && this.config.getLogging ()
            .isResponse ();
    }

    public EnvironmentConfig getEnvironmentConfig () {
        return this.config.getEnvironments ()
            .get (this.activeEnvironment);
    }

    private String resolveEnvironment () {
        final String environment = System.getProperty ("env", this.config.getActiveEnvironment ());

        if (environment == null || environment.isBlank ()) {
            throw new ConfigurationException (
                "No active Environment configured. Provide -Denv=<environment> or configure 'activeEnvironment in " + "config.json'");
        }
        return environment.trim ()
            .toLowerCase ();
    }

    private void validateConfiguration () {
        if (this.config.getEnvironments () == null || this.config.getActiveEnvironment ()
            .isEmpty ()) {
            throw new ConfigurationException ("No environments configured in config.json");
        }

        if (!this.config.getEnvironments ()
            .containsKey (this.activeEnvironment)) {
            throw new ConfigurationException (
                "Environment: " + this.activeEnvironment + "is not configured in config" + ".json");
        }

        final EnvironmentConfig environmentConfig = getEnvironmentConfig ();
        if (environmentConfig == null) {
            throw new ConfigurationException (
                "Environment: " + this.activeEnvironment + "is not configured in config" + ".json");
        }

        if (environmentConfig.getBaseUrl ()
            .isBlank ()) {
            throw new ConfigurationException (
                "Base URL is not configured in config.json for environment: " + this.activeEnvironment);
        }
        if (this.config.getTimeouts () == null) {
            throw new ConfigurationException (
                "Timeouts configuration is missing in config.json for environment: " + this.activeEnvironment);
        }
        if (this.config.getTimeouts ()
            .getConnection () <= 0) {
            throw new ConfigurationException (
                "Connection timeout must be greater than zero for environment: " + this.activeEnvironment);
        }
        if (this.config.getLogging () == null) {
            throw new ConfigurationException (
                "Logging configuration is missing in config.json for environment: " + this.activeEnvironment);
        }
    }
}