package duke.models.student;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import duke.exceptions.DukeException;

import static java.util.Objects.requireNonNull;

/**
 * Stores the student id of the student renting a locker.
 */
public class StudentId {

    public static final String ERROR_MESSAGE = " Matriculation number should contain only "
            + "alpha numeric characters and should have only 9 characters. "
            + "\n\n     It should satisfy the following constraints:"
            + "\n      1. It should start with the character 'A'"
            + "\n      2. It should end with a letter"
            + "\n      3. It should contain only digits between the first and the last character";

    private static final String CHECK_REGEX = "[Aa]\\d{7}[a-zA-Z]";

    private final String studentId;

    /**
     * Instantiates the student ID  of the student.
     * @param studentId stores the student ID of the student
     * @throws DukeException if the student ID is in invalid format
     */
    @JsonCreator
    public StudentId(@JsonProperty("id") String studentId) throws DukeException {
        requireNonNull(studentId);
        if (!checkIsValidStudentId(studentId)) {
            throw new DukeException(ERROR_MESSAGE);
        }
        this.studentId = studentId;
    }


    @JsonGetter("id")
    public String getStudentId() {
        return studentId;
    }

    public static boolean checkIsValidStudentId(String studentId) {
        return studentId.matches(CHECK_REGEX);
    }


    /* Need to override functions equals() and hashCode() in order to account for
       user defined checks about equality while using streams
     */
    @Override
    public boolean equals(Object other) {
        return this == other //checks if the two objects are the same
                || (other instanceof StudentId //checks for all null instances and irrelevant references
                && studentId.equalsIgnoreCase(((StudentId) other).studentId)); //checks for equality
    }

    @Override
    public int hashCode() {
        return studentId.hashCode();
    }
}
