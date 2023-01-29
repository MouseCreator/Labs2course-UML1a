package com.example.lab23a.model;

import java.util.ArrayList;
import java.util.Comparator;

public class FolderList {
	private final ArrayList<Folder> folders;
	private final Comparator<Folder> comparatorByName = Comparator.comparing(Folder::getName);

	/**
	 * creates empty list of folders
	 */
	public FolderList() {
		folders = new ArrayList<>();
	}

	/**
	 *
	 * @param other - list of folders to copy
	 */
	public FolderList(FolderList other) {
		folders = new ArrayList<>(other.folders);
	}

	/**
	 *
	 * @param folder - folder to add to the list
	 */
	public void add(Folder folder) {
		folders.add(folder);
	}

	/**
	 *
	 * @param index - number of folder in the list
	 * @return specified folder
	 */
	public Folder get(int index) {
		return folders.get(index);
	}

	/**
	 *
	 * @param folder - folder to remove. If it is not in the list, nothing will be deleted
	 */
	public void remove(Folder folder) {
		folders.remove(folder);
	}

	/**
	 *
	 * @param index - number of folder in the list to remove
	 */
	public void remove(int index) {
		folders.remove(index);
	}

	/**
	 *
	 * @return number of folders in the list
	 */
	public int size() {
		return folders.size();
	}

	/**
	 *
	 * @return list with the same folders, sorted in alphabetic order
	 */
	public FolderList sortByName() {
		FolderList result = new FolderList(this);
		result.folders.sort(comparatorByName);
		return result;
	}

	/**
	 *
	 * @return array list, containing all folders in this list
	 */
	public ArrayList<Folder> asArrayList() {
		return folders;
	}
}
