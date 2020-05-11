package com.nullok.core.config;

public class AutoConfigRegistry {

    public static PropertiesConfigReader reader;

    public static void setReader(PropertiesConfigReader reader) {
        AutoConfigRegistry.reader = reader;
    }
}