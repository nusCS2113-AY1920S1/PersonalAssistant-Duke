package ui;

import java.util.ArrayList;
import java.util.Date;

public class Receipt {
    private Double cashSpent;
    private Date dateBought;
    private ArrayList<String> tags;

    /**
     * Complete Constructor for Receipt Object.
     * @param cashSpent Double to be set as cashSpent property of Receipt Object
     * @param dateBought Data Object to be set as the dateBought property of Receipt Object
     * @param tags ArrayList to be set as the tags property of Receipt Object
     */
    public Receipt(Double cashSpent, Date dateBought, ArrayList<String> tags) {
        this.setCashSpent(cashSpent);
        this.setDateBought(dateBought);
        this.setTags(tags);
    }

    /**
     * Partial Constructor for Receipt Object.
     * @param cashSpent Double to be set as cashSpent property of Receipt Object
     * @param dateBought Data Object to be set as the dateBought property of Receipt Object
     */
    public Receipt(Double cashSpent, Date dateBought) {
        this.setCashSpent(cashSpent);
        this.setDateBought(dateBought);
        this.setTags(null);
    }

    /**
     * Partial Constructor for Receipt Object.
     * @param cashSpent Double to be set as cashSpent property of Receipt Object
     */
    public Receipt(Double cashSpent) {
        this.setCashSpent(cashSpent);
        this.setDateBought(null);
        this.setTags(null);
    }

    /**
     * Adds a tag to the tags property of Receipt Object.
     * @param tag String to be stored as a tag
     */
    public void addTag(String tag) {
        this.tags.add(tag);
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
     * Setter for dateBought property.
     * @param dateBought Data Object to be set as the dateBought property of Receipt Object
     */
    public void setDateBought(Date dateBought) {
        this.dateBought = dateBought;
    }

    /**
     * Getter for dateBought property.
     * @return Date Object of the dateBought property of Receipt Object
     */
    public Date getDateBought() {
        return dateBought;
    }
}
