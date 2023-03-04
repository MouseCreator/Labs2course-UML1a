package com.example.lab23a.lib;

import java.lang.reflect.Field;

public class Injector {
    private static final Injector injector = new Injector();
    public static Injector getInjector() {
        return injector;
    }
    public static Object getInstance(Class<?> someClass) {
        Field[] fields = someClass.getDeclaredFields();
        for (Field field: fields) {
            if (field.isAnnotationPresent(Inject.class)) {
                Object fieldInstance;
            }
        }
        return null;
    }
}
