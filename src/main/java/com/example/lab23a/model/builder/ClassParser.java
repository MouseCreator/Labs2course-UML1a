package com.example.lab23a.model.builder;

import com.example.lab23a.model.Folder;
import com.example.lab23a.model.SetIndex;

import java.lang.reflect.Field;
public class ClassParser {
    private static String parseToString(Class<?> dataClass, Object obj) {
        Field[] fields = dataClass.getDeclaredFields();
        StringBuilder builder = new StringBuilder(dataClass.getName());
        builder.append("\n\t");
        try {
            for (Field field : fields) {
                if (field.isAnnotationPresent(Saved.class)) {
                    field.setAccessible(true);
                    if (field.getAnnotationsByType(Saved.class)[0].name().isEmpty())
                         builder.append(field.getName());
                    else builder.append(field.getAnnotationsByType(Saved.class)[0].name());
                    builder.append(" = ").append(field.get(obj)).append("\n\t");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
        return builder.toString();
    }

    public static String saveIndex(SetIndex index) {
        return parseToString(SetIndex.class, index);
    }
    public static String saveFolder(Folder folder) {
        return parseToString(Folder.class, folder);
    }
}
