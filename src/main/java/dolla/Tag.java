package dolla;

/**
<<<<<<< .merge_file_a07256
 * This class handles tag related methods.
=======
 * This class handles tag related inputs.
>>>>>>> .merge_file_a11240
 */
public class Tag {

    protected String inputLine;
    private String[] inputArray;
    private String tagName;
    private int tagIndex;
    private static final String PREFIX_TAG = "/tag";
    private static final String SPACE = " ";

    /**
     * Instantiates a new Tag.
     *
     * @param inputLine the input line
     */
    public Tag(String inputLine) {
        this.inputLine = inputLine;
        this.inputArray = inputLine.split(SPACE);
        this.tagName = null;
        this.tagIndex = - 1;
    }

    /**
     * Formats state as text for viewing.
     */
    public String toString() {
        return '[' + tagName + ']';
    }

    /**
     * Checks if inputArray has tag and changes tagIndex to the index of the tag name.
     * @return true if inputArray contains "/tag"
     */
    private Boolean hasTag() {
        Boolean hasTag = false;
        int arraySize = inputArray.length;
        if (arraySize > 1 && inputArray[arraySize - 2].equalsIgnoreCase((PREFIX_TAG))) {
            tagIndex = arraySize - 1;
            hasTag = true;
        }
        return hasTag;
    }

    private void findTag() {
        tagName = inputArray[tagIndex];
    }

    /**
<<<<<<< .merge_file_a07256
     * Parses tag.
     */
    public void parseTag() { //todo: change to be inside parser folder
=======
     * Parse tag.
     */
    public void parseTag() {
>>>>>>> .merge_file_a11240
        if (hasTag()) {
            findTag();
            //todo: store tag properly instead of printing it out.
            System.out.println("TAG IS " + getTagName());
        }
    }

<<<<<<< .merge_file_a07256
=======
    /**
     * Gets tag name.
     *
     * @return the tag name
     */
>>>>>>> .merge_file_a11240
    public String getTagName() {
        return tagName;
    }
}
