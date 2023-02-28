package com.example.lab23a.model;

/**
 * Stores and updates user's information, including options, folders and study sets
 */
public class UserData {
	private SetIndexList indexList;
	private FolderList folderList;
	private int lastUsedIndex;
	private int lastFolderIndex;
	private Thread saveIndexThread;

	private UserOptions userOptions;
	private UserStreak userStreak;
	private UserSavedInfo userInfo;

	public UserData() {
		this.folderList = new FolderList();
		this.indexList = new SetIndexList();
		this.lastUsedIndex = 0;
	}

	/**
	 * Receives information from file system
	 */
	public void load() {
		indexList = FileBuilder.readIndexFile();
		initStyle();
		folderList = FileBuilder.readFolderIndexList();
	}
	
	private void initStyle() {
		UserLearnStyle style = FileBuilder.readInfo();
		this.userInfo = style.getInfo();
		this.userOptions = style.getOptions();
		this.userStreak = style.getStreak();
		userStreak.initStreak();
	}

	/**
	 * Saves data to file system
	 */
	public void saveData() {
		saveIndex();
	}

	/**
	 * Updates index list file
	 */
	public void saveIndex() {

		FileBuilder.writeInfo(toStyle());
		FileBuilder.writeIndexFile(indexList);
		FileBuilder.writeFolderIndexList(folderList);
	}

	public UserLearnStyle toStyle() {
		return new UserLearnStyle(userOptions, userStreak, userInfo);
	}
	
	
	public void autoSave() {
		if (!userOptions.getAutosaveOn())
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
	public void addSetToFolder(SetIndex index, Folder folder) {
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

	public UserOptions getUserOptions() {
		return userOptions;
	}




	public UserStreak getUserStreak() {
		return userStreak;
	}

	public UserSavedInfo getUserInfo() {
		return userInfo;
	}




}
