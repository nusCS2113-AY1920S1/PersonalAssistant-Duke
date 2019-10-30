package duke.command;

/**
 * Represents a subclass of Command class which ends the program
 * from any part of the program.
 */
public class ExitCommand {

    /**
     * The method to exit the program from any part of the program.
     */
    public void exitProgram() {
        System.exit(0);
    }

    /**
     * The method to exit the Goal of the day.
     * @return Returns the false value to exit Goal of the day.
     */
    public boolean exitGoal() {
        return false;
    }

    /**
     * The method to exit the Lesson of the day.
     * @return Returns the false value to exit Lesson of the day.
     */
    public boolean exitLesson() {
        return false;
    }
}
