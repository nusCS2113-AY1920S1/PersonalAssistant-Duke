import java.util.*;
import java.io.*;
import java.text.*;

/**
 * This class takes in a String of input from the Ui, and depending on the content of
 * the input, parses it into a unique executable command that will carry out the tasks
 * required for that input.
 */

public class Parser {
    /**
     * Checks if an entered date is in the required format. Throws a DukeException otherwise.
     * @param date the date entered by the user
     * @throws DukeException if the format of the entered date is invalid.
     */
    protected void checkParsableDate(String date) throws DukeException {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HHmm");
            Date dateValue = formatter.parse(date);
        }
        catch (ParseException e) {
            throw new DukeException("Please specify the date using the following format: dd/MM/yyyy HHmm");
        }
    }

    /**
     * Parses the input obtained by the Ui from the user into an executable command.
     * @param input the input obtained from the user by the Ui.
     * @return a Command that can be executed to carry out the necessary tasks
     * @throws DukeException if the input is in a wrong format or does not make sense.
     */
    public Command parseInput(String input) throws DukeException, ParseException {
        Command c;
        String[] words = input.split(" ");
        if (input.equals("bye") && words.length == 1) {
            c = new CloseCommand();
        }
        else if (input.equals("list") && words.length == 1) {
            c = new ListCommand();
        }
        else if (words[0].equals("done") && words.length <= 2) {
            if (words.length == 1) {
                throw new DukeException("Please specify the task number you want to mark as done.");
            }
            if (!(words[1].matches("[1-9][0-9]*"))) {
                throw new DukeException("That's an invalid task number!");
            }
            c = new DoneCommand(words[1]);
        }
        else if (words[0].equals("delete") && words.length <= 2) {
            if (words.length == 1) {
                throw new DukeException("Please specify the task number you want to mark as done.");
            }
            if (!(words[1].matches("[1-9][0-9]*"))) {
                throw new DukeException("That's an invalid task number!");
            }
            c = new DeleteCommand(words[1]);
        }
        else if (words[0].equals("find")) {
            if (words.length == 1) {
                throw new DukeException("Tell me what you want me to find.");
            }
            String query = "";
            for (int i = 1; i < words.length; i++) {
                query += words[i];
                if (i != words.length - 1) {
                    query += " ";
                }
            }
            query = query.toLowerCase();
            c = new FindCommand(query);
        }
        else if (words[0].equals("todo")) {
            if (!(words.length > 1)) {
                throw new DukeException("The description of a todo cannot be empty.");
            }
            input = input.replaceFirst("todo ", "");
            c = new TodoCommand(input);
        }
        else if (words[0].equals("deadline")) {
            if (!(words.length > 1)) {
                throw new DukeException("The description of a deadline cannot be empty.");
            }
            input = input.replaceFirst("deadline ", "");
            int splitIndex = input.indexOf("/by");
            if (splitIndex == -1) {
                throw new DukeException("Please specify the deadline date/time using the '/by' command.");
            }
            else if (splitIndex == 0) {
                throw new DukeException("The description of a deadline cannot be empty.");
            }
            String description = input.substring(0, splitIndex - 1);
            if (description.isEmpty()) {
                throw new DukeException("The description of a deadline cannot be empty.");
            }
            String by = input.substring(splitIndex + 1);
            by = by.replaceFirst("by ", "");
            if (by.equals("by")) {
                throw new DukeException("The deadline cannot be empty.");
            }
            checkParsableDate(by);
            c = new DeadlineCommand(description, by);
        }
        else if (words[0].equals("event")) {
            if (!(words.length > 1)) {
                throw new DukeException("The description of an event cannot be empty.");
            }
            input = input.replaceFirst("event ", "");
            int splitIndex = input.indexOf("/at");
            if (splitIndex == -1) {
                throw new DukeException("Please specify the event date using the '/at' command.");
            }
            else if (splitIndex == 0) {
                throw new DukeException("The description of an event cannot be empty.");
            }
            String description = input.substring(0, splitIndex - 1);
            if (description.isEmpty()) {
                throw new DukeException("The description of an event cannot be empty.");
            }
            String at = input.substring(splitIndex + 1);
            at = at.replaceFirst("at ", "");
            if (at.equals("at")) {
                throw new DukeException("The event date cannot be empty.");
            }
            checkParsableDate(at);
            c = new EventCommand(description, at);
        }
        else {
            throw new DukeException("I'm sorry, but I don't know what that means :-(");
        }
        return c;
    }
}