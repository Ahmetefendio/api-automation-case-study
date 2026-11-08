package com.nesine.api.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Ortam ayarlarini config/{env}.properties dosyasindan okur.
 * Her anahtar -D ile ezilebilir, orn: -Denv=stage -DbaseUrl=http://localhost:8080
 */
public final class Config {

    private static final String ENV = System.getProperty("env", "prod");
    private static final Properties PROPERTIES = load(ENV);

    private Config() {
    }

    public static String baseUrl() {
        return get("baseUrl");
    }

    public static int timeoutMs() {
        return Integer.parseInt(get("timeoutMs"));
    }

    private static String get(String key) {
        String value = System.getProperty(key, PROPERTIES.getProperty(key));
        if (value == null || value.isBlank()) {
            throw new IllegalStateException("'" + key + "' is not defined for env: " + ENV);
        }
        return value.trim();
    }

    private static Properties load(String env) {
        String file = "config/" + env + ".properties";
        try (InputStream in = Config.class.getClassLoader().getResourceAsStream(file)) {
            if (in == null) {
                throw new IllegalStateException("Config file not found: " + file);
            }
            Properties properties = new Properties();
            properties.load(in);
            return properties;
        } catch (IOException e) {
            throw new IllegalStateException("Could not read " + file, e);
        }
    }
}
