package io.github.mfaisalkhatri.config;

import io.github.mfaisalkhatri.config.model.Frameworkconfig;

public interface ConfigProvider {
    Frameworkconfig load();
}
