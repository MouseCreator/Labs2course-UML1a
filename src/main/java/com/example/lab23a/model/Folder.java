package com.example.lab23a.model;

import com.example.lab23a.model.builder.Saved;

public class Folder {
	@Saved
	private int index;
	public static int ALL_SETS = -2;
	@Saved
	private String name;
	public Folder(int index) {
		this.setIndex(index);
		this.setName("");
	}
	public Folder(int index, String name) {
		this.setIndex(index);
		this.setName(name);
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return this.name;
	}
	@Override
	public boolean equals(Object other) {
		if (this == other)
			return true;
		if (!(other instanceof Folder folderOther))
			return false;

		return folderOther.index == this.index && folderOther.name.equals(this.name);
	}
	
}
