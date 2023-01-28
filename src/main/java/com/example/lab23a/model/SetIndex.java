package com.example.lab23a.model;

import java.time.LocalDateTime;

/**
 * Class that contains general information about study set without loading its term list
 */
public class SetIndex {
	private String name;
	int elementsCount;
	int elementsMastered;
	
	private int index;
    private LocalDateTime lastStudied;
    private LocalDateTime created;

    /**
     *
     * @param index - unique number of the set index
     * @param name - set's name (will be displayed in UI)
     * @param elementsCount - elements in the corresponding term list. Mastered terms will be considered as 0
     */
    public SetIndex(int index, String name, int elementsCount) {
        this.index = index;
        this.name = name;
        this.elementsCount = elementsCount;
        this.created = Dates.currentDate();
        this.lastStudied = created;
    }
    /**
     *
     * @param index - unique number of the set index
     * @param name - set's name (will be displayed in UI)
     * @param elementsCount - elements in the corresponding term list.
     * @param elementsStudied - elements that has 'Mastered' level
     */
    public SetIndex(int index, String name, int elementsCount, int elementsStudied) {
        this(index, name, elementsCount);
        this.elementsMastered = elementsStudied;
    }

    /**
     *
     * @param index is unique number of the study set
     */
    public SetIndex(int index) {
        this.index = index;
        this.created = Dates.currentDate();
        this.lastStudied = created;
    }

    /**
     *
     * @param index - unique number of study set
     * @param name - name of the study set
     * @param size - number of terms in the corresponding term list
     * @param created - date of creation of the set
     * @param lastStudied - date, when the set was opened in study mode
     */
    public SetIndex(int index, String name, int size, LocalDateTime created, LocalDateTime lastStudied) {
        this.index = index;
        this.name = name;
        this.elementsCount = size;
        this.created = created;
        this.lastStudied = lastStudied;
    }

    /**
     *
     * @return date and time, when the set was created
     */
    public LocalDateTime getCreatedDate() {
        return created;
    }

    /**
     *
     * @return date and time, when the set was opened in write mode
     */
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
    /**
     * sets last studied date to current date
     */
    public void updateLastStudied() {
        this.lastStudied = Dates.currentDate();
    }

    /**
     *
     * @return the name of the study set, that will be displayed in GUI
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param count is max allowed length of name
     * @return set's name if its length is less than {@param count}, and short version of name otherwise
     */
    public String getNameShort(int count) {
        String toReturn = getNotEmptyName();
        return toReturn.length() > count ? toReturn.substring(0, count) + "..." : toReturn;
    }

    /**
     *
     * @return name if the set has a name or "StudySet" if it doesn't
     */
    public String getNotEmptyName() {
        return name.isEmpty() ? "StudySet" : name;
    }

    /**
     *
     * @param name is a new name of the Study set
     */
    public void setName(String name) {
        this.name = name;
    }

    public int getID() {
        return index;
    }
    public void setID(int id) {
        this.index = id;
    }

    /**
     *
     * @return number of terms in the set
     */
    public int getElementsCount() {
        return elementsCount;
    }

    /**
     *
     * @param elementsCount is number of elements in the set
     */
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

    /**
     *
     * @param elements is how many terms are mastered (completely learnt) by the user
     */
    public void setElementsMastered(int elements) {
        this.elementsMastered = elements;
    }
    
    @Override
    public String toString() {
        return "[" + index + ' ' + name + ' ' + elementsCount + ' ' + created + ' ' + lastStudied + "]";
    }

    /**
     *
     * @param date is the date, when set was created
     */
	public void setCreatedDate(LocalDateTime date) {
		this.created = date;
	}
	@Override
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof SetIndex newIndex))
			return false;
        return newIndex.index == this.index && newIndex.name.equals(this.name) && newIndex.elementsCount == this.elementsCount;
	}
	
}
