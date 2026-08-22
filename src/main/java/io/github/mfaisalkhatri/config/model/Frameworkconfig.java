package io.github.mfaisalkhatri.config.model;

import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Frameworkconfig {
    private String                         activeEnvironment;
    private Map<String, EnvironmentConfig> environments;
    private TimeoutConfig                  timeouts;
    private LoggingConfig logging;

}
