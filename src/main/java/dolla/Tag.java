package dolla;

import dolla.parser.Parser;
import dolla.task.Record;
import dolla.ui.Ui;

import static dolla.parser.ParserStringList.COMPONENT_TAG;

/**
 * This class handles tag related methods.
 */
//@@author Weng-Kexin
public class Tag {

    private String tagName;
    private String[] inputArray = Parser.getInputArray();

    /**
     * Instantiates a new Tag.
     */
    public Tag() {
        this.tagName = "";
    }

    private String getTagName() {
        return tagName;
    }

    public String toString() {
        if (hasTag()) {
            return "[Tag: " + getTagName() + ']';
        } else {
            return "";
        }
    }

    private Boolean hasTag() {
        boolean hasTag = false;
        for (int i = 0; i < inputArray.length - 1; i++) {
            if (inputArray[i].equals(COMPONENT_TAG)) {
                hasTag = true;
                break;
            }
        }
        return hasTag;
    }

    private void extractTagName(String inputLine) {
        String[] tagArray = inputLine.split(COMPONENT_TAG);
        tagName = tagArray[1].trim();
    }

    /**
     * Method handles input to check for tag and store it.
     */
    public void handleTag(String inputLine, String[] inputArray, Record record) { //todo: change to inside parser
        if (hasTag()) {
            extractTagName(inputLine);
            Dolla.tagList.addTag(tagName, record); //todo: find out how to store
            Ui.printAddedTagMsg(tagName);
        }
    }
}
