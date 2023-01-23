package com.example.lab23a.model;

import java.io.*;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class FileBuilder {
	private static final String separator = File.separator;
    private static final String DATA_FOLDER = "src" + separator + "main" + separator + "resources" +
            separator + "data" + separator;
    private static final String SETS_FOLDER = "sets" + separator;
    private static final String FOLDERS_FOLDER = "folders" + separator;
    private static final String SETS_EXTENSION = ".set";
    private static final String FOLDER_EXTENSION = ".fld";

    private static final String INDEX_FILENAME = "sets";
    
    private static final String FILE_DET = "file:";
    private static final String GRAPHICS_FOLDER = "gfx" + separator;
    private static final String ICON_FILENAME = "icon";
    private static final String ATTENTION_ICON_FILENAME = "attention";
    private static final String ICON_EXTENSION = ".png";
    
    
    private static final String INFO_FILENAME = "info";
    private static final String FOLDERS_FILENAME = "folders";
    private static final String INDEX_EXTENSION = ".index";
    private static final String IMAGE_EXTENSION = ".jpg";


    public static String getStudySetFileName(int index) {
        return DATA_FOLDER + SETS_FOLDER + index + SETS_EXTENSION;
    }
    public static String getFolderFileName(int index) {
        return DATA_FOLDER + FOLDERS_FOLDER + index + FOLDER_EXTENSION;
    }
    
    private static String gfx() {
    	return FILE_DET + DATA_FOLDER + GRAPHICS_FOLDER;
    }
    
    public static String getIconDestination() {
        return gfx() + ICON_FILENAME + ICON_EXTENSION;
    }
    public static String getAttentionIconDestination() {
        return gfx() + ATTENTION_ICON_FILENAME + ICON_EXTENSION;
    }
    
    public static String getIndexFileDestination() {
        return DATA_FOLDER + INDEX_FILENAME + INDEX_EXTENSION;
    }
    public static String getInfoFileDestination() {
        return DATA_FOLDER + INFO_FILENAME + INDEX_EXTENSION;
    }
    public static String getFoldersIndexDestination() {
        return DATA_FOLDER + FOLDERS_FILENAME + INDEX_EXTENSION;
    }

	
    protected static final String delimiter = "= ";
    protected static String doubleDelimiter() {
        return ":" + delimiter;
    }
    protected static String commandDelimiter() {
        return " " + delimiter;
    }

    private static final String STUDY_SET_INITIALISATION = "StudySet";
    private static final String STUDY_SET_INDEX = "Index";
    private static final String STUDY_SET_NAME = "Name";
    private static final String STUDY_SET_ELEMENTS = "NumTerms";

    private static final String STUDY_SET_MASTERED = "Mastered";
    private static final String STUDY_SET_CREATION_DATE = "CreatedDate";
    private static final String STUDY_SET_STUDIED_DATE = "StudiedDate";
    private static final String STUDY_SET_CLOSE = "end";
    public static void writeTerms(int index, TermList terms) {
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(getStudySetFileName(index)));
            for (int i = 0; i < terms.size(); i++) {
                writer.write(termToLine(terms.getByIndex(i)));
                if (i != terms.size() - 1) {
                    writer.append('\n');
                }
            }
            writer.close();
        } catch (IOException e) {
            System.err.println("Can't open file to write set");
        } finally {
            close(writer);
        }
    }
    private static String termToLine(StudyTerm term) {
        return duplicateDelimiter(term.getTerm()) + commandDelimiter() +
                duplicateDelimiter(term.getDefinition()) + commandDelimiter() +
                term.getProgress();
    }

    private static String duplicateDelimiter(String str) {
        return str.replaceAll(delimiter, doubleDelimiter());
    }
    private static String removeDelimiterDuplicates(String str) {
        return str.replaceAll(doubleDelimiter(), delimiter);
    }
    private static StudyTerm lineToTerm(int iterator, String line) {
        String[] strings = line.split(commandDelimiter());
        String term = removeDelimiterDuplicates(strings[0]);
        String definition = removeDelimiterDuplicates(strings[1]);
        StudyProgress progress = StudyProgress.valueOf(strings[2]);
        return new StudyTerm(iterator, term, definition, progress);
    }
    public static TermList readTerms(int id) {
        TermList list = new TermList();
        try {
            Scanner scanner = new Scanner(new File(getStudySetFileName(id)));
            int i = 0;
            while (scanner.hasNextLine()) {
                list.add(lineToTerm(i, scanner.nextLine()));
                i++;
            }
        }
        catch (Exception ignored) {

        }
        return list;


    }

    public static void writeIndexFile(SetIndexList list) {
        BufferedWriter writer;
        try {
            writer = new BufferedWriter(new FileWriter(getIndexFileDestination()));
            for (int i = 0; i < list.size(); i++) {
            	String temp = indexToStructure(list.get(i));
                writer.write(temp);
            } 
            writer.close();
        } catch (IOException e) {
            System.err.println("Can't open index file to write");
        }
    }

    private static void close(Closeable closeable) {
        if (closeable == null)
            return;
        try {
            closeable.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    private static String formatField(final String initializer, String value) {
        return "\t" + initializer + commandDelimiter() + duplicateDelimiter(value) + "\n";
    }
    private static String formatField(final String initializer, int value) {
        return formatField(initializer, String.valueOf(value));
    }
    private static String formatField(final String initializer, boolean value) {
        return formatField(initializer, String.valueOf(value));
    }
    private static String indexToStructure(SetIndex index) {
        return STUDY_SET_INITIALISATION + '\n' +
                formatField(STUDY_SET_INDEX, index.getID()) +
                formatField(STUDY_SET_NAME, index.getName()) +
                formatField(STUDY_SET_ELEMENTS,index.getElementsCount()) +
                formatField(STUDY_SET_MASTERED,index.getElementsMastered()) +
                formatField(STUDY_SET_CREATION_DATE, Dates.toDateFormat(index.getCreatedDate())) +
                formatField(STUDY_SET_STUDIED_DATE, Dates.toDateFormat(index.getLastStudied())) +
                STUDY_SET_CLOSE + '\n';
    }
    private static SetIndex lineToIndex(LinkedList<String> indexStructure) {
        SetIndex result = new SetIndex(-1);
        for (String line : indexStructure) {
            String[] initializerAndValue = line.split(commandDelimiter());
            
            String initializer = initializerAndValue[0].trim();
            String fieldValue = "";
            if (initializerAndValue.length == 2)		
            	fieldValue = initializerAndValue[1];
            switch (initializer) {
                case STUDY_SET_INDEX -> result.setID(Integer.parseInt(fieldValue));
                case STUDY_SET_NAME -> result.setName(removeDelimiterDuplicates(fieldValue) );
                case STUDY_SET_ELEMENTS -> result.setElementsCount(Integer.parseInt(fieldValue));
                case STUDY_SET_MASTERED -> result.setElementsMastered(Integer.parseInt(fieldValue));
                case STUDY_SET_CREATION_DATE -> result.setCreatedDate(Dates.fromDateFormat(fieldValue));
                case STUDY_SET_STUDIED_DATE -> result.setLastStudied(Dates.fromDateFormat(fieldValue));
            }
        }
        assert result.getID() != -1;
        return result;
    }

    public static SetIndexList readIndexFile() {
        SetIndexList list = new SetIndexList();
        try {
            Scanner scanner = new Scanner(new File(getIndexFileDestination()));
            while (scanner.hasNext(STUDY_SET_INITIALISATION)) {
                scanner.nextLine();
                LinkedList<String> fields = new LinkedList<>();
                while (scanner.hasNextLine()) {
                    String current = scanner.nextLine();
                    if (current.equals(STUDY_SET_CLOSE))
                        break;
                    else
                        fields.add(current);
                }
                list.add(lineToIndex(fields));
            }
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return list;
    }
    
    public static UserLearnStyle readInfo() {
    	LinkedList<String>list = new LinkedList<>();
    	try {
            Scanner scanner = new Scanner(new File(getInfoFileDestination()));
            while (scanner.hasNextLine()) {
            	list.add(scanner.nextLine());
            }
            return styleFromStructure(list);
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
        }
    	return new UserLearnStyle();
    }
    private static UserLearnStyle styleFromStructure(LinkedList<String> structure) {
    	UserLearnStyle result = new UserLearnStyle();
        while (!structure.isEmpty()) {
        	String line = structure.removeFirst();
            String[] initializerAndValue = line.trim().split(commandDelimiter());
            String initializer = initializerAndValue[0];
            String fieldValue = initializerAndValue[1];
            switch (initializer) {
                case INFO_LAST_USED -> result.setLastUsedIndex(Integer.parseInt(fieldValue));
                case INFO_LAST_FOLDER -> result.setLastUsedIndexFodler (Integer.parseInt(fieldValue));
                case INFO_SHUFFLE_OF -> result.setShuffleOn( Boolean.parseBoolean(fieldValue));
                case INFO_AUTOSAVE -> result.setAutoSaveOn(Boolean.parseBoolean(fieldValue));
                case INFO_LAST_STUDIED -> result.setLastStudied(Dates.fromString(fieldValue));
                case INFO_CURRENT_STREAK -> result.setStreak(Integer.parseInt(fieldValue));
                case INFO_SAVED_PATH -> result.updateLastSavedFileAbsolutePath(fieldValue);
            }
        }
        return result;
    }
    public static void writeInfo(UserLearnStyle style) {
    	BufferedWriter writer;
        try {
            writer = new BufferedWriter(new FileWriter(getInfoFileDestination()));
            writer.write(infoToStructute(style));
            writer.close();
        } catch (IOException e) {
            System.err.println("Can't open info file to write");
        }
    }
    
    private static final String INFO_LAST_USED = "LastIndex";
    private static final String INFO_LAST_FOLDER = "LastFolder";
    private static final String INFO_SHUFFLE_OF = "ShuffleOn";
    private static final String INFO_AUTOSAVE = "Autosave";
    private static final String INFO_LAST_STUDIED = "LastStudied";
    private static final String INFO_CURRENT_STREAK = "Streak";
    private static final String INFO_SAVED_PATH = "Path";
    
    private static String infoToStructute(UserLearnStyle style) {
    	return formatField(INFO_LAST_USED, style.getLastUsedIndex()) +
    			formatField(INFO_LAST_FOLDER, style.getLastUsedIndexFolder())+
    			formatField(INFO_SHUFFLE_OF, style.getIsShuffleOn())+
    			formatField(INFO_AUTOSAVE, style.getAutosaveOn())+
    			formatField(INFO_LAST_STUDIED, Dates.toDateFormat(style.getLastStudied())) +
    			formatField(INFO_CURRENT_STREAK, style.getStreak()) +
                formatField(INFO_SAVED_PATH, style.getLastSavedFileAbsolutePath());
    }
    
    public static String FXMLdestination(String filename) {
       // return "/" + APPLICATION_FOLDER + filename + ".fxml";
        return filename + ".fxml";
	}

    public static SetIndexList readFolder(SetIndexList allSets, int index) {
    	SetIndexList result = new SetIndexList();
    	try {
            Scanner scanner = new Scanner(new File(getFolderFileName(index)));
            while (scanner.hasNextInt()) {
            	try {
            		result.add(allSets.find(scanner.nextInt()));
            	} catch (NoSuchElementException ignored) {}
            }
            	
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
        }
    	return result;
    }
    public static void writeFolder(int index, SetIndexList list) {
    	BufferedWriter writer;
        try {
            writer = new BufferedWriter(new FileWriter(getFolderFileName(index)));
            for (int i = 0; i < list.size(); i++) {
            	writer.write(list.get(i).getID() + " ");
            }
            writer.close();
        } catch (IOException e) {
            System.err.println("Can't open folder file.");
        }
    }
    
    public static FolderList readFolderIndexList() {
    	FolderList result = new FolderList();
    	try {
            Scanner scanner = new Scanner(new File(getFoldersIndexDestination()));
            while (scanner.hasNextLine()) {
            	String line = scanner.nextLine();
            	
            	String[] splitted = line.split(commandDelimiter()); 
            	String id = splitted[0];
            	String name = "";
                if (splitted.length == 2)		
                	name = splitted[1];

            	result.add(new Folder(Integer.parseInt(id),
            			removeDelimiterDuplicates(name)));
            }
            	
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
        }
    	return result;
    }
    public static void writeFolderIndexList(FolderList list) {
    	BufferedWriter writer;
        try {
            writer = new BufferedWriter(new FileWriter(getFoldersIndexDestination()));
            for (int i = 0; i < list.size(); i++) {
            	writer.write(list.get(i).getIndex() + commandDelimiter() + duplicateDelimiter(list.get(i).getName()));
            }
            writer.close();
        } catch (IOException e) {
            System.err.println("Can't open folder index.");
        }
    }
	public static void deleteTerms(int id) {
		File file = new File(FileBuilder.getStudySetFileName(id));
		if(!file.delete()) {
            System.err.println("Unable to delete terms.");
        }
		
	}
	public static void deleteFolder(int index) {
		File file = new File(FileBuilder.getFolderFileName(index));
        if(!file.delete()) {
            System.err.println("Unable to delete terms.");
        }
	}
    public static String getImagePNG(String filename) {
        return DATA_FOLDER + GRAPHICS_FOLDER + filename + ICON_EXTENSION ;
    }
    public static String getImageJPG(String filename) {
        return DATA_FOLDER + GRAPHICS_FOLDER + filename + IMAGE_EXTENSION ;
    }
}

