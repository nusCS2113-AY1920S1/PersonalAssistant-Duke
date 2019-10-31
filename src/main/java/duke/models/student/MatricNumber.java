package duke.models.student;

import duke.exceptions.DukeException;

import static java.util.Objects.requireNonNull;

public class MatricNumber {

    public static final String ERROR_MESSAGE = " Matriculation number should contain only "
            + "alpha numeric characters and should have only 9 characters. "
            + "\n\n     It should satisfy the following constraints:"
            + "\n      1. It should start with the character 'A'"
            + "\n      2. It should end with a letter"
            + "\n      3. It should contain only digits between the first and the last character";

    public static final String CHECK_REGEX = "[Aa]\\d{7}[a-zA-Z]";

    public final String matricId;

    /**
     * This constructor instantiates the student ID / the matric number of the student.
     * @param matricId stores the matric number of the student
     * @throws DukeException when the matric number is in invalid format
     */
    public MatricNumber(String matricId) throws DukeException {
        requireNonNull(matricId);
        if (!checkIsValidMatricNumber(matricId)) {
            throw new DukeException(ERROR_MESSAGE);
        }
        this.matricId = matricId;
    }

    public static boolean checkIsValidMatricNumber(String matricId) {
        return matricId.matches(CHECK_REGEX);
    }

    public String getStudentCourse() {
        return matricId;
    }

    /* We need to override functions equals() and hashCode() in order to account for
       user defined checks about equality while using streams
     */
    @Override
    public boolean equals(Object other) {
        return this == other //checks if the two objects are the same
                || (other instanceof MatricNumber //checks for all null instances and irrelevant references
                && matricId.equalsIgnoreCase(((MatricNumber) other).matricId)); //checks for equality
    }

    @Override
    public int hashCode() {
        return matricId.hashCode();
    }
}
