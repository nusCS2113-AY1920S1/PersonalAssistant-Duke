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
        this.tagName = "";
    }

    private String getTagName() {
        return tagName;
    }

    @Override
    public String toString() {
        if (hasTag()) {
            return " [Tag: " + getTagName() + ']';
        } else {
            return "";
        }
    }

    public String toFormatSave() {
        if (hasTag()) {
            return " | " + getTagName();
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

    private void extractTagName() {
        String[] tagArray = inputLine.split(COMPONENT_TAG);
        tagName = tagArray[1].trim();
        System.out.println(tagName);
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
