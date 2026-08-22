package io.github.mfaisalkhatri.config.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoggingConfig {
    private boolean enabled;
    private boolean request;
    private boolean response;

}
