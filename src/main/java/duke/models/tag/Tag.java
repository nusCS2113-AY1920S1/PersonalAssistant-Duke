package duke.models.tag;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import duke.exceptions.DukeException;

import static java.util.Objects.requireNonNull;

/**
 * Stores the status of a locker.
 */
public class Tag {
    public String tagName;

    public static final String IN_USE = "in-use";
    public static final String NOT_IN_USE = "not-in-use";
    public static final String UNAUTHORIZED = "unauthorized";
    public static final String BROKEN = "broken";
    public static final String INVALID_TAG_NAME = "Tag names can either be:\n"
            + "\n     1.<in-use>"
            + "\n     2.<not-in-use>"
            + "\n     3.<unauthorized>"
            + "\n     4.<broken>";

    /**
     * Instantiates the status of the locker that it is associated with.
     * @param tagName stores a valid tagName
     * @throws DukeException if the tagName is invalid
     */
    @JsonCreator
    public Tag(@JsonProperty("tagName") String tagName) throws DukeException {
        requireNonNull(tagName);
        if (!checkValidTagName(tagName)) {
            throw new DukeException(INVALID_TAG_NAME);
        }
        this.tagName = tagName.toLowerCase();
    }

    /**
     * Checks if the tag name is valid or not i.e whether it is in-use, not-in-use, available
     * or broken.
     * @param test stores the string that is to be tested
     * @return true if the tag name is valid
     */
    public static boolean checkValidTagName(String test) {
        return (test.equalsIgnoreCase(IN_USE) || test.equalsIgnoreCase(NOT_IN_USE)
                || test.equalsIgnoreCase(UNAUTHORIZED) || test.equalsIgnoreCase(BROKEN));
    }

    @Override
    public String toString() {
        return "[" + tagName + "]";
    }

    @JsonGetter("tagName")
    public String getTagName() {
        return tagName;
    }


    /* Need to override function equals() and hashCode() in order to account
       for user defined checks for equality while using streams
     */
    @Override
    public boolean equals(Object other) {
        return this == other //short circuit for being the same object
                || (other instanceof Tag //handles all null cases and other irrelevant instances
                && tagName.equalsIgnoreCase(((Tag) other).tagName));//checks for equality
    }

    @Override
    public int hashCode() {
        return tagName.hashCode();
    }
}
