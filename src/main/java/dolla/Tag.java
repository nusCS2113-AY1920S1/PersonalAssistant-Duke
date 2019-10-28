package dolla;

import dolla.task.Record;
import dolla.ui.Ui;

/**
 * This class handles tag related methods.
 */
public class Tag {

    private String tagName;
    private static final String PREFIX_TAG = "/tag";

    /**
     * Instantiates a new Tag.
     */
    public Tag() {
        this.tagName = null;
    }

    public String getTagName() {
        return tagName;
    }

    public String toString() {
        return '[' + getTagName() + ']';
    }

    private Boolean hasTag(String[] inputArray) {
        boolean hasTag = false;
        for (int i = 0; i < inputArray.length - 1; i++) {
            if (inputArray[i].equalsIgnoreCase(PREFIX_TAG)) {
                hasTag = true;
                break;
            }
        }
        return hasTag;
    }

    private void extractTagName(String inputLine) {
        String[] tempArray = inputLine.split(PREFIX_TAG);
        tagName = tempArray[1].trim();
    }

    /**
     * Method handles input to check for tag and store it.
     */
    public void handleTag(String inputLine, String[] inputArray, Record record) { //todo: change to be inside parser folder
        if (hasTag(inputArray)) {
            extractTagName(inputLine);
            dolla.Dolla.tagList.addTag(tagName, record); //todo: find out how to store
            Ui.printAddedTagMsg(tagName);
        }
    }
}
