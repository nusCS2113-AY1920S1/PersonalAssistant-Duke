package dolla;

/**
 * Package for
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
        for (int i = 0; i < inputArray.length; i++) {
            if (inputArray[i].equalsIgnoreCase(PREFIX_TAG)) {
                tagIndex = i + (PREFIX_TAG.length());
                hasTag = true;
                break;
            }
        }
        return hasTag;
    }

    private void findTag() {
        tagName = inputLine.substring(tagIndex);
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
