package dolla;

/**
 *
 */
public class Tag {

    public String inputLine;
    public String[] inputArray;
    public String tagName;
    protected int tagIndex;
    public static final String PREFIX_TAG = "/tag";
    public static final String SPACE = " ";

    public Tag(String inputLine) {
        this.inputLine = inputLine;
        this.inputArray = inputLine.split(SPACE);
        this.tagName = null;
        this.tagIndex = - 1;
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return '[' + tagName + ']';
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

    private void findTag() {
        tagName = inputArray[tagIndex];
    }

    public void parseTag() {
        if (hasTag()) {
            findTag();
        }
        System.out.println("TAG IS " + tagName);
    }

    public String getTagName() {
        return tagName;
    }
}
