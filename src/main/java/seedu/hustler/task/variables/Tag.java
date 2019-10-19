package seedu.hustler.task.variables;

/**
 * The class that handles the tags of the Tasks.
 */
public class Tag {

    /**
     * The one-word tag.
     */
    public final String tagName;

    /**
     * Constructs a tag with the string given.
     * @param name the tag name from the user input.
     */
    public Tag(String name) {
        tagName = name;
    }

    /**
     * returns the String of the tagName.
     * @return the tag tagName.
     */
    public String getTagName() {
        return this.tagName;
    }

    @Override
    public String toString() {
        if (!this.tagName.equals("")) {
            return "[#" + this.tagName + "]";
        }
        return "";
    }
}
