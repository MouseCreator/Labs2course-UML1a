package com.example.lab23a.model;

import com.example.lab23a.model.sort.Sortable;
import com.example.lab23a.model.sort.SortingVisitor;

import java.time.LocalDate;
import java.util.*;

public class SetIndexList implements Iterable<SetIndex>, Sortable {

    private ArrayList<SetIndex> indexList;
    public SetIndexList() {
        indexList = new ArrayList<>();
    }
    public SetIndexList(SetIndexList other) {
        indexList = new ArrayList<>(other.indexList);
    }

    public boolean contains(SetIndex index) {
        return indexList.contains(index);
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
    public void remove(SetIndex index) {
        indexList.remove(index);
    }
    public SetIndex get(int index) {
        return indexList.get(index);
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

    @Override
    public Iterator<SetIndex> iterator() {
        return new Iterator<>() {
            private int count = 0;
            @Override public boolean hasNext() {
                return count < size();
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("Cannot remove element in SetIndex iterator");
            }

            public SetIndex next() {
                return get(count++);
            }
        };
    }

    @Override
    public void acceptVisitor(SortingVisitor visitor) {
        visitor.visitSetIndexList(this);
    }

    public List<SetIndex> asList() {
        return this.indexList;
    }
}

