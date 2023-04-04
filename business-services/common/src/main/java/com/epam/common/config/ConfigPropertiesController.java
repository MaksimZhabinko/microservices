package com.epam.common.config;

import com.netflix.archaius.DefaultPropertyFactory;
import com.netflix.archaius.config.PollingDynamicConfig;
import com.netflix.archaius.config.polling.FixedPollingStrategy;
import com.netflix.archaius.readers.URLConfigReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RestController
public class ConfigPropertiesController {
    private final DefaultPropertyFactory factory;

    @Autowired
    public ConfigPropertiesController(@Value("${internal-properties.path}") String configPath) {
        factory = DefaultPropertyFactory.from(
                new PollingDynamicConfig(
                        new URLConfigReader(configPath),
                        new FixedPollingStrategy(1, TimeUnit.SECONDS)));
    }

    @GetMapping("/props/{name}")
    public String findProperty(@PathVariable("name") String name) {
        return factory.get(name, String.class).get();
    }
}
