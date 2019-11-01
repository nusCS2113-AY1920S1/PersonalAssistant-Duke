package dolla;

import dolla.task.Record;
import dolla.ui.Ui;

import static dolla.ModeStringList.PREFIX_TAG;

/**
 * This class handles tag related methods.
 */
//@@author Weng-Kexin
public class Tag {

    private String tagName;

    /**
     * Instantiates a new Tag.
     */
    public Tag() {
        this.tagName = "";
    }

    public String getPrefixTag() {
        return PREFIX_TAG;
    }

    private String getTagName() {
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
        String[] tagArray = inputLine.split(PREFIX_TAG);
        tagName = tagArray[1].trim();
    }

    /**
     * Method handles input to check for tag and store it.
     */
    public void handleTag(String inputLine, String[] inputArray, Record record) { //todo: change to inside parser
        if (hasTag(inputArray)) {
            extractTagName(inputLine);
            Dolla.tagList.addTag(tagName, record); //todo: find out how to store
            Ui.printAddedTagMsg(tagName);
        }
    }
}
