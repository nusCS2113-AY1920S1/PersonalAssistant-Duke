package storage.wallet;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public class Receipt {
    private Double cashSpent;
    private LocalDate date;
    private ArrayList<String> tags;


    /**
     * Complete Constructor for Receipt Object.
     * @param cashSpent Double to be set as cashSpent property of Receipt Object
     * @param date Data Object to be set as the date property of Receipt Object
     * @param tags ArrayList to be set as the tags property of Receipt Object
     */
    public Receipt(Double cashSpent, LocalDate date, ArrayList<String> tags) {
        this.setCashSpent(cashSpent);
        this.setDate(date);
        this.initializeTags(tags);
    }

    /**
     * Partial Constructor for Receipt Object.
     * @param cashSpent Double to be set as cashSpent property of Receipt Object
     * @param date Data Object to be set as the date property of Receipt Object
     */
    public Receipt(Double cashSpent, LocalDate date) {
        this.setCashSpent(cashSpent);
        this.setDate(date);
        this.initializeTags(null);
    }

    /**
     * Partial Constructor for Receipt Object.
     * @param cashSpent Double to be set as cashSpent property of Receipt Object
     */
    public Receipt(Double cashSpent) {
        this.setCashSpent(cashSpent);
        this.setDate(null);
        this.initializeTags(null);
    }

    /**
     * Initializes tags.
     * @param inputTags ArrayList containing any strings passed in during construction.
     */
    private void initializeTags(ArrayList<String> inputTags) {
        ArrayList<String> tags = new ArrayList<>();
        if (this.getCashSpent() >= 0) {
            tags.add("Expenses");
        } else {
            tags.add("Income");
        }
        if (inputTags != null) {
            tags.addAll(inputTags);
        }
        this.tags = tags;
    }

    /**
     * Adds a tag to the tags property of Receipt Object.
     * @param tag String to be stored as a tag
     */
    public void addTag(String tag) {
        this.tags.add(tag);
    }

    // -- Boolean Functions
    /**
     * Checks if the tags property is empty.
     * @return true if tags property is empty, false otherwise.
     */
    public boolean areTagsEmpty() {
        return this.getTags().isEmpty();
    }

    /**
     * Checks if a tag is in the tags property of Receipt Object.
     * @param tag String to be checked
     * @return true if tag exists in the Receipt Object, false otherwise.
     */
    public boolean containsTag(String tag) {
        return this.getTags().contains(tag);
    }

    /**
     * Checks if a date equals to the date property of Receipt Object.
     * @param date String to be checked
     * @return true if date equals tot the one in the Receipt Object, false otherwise.
     */
    public boolean equalsDate(String date) {
        return this.getDate().equals(LocalDate.parse(date));
    }

    // -- Setters & Getters

    /**
     * Setter for cashSpent property of Receipt Object.
     * @param cashSpent Double to be set as cashSpent property of Receipt Object
     */
    public void setCashSpent(Double cashSpent) {
        this.cashSpent = cashSpent;
    }

    /**
     * Getter for cashSpent property of Receipt Object.
     * @return Double corresponding to cashSpent property of Receipt Object
     */
    public Double getCashSpent() {
        return cashSpent;
    }

    /**
     * Setter for tags property.
     * @param tags ArrayList to be set as the tags property of Receipt Object
     */
    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
    }

    /**
     * Getter for the tags property of Receipt Object.
     * @return ArrayList of the tags property of Receipt Object
     */
    public ArrayList<String> getTags() {
        return tags;
    }



    /**
     * Setter for date property.
     * @param date Data Object to be set as the date property of Receipt Object
     */
    public void setDate(LocalDate date) {
        this.date = date;
    }

    /**
     * Getter for date property.
     * @return Date Object of the date property of Receipt Object
     */
    public LocalDate getDate() {
        return date;
    }
}
