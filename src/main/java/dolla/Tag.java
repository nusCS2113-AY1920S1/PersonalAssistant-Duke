package dolla;

import dolla.command.ErrorCommand;
import dolla.ui.LimitUi;

/**
 * Package for
 */
public class Tag {

    public String inputLine;
    public static final String PREFIX_TAG = "/tag";
    public final String tagName;

    public Tag(String inputLine) {
        this.inputLine = inputLine;
        this.tagName = null;
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return '[' + tagName + ']';
    }

    public Boolean isTagged(String inputLine) {
        boolean hasPrefix = true;
        int indexOfTag = inputLine.indexOf(PREFIX_TAG);
        if (indexOfTag == -1) {
            hasPrefix = false;
        }
        return hasPrefix;
    }
}
