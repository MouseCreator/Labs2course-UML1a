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

	/**
	 *
	 * @return list of all user's sets
	 */
	public SetIndexList getIndexList() {
		return indexList;
	}

	/**
	 * Adds new set index, if it wasn't included yet
	 * @param index - index to add.
	 */
	public void insertNewSet(SetIndex index) {
		
		if (!this.getIndexList().contains(index))
			this.lastUsedIndex++;
		
		this.getIndexList().insert(index);
		
	}

	/**
	 * Creates new empty study set
	 * @return generated index
	 */
	public SetIndex generateNewIndex() {
		return new SetIndex(this.lastUsedIndex+1);
	}

	/**
	 * Removes specified index from the list of all indexes
	 * @param index - set index to remove
	 */
	public void removeStudySet(SetIndex index) {
		this.indexList.remove(index);
		FileBuilder.deleteTerms(index.getID());
	}

	/**
	 *
	 * @return list of all user's folders
	 */
	public FolderList getFolderList() {
		return this.folderList;
	}

	/**
	 *
	 * @param name - name of the new folder
	 * @return new folder, that was added to the list of all folders
	 */
	public Folder appendNewFolder(String name) {
		int folderIndex;
		folderIndex = ++lastFolderIndex;
		Folder toAdd = new Folder(folderIndex, name);
		this.folderList.add(toAdd);
		return toAdd;
	}

	/**
	 *
	 * @param index - set index to add
	 * @param folder - in which folder the index will be added
	 */
	public void addSetToFolder(SetIndex index, Folder folder) {
		SetIndexList sets = FileBuilder.readFolder(indexList, folder.getIndex());
		if (!sets.contains(index)) {
			sets.add(index);
			FileBuilder.writeFolder(folder.getIndex(), sets);
		}
	}

	/**
	 * Removes index from folder. If the index is not in the folder, nothing changes.
	 * @param index - study set that has to be removed from folder.
	 * @param folder - folder, from which the index will be deleted
	 */
	public void removeSetFromFolder(SetIndex index, Folder folder) {
		SetIndexList sets = FileBuilder.readFolder(indexList, folder.getIndex());
		try {
			sets.remove(index);
		} catch(Exception ignored) {}
		FileBuilder.writeFolder(folder.getIndex(), sets);
	}

	/**
	 * Removes folder from the list of all folders
	 * @param folder - folder to remove
	 */
	public void removeFolder(Folder folder) {
		this.folderList.remove(folder);
		FileBuilder.deleteFolder(folder.getIndex());
	}

	/**
	 *
	 * @return user's main options
	 */
	public UserOptions getUserOptions() {
		return userOptions;
	}

	/**
	 *
	 * @return user's streak manager
	 */
	public UserStreak getUserStreak() {
		return userStreak;
	}

	/**
	 *
	 * @return additional user info, saved in filesystem
	 */
	public UserSavedInfo getUserInfo() {
		return userInfo;
	}




}
