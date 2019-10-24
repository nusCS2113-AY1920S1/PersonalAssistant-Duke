package model.task;
import static java.util.Objects.requireNonNull;

public class Name {
    //@@author Justin Chia
    //TODO change this according to our project requirements
    public static final String MESSAGE_REQUIREMENTS =
            "Names should only contain alphanumeric characters and spaces, and it should not be blank";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String taskName;

    public Name(String taskName){
        requireNonNull(taskName);
        this.taskName = taskName;
    }

    @Override
    public String toString(){
        return taskName;
    }

}
