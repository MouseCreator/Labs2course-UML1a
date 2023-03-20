package com.example.lab23a.model.builder;

import java.lang.reflect.Field;

public class FileReader {
    private String parseToString(Class<?> dataClass, Object obj) {
        Field[] fields = dataClass.getFields();
        StringBuilder builder = new StringBuilder(dataClass.getName());
        try {
            for (Field field : fields) {
                if (field.isAnnotationPresent(Saved.class)) {
                    builder.append(field.getName()).append(" = ").append(field.get(obj));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
        return builder.toString();
    }
}
