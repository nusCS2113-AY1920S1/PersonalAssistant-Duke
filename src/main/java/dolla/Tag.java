package dolla;

import dolla.parser.Parser;
import dolla.task.Record;

import static dolla.parser.ParserStringList.COMPONENT_TAG;

//@@author Weng-Kexin
public class Tag {

    private String tagName;
    private String[] inputArray = Parser.getInputArray();
    private String inputLine = Parser.getInputLine();

    /**
     * Instantiates a new Tag.
     */
    public Tag() {
        this.tagName = "-";
    }

    public String getTagName() {
        return tagName;
    }

    @Override
    public String toString() {
        return " {Tag: " + tagName + '}';
    }

    private Boolean hasTag() {
        boolean hasTag = false;
        for (int i = 0; i < inputArray.length - 1; i++) {
            if (inputArray[i].equals(COMPONENT_TAG) && i > 4) {
                hasTag = true;
                break;
            }
        }
        return hasTag;
    }

    private void extractTagName() {
        String[] tagArray = inputLine.split(COMPONENT_TAG);
        tagName = tagArray[1].trim();
    }

    /**
     * Method handles input to check for tag and store it.
     */
    public void handleTag(Record record) {
        if (hasTag()) {
            extractTagName();
            record.setTagName(tagName);
        }
    }
}
