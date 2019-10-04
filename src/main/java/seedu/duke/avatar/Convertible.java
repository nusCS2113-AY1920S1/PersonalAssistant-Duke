package seedu.duke.avatar;

/**
 * The interface that all classes in avatar must inherit to
 * convert them to save in a txtfile.
 */
public interface Convertible {
    /**
     * Converts avatar into a parsable text format to save in a txt file.
     * @return the String to add into a txt file.
     */
    public String toTxt();
}
