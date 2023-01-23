package com.example.lab23a.PDF;

import java.util.LinkedList;

public class PDFCell {
    private static final double monospaceConstant = 1.3;
    private final String word;
    private final int width;
    private final int height;
    private final LinkedList<String> strings;

    public PDFCell(int cellWidth, int fontSize, String word) {
        strings = new LinkedList<>();
        this.word = word;
        width = cellWidth;
        int numWords = this.splitWord(fontSize);
        this.height = (int) (monospaceConstant * numWords * fontSize + 4);
    }
    public int getHeight() {
        return height;
    }
    public String[] getLines(){
        return this.strings.toArray(new String[0]);
    }
    protected int getCharsPerLine(int fontSize) {
        return (int) (width * monospaceConstant / fontSize); //25
    }
    protected int splitWord(int fontSize) {
        int charsInLine = getCharsPerLine(fontSize);
        String[] splitBySpaces = word.split(" ");
        int i = 0;
        while (i < splitBySpaces.length) {
            if (splitBySpaces[i].length() > charsInLine) {
                strings.add(splitBySpaces[i].substring(0, charsInLine));
                splitBySpaces[i] = splitBySpaces[i].substring(charsInLine);
            }
            else {
                if (i + 1 < splitBySpaces.length) {
                    StringBuilder toAdd = new StringBuilder(splitBySpaces[i]);
                    while (i + 1 < splitBySpaces.length && toAdd.toString().concat(splitBySpaces[i+1]).length() <= charsInLine) {
                        i++;
                        toAdd.append(" ").append(splitBySpaces[i]);
                    }
                    strings.add(toAdd.toString());
                    i++;
                } else {
                    strings.add(splitBySpaces[i]);
                    break;
                }
            }
        }

        return strings.size();
    }

    public float getWidth() {
        return this.width;
    }
}
