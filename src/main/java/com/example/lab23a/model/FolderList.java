package com.example.lab23a.model;

import com.example.lab23a.model.sort.NameSorterVisitor;
import com.example.lab23a.model.sort.Sortable;
import com.example.lab23a.model.sort.SortingVisitor;

import java.util.ArrayList;
import java.util.List;

public class FolderList implements Sortable {
	private final ArrayList<Folder> folders;


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
		NameSorterVisitor nameSorterVisitor = new NameSorterVisitor();
		FolderList result = new FolderList(this);
		result.acceptVisitor(nameSorterVisitor);
		return result;
	}

	/**
	 *
	 * @return array list, containing all folders in this list
	 */
	public List<Folder> asList() {
		return folders;
	}

	@Override
	public void acceptVisitor(SortingVisitor visitor) {
		visitor.visitFolderList(this);
	}
}
