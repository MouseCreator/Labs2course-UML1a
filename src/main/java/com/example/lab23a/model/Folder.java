package com.example.lab23a.model;

public class Folder {
	private int index;
	public static int ALL_SETS = -2;
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
		if (!(other instanceof Folder))
			return false;
		Folder f = (Folder) other;
		
		return f.index == this.index && f.name.equals(this.name);
	}
	
}
