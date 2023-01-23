package com.example.lab23a.model;

import java.util.LinkedList;
import java.util.NoSuchElementException;

public class GarbageList {
	private LinkedList<Integer> list;
	/**
	 * removes all garbage indexes
	 */
	public void clear() {
		list.clear();
	}
	public GarbageList() {
		list = new LinkedList<>();
	}
	public GarbageList(GarbageList copyOf) {
		this.list = new LinkedList<>(copyOf.list);
	}
	public void add(int id) {
		list.add(id);
	}
	/**
	 * 
	 * @return gets index of folder/study set and removes it from the list. -1 if list is empty
	 */
	public int pop() {
		try {
			return list.removeFirst();
		} catch (NoSuchElementException e) {
			return -1;
		}
	}
	/**
	 * 
	 * @return unused index of folder/study set. -1 if list is empty
	 */
	public int peek(){
		try {
			return list.getFirst();
		} catch (IndexOutOfBoundsException e) {
			return -1;
		}
	}
	public boolean isEmpty() {
		return list.isEmpty();
	}
}
