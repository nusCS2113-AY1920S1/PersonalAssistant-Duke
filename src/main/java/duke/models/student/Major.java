package duke.models.student;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import duke.exceptions.DukeException;

import static java.util.Objects.requireNonNull;

/**
 * Stores the major/course pursued by the student renting a locker.
 */
public class Major {

    public static final String ERROR_MESSAGE = " Major should contain only alphanumeric characters"
            + " and spaces and it should not be empty";

    private static final String CHECK_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    private final String course;

    /**
     * Instantiates the course/major of the student.
     * @param course stores the course that the student is currently pursuing
     * @throws DukeException when the course is in invalid format
     */
    @JsonCreator
    public Major(@JsonProperty("major") String course) throws DukeException {
        requireNonNull(course);
        if (!checkIsValidCourse(course)) {
            throw new DukeException(ERROR_MESSAGE);
        }
        this.course = course;
    }

    /**
     * Checks if the course pursued by the student is valid or not.
     * A valid course contains only alpha numeric characters
     * @param course stores the course tested for its validity
     * @return true if the course is valid
     */
    public static boolean checkIsValidCourse(String course) {
        return course.matches(CHECK_REGEX);
    }

    @JsonGetter("major")
    public String getCourse() {
        return course;
    }

    /* We need to override functions equals and hashCode in order to account for
       user defined checks for equality while using streams
     */
    @Override
    public boolean equals(Object other) {
        return this == other //short circuit if both the objects are the same
                || (other instanceof Major //handles all null instances and other irrelevant references
                && course.equalsIgnoreCase(((Major) other).course)); //checks for equality
    }

    @Override
    public int hashCode() {
        return course.hashCode();
    }
}
