package com.example.lab23a.model;

import java.util.ArrayList;
import java.util.Comparator;

public class FolderList {
	private ArrayList<Folder> folders;
	private Comparator<Folder> comparatorByName = (o1, o2) -> o1.getName().compareTo(o2.getName());
	public FolderList() {
		folders = new ArrayList<>();
	}
	public FolderList(FolderList other) {
		folders = new ArrayList<>(other.folders);
	}
	public void add(Folder folder) {
		folders.add(folder);
	}
	public Folder get(int index) {
		return folders.get(index);
	}
	public void remove(Folder folder) {
		folders.remove(folder);
	}
	public Folder remove(int index) {
		return folders.remove(index);
	}
	public int size() {
		return folders.size();
	}
	public boolean isEmpty() {
		return folders.isEmpty();
	}
	public FolderList sortByName() {
		FolderList result = new FolderList(this);
		result.folders.sort(comparatorByName);
		return result;
	}
	public ArrayList<Folder> asArrayList() {
		return folders;
	}
}
