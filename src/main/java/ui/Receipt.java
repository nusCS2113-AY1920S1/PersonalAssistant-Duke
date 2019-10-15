package ui;

import java.util.ArrayList;
import java.util.Date;

public class Receipt {
    private Double cashSpent;
    private Date date;
    private ArrayList<String> tags;

    /**
     * Complete Constructor for Receipt Object.
     * @param cashSpent Double to be set as cashSpent property of Receipt Object
     * @param date Data Object to be set as the date property of Receipt Object
     * @param tags ArrayList to be set as the tags property of Receipt Object
     */
    public Receipt(Double cashSpent, Date date, ArrayList<String> tags) {
        this.setCashSpent(cashSpent);
        this.setDate(date);
        this.setTags(tags);
    }

    /**
     * Partial Constructor for Receipt Object.
     * @param cashSpent Double to be set as cashSpent property of Receipt Object
     * @param date Data Object to be set as the date property of Receipt Object
     */
    public Receipt(Double cashSpent, Date date) {
        this.setCashSpent(cashSpent);
        this.setDate(date);
        this.setTags(new ArrayList<String>());
    }

    /**
     * Partial Constructor for Receipt Object.
     * @param cashSpent Double to be set as cashSpent property of Receipt Object
     */
    public Receipt(Double cashSpent) {
        this.setCashSpent(cashSpent);
        this.setDate(null);
        this.setTags(new ArrayList<String>());
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
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * Getter for date property.
     * @return Date Object of the date property of Receipt Object
     */
    public Date getDate() {
        return date;
    }
}
