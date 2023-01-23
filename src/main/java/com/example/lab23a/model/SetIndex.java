package com.example.lab23a.model;

import java.time.LocalDateTime;

public class SetIndex {
	String name;
	int elementsCount;
	int elementsMastered;
	
	private int index;
    private LocalDateTime lastStudied;
    private LocalDateTime created;
    
    public SetIndex(int index, String name, int elementsCount) {
        this.index = index;
        this.name = name;
        this.elementsCount = elementsCount;
        this.created = Dates.currentDate();
        this.lastStudied = created;
    }
    public SetIndex(int index, String name, int elementsCount, int elementsStudied) {
        this(index, name, elementsCount);
        this.elementsMastered = elementsStudied;
    }
    public SetIndex(int index) {
        this.index = index;
        this.created = Dates.currentDate();
        this.lastStudied = created;
    }
    public LocalDateTime getCreatedDate() {
        return created;
    }
    public LocalDateTime getLastStudied() {
        return lastStudied;
    }
    /**
     * 
     * @param lastStudied is the date, when study term was last time studied in Write mode
     */
    public void setLastStudied(LocalDateTime lastStudied) {
        this.lastStudied = lastStudied;
    }

    public SetIndex(int index, String name, int size, LocalDateTime created, LocalDateTime lastStudied) {
        this.index = index;
        this.name = name;
        this.elementsCount = size;
        this.created = created;
        this.lastStudied = lastStudied;
    }
    /**
     * sets last studied date to current date
     */
    public void updateLastStudied() {
        this.lastStudied = Dates.currentDate();
    }

    public String getName() {
        return name;
    }
    public String getNameShort(int count) {
        String toReturn = getNotEmptyName();
        return toReturn.length() > count ? toReturn.substring(0, count) + "..." : toReturn;
    }
    public String getNotEmptyName() {
        return name.isEmpty() ? "StudySet" : name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getID() {
        return index;
    }
    public void setID(int id) {
        this.index = id;
    }

    public int getElementsCount() {
        return elementsCount;
    }

    public void setElementsCount(int elementsCount) {
        this.elementsCount = elementsCount;
    }
    /**
     * 
     * @return number of terms that has "Mastered" status;
     * THis value is calculated, when user finishes Write mode or when set is being edited
     */
    public int getElementsMastered() {
        return elementsMastered;
    }

    public void setElementsMastered(int elements) {
        this.elementsMastered = elements;
    }
    
    @Override
    public String toString() {
        return "[" + index + ' ' + name + ' ' + elementsCount + ' ' + created + ' ' + lastStudied + "]";
    }
	public void setCreatedDate(LocalDateTime time) {
		this.created = time;
	}
	@Override
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof SetIndex))
			return false;
		SetIndex a = (SetIndex) other;
		return a.index == this.index && a.name.equals(this.name) && a.elementsCount == this.elementsCount;
	}
	
}
