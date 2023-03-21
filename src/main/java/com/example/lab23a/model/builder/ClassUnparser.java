package com.example.lab23a.model.builder;

import com.example.lab23a.model.Folder;
import com.example.lab23a.model.SetIndex;

import java.lang.reflect.Field;

/**
 * Class with static methods to parse objects to strings
 * Visitor pattern implementation
 */
public class ClassUnparser implements Visitor {


    /**
     * Parses folder to savable string
     * @param folder - folder to parse
     * @return string representation of the folder
     */
    public String parseFolder(Folder folder) {
        return parseToString(Folder.class, folder);
    }

    private String parseToString(Class<?> dataClass, Object obj) {
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

    @Override
    public void visitSetIndex(SetIndex index) {
        str = parseToString(SetIndex.class, index);
    }

    @Override
    public Object get() {
        return str;
    }

    private String str;
}
