package com.example.lab23a.model.builder;

import com.example.lab23a.model.Folder;
import com.example.lab23a.model.SetIndex;

import java.lang.reflect.Field;

/**
 * Class with static methods to parse objects to strings
 * Visitor pattern implementation
 */
public class ClassParser {

    /**
     * Parses index to savable string
     * @param index - index to parse
     * @return string representation of the given set index
     */
    public static String parseIndex(SetIndex index) {
        return parseToString(SetIndex.class, index);
    }

    /**
     * Parses folder to savable string
     * @param folder - folder to parse
     * @return string representation of the folder
     */
    public static String parseFolder(Folder folder) {
        return parseToString(Folder.class, folder);
    }

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
    private String parseList(Class<?> listClass, Class<?> elementClass, Object obj) throws IllegalAccessException {
        Field[] fields = listClass.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            if (field.isAnnotationPresent(Collect.class)) {
                Object collection = field.get(obj);
            }
        }
        return "";
    }
}
