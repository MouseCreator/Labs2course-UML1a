package com.example.lab23a.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.NoSuchElementException;
import java.util.stream.IntStream;

public class SetIndexList {

    private ArrayList<SetIndex> indexList;
    public SetIndexList() {
        indexList = new ArrayList<>();
    }
    public boolean contains(SetIndex index) {
    	return indexList.contains(index);
    }
    public SetIndexList(SetIndexList list) {
        indexList = new ArrayList<>(list.indexList);
    }

    public void clear() {
        indexList.clear();
    }
    public int size() {
        return indexList.size();
    }
    public void add(SetIndex index) {
        indexList.add(index);
    }
    
    public void insert(SetIndex index) {
    	for (int i = 0; i < indexList.size(); i++) {
    		if (indexList.get(i).getID() == index.getID()) {
    			indexList.set(i, index);
    			return;
    		}
    	}
        indexList.add(index);
    }
    public boolean hasLast(SetIndex index) {
    	return indexList.get(indexList.size()-1).equals(index);
    }
    
    public void remove(SetIndex index) {
        indexList.remove(index);
    }
    public SetIndex get(int index) {
        return indexList.get(index);
    }

    public void save() {
        FileBuilder.writeIndexFile(this);
    }

    public void load() {
        SetIndexList temp = FileBuilder.readIndexFile();
        this.indexList = temp.indexList;
    }
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        for (SetIndex index : indexList) {
            builder.append(index.toString());
        }
        return builder.toString();
    }
    
    public SetIndexList searchByName(String substring) {
    	String lowerCaseSubstring = substring.toLowerCase();
    	SetIndexList result = new SetIndexList();
    	for (SetIndex i : indexList) {
    		if (i.getName().toLowerCase().contains(lowerCaseSubstring))
    			result.add(i);
    	}
    	return result;
    }
    public void reverse() {
    	Collections.reverse(indexList);
    }
    
    public SetIndexList fromFolder(int[] ids) {
    	Arrays.sort(ids);
    	SetIndexList result = new SetIndexList();
    	for (SetIndex i : indexList) {
    		if (IntStream.of(ids).anyMatch(x -> x == i.getID())) {
    			result.add(i);
    		}
    	}
    	return result;
    }
    
    public SetIndexList searchAfter(LocalDate date) {
    	SetIndexList result = new SetIndexList();
    	for (SetIndex i : indexList) {
    		if (i.getCreatedDate().toLocalDate().compareTo(date) >= 0)
    			result.add(i);
    	}
    	return result;
    }
    public SetIndexList searchBefore(LocalDate date) {
    	SetIndexList result = new SetIndexList();
    	for (SetIndex i : indexList) {
    		if (i.getCreatedDate().toLocalDate().compareTo(date) <= 0)
    			result.add(i);
    	}
    	return result;
    }
	public SetIndex find(int id) throws NoSuchElementException {
		for (SetIndex i : indexList) {
    		if (i.getID() == id)
    			return i;
    	}
		throw new NoSuchElementException();
	}
    
}

