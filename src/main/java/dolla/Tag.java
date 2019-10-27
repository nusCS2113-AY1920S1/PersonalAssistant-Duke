package dolla;

/**
 * This class handles tag related methods.
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

    private String getTagName() {
        return tagName;
    }

    public String toString() {
        return '[' + getTagName() + ']';
    }

    private Boolean hasTag() {
        Boolean hasTag = false;
        int arraySize = inputArray.length;
        if (arraySize > 1 && inputArray[arraySize - 2].equalsIgnoreCase((PREFIX_TAG))) {
            tagIndex = arraySize - 1;
            hasTag = true;
        }
        return hasTag;
    }

    /**
     * Finds the tag.
     */
    private void findTag() {
        tagName = inputArray[tagIndex];
    }

    public void parseTag() { //todo: change to be inside parser folder
        if (hasTag()) {
            findTag();
            //todo: store tag properly instead of printing it out.
            System.out.println("TAG IS " + getTagName());
        }
    }
}
