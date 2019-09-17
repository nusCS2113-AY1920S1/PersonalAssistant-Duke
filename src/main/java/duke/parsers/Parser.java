package duke.parsers;
import duke.commands.*;
import duke.exceptions.DukeException;
import duke.tasks.Deadline;
import duke.tasks.Event;
import duke.tasks.ToDo;


/**
 * Parser is a public class that help to parse the command that is inputted from the user
 * And generate the appropriate command with their appropriate arguments
 */
public class Parser {
    /**
     * This is the main function that parse the command inputted by the user
     * @param fullCommand the string the user input in the CLI
     * @return <code>new ExitCommand()</code> if the user input "bye"
     *         <code>new AddCommand(new ToDo())</code> if the user input "todo" followed by the description of the activity
     *         <code>new AddCommand(new Event()</code> if the user input "event" followed by the time the event is held
     *         <code>new ListCommand()</code> if the user input list
     *         <code>new MarkDoneCommand(index)</code> if the user input "done" followed by the index of the task to be marked done
     *         <code>new FindCommand(description)</code> if the user input "find" followed by the string that needs to be added
     *         <code>new DeleteCommand(index) </code> if the sure input "delete" followed by the index of the task to be deleted
     * @throws DukeException either there is no description in "done", "todo", "event", and "deadline" command
     *                       or the command is not recognized
     */
    public static Command parse(String fullCommand) throws DukeException {
        //TODO: Put error for invalid input and what not
        String[] splitCommand = fullCommand.split(" ", 2);
        String command = splitCommand[0];
        String description = "";

        if (splitCommand.length >= 2) {
            description = splitCommand[1];
        }
        if (command.equals("done") || command.equals("todo") || command.equals("event") || command.equals("deadline")) {
            if (description.trim().length() == 0) {
                throw new DukeException("\u2639 OOPS!!! The description of a " + command + " cannot be empty.");
            }
        }
        if (command.equals("bye")) {
            return new ExitCommand();
        } else if (command.equals("todo")) {
            if (description.contains("/needs")) {
                try {
                    String SplitString[] = description.split(" /needs ", 2);
                    return new AddCommand(new ToDo(SplitString[0], SplitString[1]));
                } catch (Exception e) {
                    throw new DukeException("\u2639 OOPS!!! The todo command does not seem to be valid.");
                }
            }
            else if (description.contains("/between")) {
                try {
                    String SplitString[] = description.split("/between", 2);
                    String SplitString2[] = SplitString[1].split(",", 2);
                    return new AddCommand(new ToDo(SplitString[0], SplitString2[0], SplitString2[1]));
                } catch (Exception e) {
                    throw new DukeException("\u2639 OOPS!!! The todo command does not seem to be valid.");
                }
            }
            return new AddCommand(new ToDo(description));
        } else if (command.equals("deadline")) {
            try {
                String SplitString[] = description.split(" /by ");
                return new AddCommand(new Deadline(SplitString[0], SplitString[1]));
            } catch (Exception e) {
                throw new DukeException("\u2639 OOPS!!! The deadline command does not seem to be valid.");
            }
        } else if (command.equals("event")) {
            String SplitString[];
            try {
                SplitString = description.split(" /at ");
                return new AddCommand(new Event(SplitString[0], SplitString[1]));
            } catch (Exception e) {
                throw new DukeException("\u2639 OOPS!!! The event command does not seem to be valid.");
            }
        } else if (command.equals("list")) {
            return new ListCommand();
        } else if (command.equals("done")) {
            int index = Integer.parseInt(description);
            return (new MarkDoneCommand(index));
        } else if (command.equals("find")) {
            return new FindCommand(description);
        } else if (command.equals("delete")) {
            int index = 0;
            try {
                index = Integer.parseInt(description);
            }
            catch (NumberFormatException e){
                throw new DukeException("Please enter a number");
            }
            return new DeleteCommand(index);
        } else if (command.equals("remindme")) {
            int index = 0;
            try {
                index = Integer.parseInt(description);
            }
            catch (NumberFormatException e){
                throw new DukeException("Please enter a number");
            }
            return new RemindCommand(index);
        } else if (command.equals("findfreetime")){
            int index = 0;
            try {
                index = Integer.parseInt(description);
            }
            catch (NumberFormatException e){
                throw new DukeException("Please enter a number");
            }
            return new FindFreeTimeCommand(index);
        } else if (command.equals("snooze")){
            int index1, index2;
            try {
                index1 = Integer.parseInt(description.split(" ", 2)[0]);
                index2 = Integer.parseInt(description.split(" ", 2)[1]);
            }
            catch (NumberFormatException e){
                throw new DukeException("Please enter a number");
            }
            if (index1 < 1 && index2 > 31 || index2 < 0 || index2 > 23){
                throw new DukeException("Improper day and hour assignment");
            }
            return new SnoozeCommand(index1,index2);
        } else if (command.equals("tentative")){
            return new TentativeCommand();
        } else if (command.equals("confirm")){
            return new ConfirmCommand();
        } else {
            throw new DukeException("\u2639 OOPS!!! I'm sorry, but I don't know what that means :-(");
        }
    }

}
