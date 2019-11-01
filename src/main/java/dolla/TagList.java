package dolla;

import dolla.task.Record;

import java.util.HashMap;

//@@author Weng-Kexin
public class TagList {

    private static HashMap<String, Record> tagList = new HashMap<>(); //change it to store the index of the record

    /**
     * Method to add tag to tag container (hashmap).
     * @param tag     Tag to be added
     * @param record  Record the tag is added with
     */
    protected void addTag(String tag, Record record) {
        try {
            tagList.put(tag, record);
        } catch (Exception e) { //todo: change
            System.out.println("error handling tag");
        }
    }
}
