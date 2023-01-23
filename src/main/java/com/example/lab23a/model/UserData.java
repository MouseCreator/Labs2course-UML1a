package com.example.lab23a.model;

public class UserData {
	private int lastUsedIndexFolder = 0;
	private SetIndexList indexList;
	private UserLearnStyle style;
	private FolderList folderList;


	private int lastUsedIndex;
	private int lastFolderIndex;
	private Thread saveIndexThread;
	
	public void load() {
		indexList = FileBuilder.readIndexFile();
		initStyle();
		folderList = FileBuilder.readFolderIndexList();
	}
	
	private void initStyle() {
		style = FileBuilder.readInfo();
		lastUsedIndex = style.getLastUsedIndex();
		lastUsedIndexFolder = style.getLastUsedIndexFolder();
		style.initStreak();
	}
	public void saveData() {
		saveIndex();
	}
	
	public void saveIndex() {
		toStyle();
		FileBuilder.writeInfo(style);
		FileBuilder.writeIndexFile(indexList);
		FileBuilder.writeFolderIndexList(folderList);
	}
	public void toStyle() {
		style.setLastUsedIndex(lastUsedIndex);
		style.setLastUsedIndexFodler(lastUsedIndexFolder);
	}
	
	
	public void autoSave() {
		if (!style.getAutosaveOn())
			return;
		if (saveIndexThread == null || !saveIndexThread.isAlive()) {
			AutoSaveTask saveTask = new AutoSaveTask(this);
			saveIndexThread = new Thread(saveTask);
			saveIndexThread.setDaemon(true);
			saveIndexThread.start();
		}
		else {
			System.err.println("Still saving previous data!");
		}
	}
	
	
	public SetIndexList getIndexList() {
		return indexList;
	}
	public void insertNewSet(SetIndex index) {
		
		if (!this.getIndexList().contains(index))
			this.lastUsedIndex++;
		
		this.getIndexList().insert(index);
		
	}
	public UserLearnStyle getStyle() {
		return style;
	}
	public SetIndex genNewIndex() {
		return new SetIndex(this.lastUsedIndex+1);
	}
	
	public void removeStudySet(SetIndex index) {
		this.indexList.remove(index);
		FileBuilder.deleteTerms(index.getID());
	}
	
	public FolderList getFolderList() {
		return this.folderList;
	}
	public Folder appendNewFolder(String name) {
		int folderIndex;
		folderIndex = ++lastFolderIndex;
		Folder toAdd = new Folder(folderIndex, name);
		this.folderList.add(toAdd);
		return toAdd;
	}
	public void addSettoFolder(SetIndex index, Folder folder) {
		SetIndexList sets = FileBuilder.readFolder(indexList, folder.getIndex());
		if (!sets.contains(index)) {
			sets.add(index);
			FileBuilder.writeFolder(folder.getIndex(), sets);
		}
	}
	public void removeSetFromFolder(SetIndex index, Folder folder) {
		SetIndexList sets = FileBuilder.readFolder(indexList, folder.getIndex());
		try {
			sets.remove(index);
		} catch(Exception ignored) {}
		FileBuilder.writeFolder(folder.getIndex(), sets);
	}
	public void removeFolder(Folder folder) {
		this.folderList.remove(folder);
		FileBuilder.deleteFolder(folder.getIndex());
	}



}
