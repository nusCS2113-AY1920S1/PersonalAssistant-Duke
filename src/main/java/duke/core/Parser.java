package duke.core;

import duke.command.*;
import duke.exception.*;
import duke.task.*;

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
    protected void checkParsableDateTime(String date) throws DukeException {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HHmm");
            Date dateValue = formatter.parse(date);
        }
        catch (ParseException e) {
            throw new DukeException("Please specify the date using the following format: dd/MM/yyyy HHmm");
        }
    }

    protected void checkParsableScheduleDate(String date) throws DukeException {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            Date dateValue = formatter.parse(date);
            if (date.length() != 10) {
                throw new DukeException("Please specify the schedule date using the following format: dd/MM/yyyy");
            }
        }
        catch (ParseException e) {
            throw new DukeException("Please specify the schedule date using the following format: dd/MM/yyyy");
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

        else if (words[0].equals("schedule")) {
            if (words.length == 1) {
                throw new DukeException("Please specify the date you want to view the schedule for.");
            }
            String dateEntered = words[1];
            checkParsableScheduleDate(dateEntered);
            c = new ViewScheduleCommand(dateEntered);
        }

        else if (words[0].equals("todo")) {
            if (!(words.length > 1)) {
                throw new DukeException("The description of a todo cannot be empty.");
            }
            input = input.replaceFirst("todo ", "");
            if(input.contains("/after")){
                int splitIndex = input.indexOf("/after");
                if (splitIndex == 0){
                    throw new DukeException( "The description of a todo cannot be empty.");
                }
                String description =  input.substring(0, splitIndex-1);
                String after = input.substring(splitIndex);
                after = after.replaceFirst("/after", "");
                if (after.equals("") || after.equals(" ")){
                    throw new DukeException("The the after time cannot be empty.");
                }

                c = new TodoCommand(description, after);
            }
            else {
                c = new TodoCommand(input);
            }
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
            // why got double error?
            String by = input.substring(splitIndex + 1);
            by = by.replaceFirst("by ", "");
            if (by.equals("by")) {
                throw new DukeException("The deadline cannot be empty.");
            }
            checkParsableDateTime(by);
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
            checkParsableDateTime(at);
            c = new EventCommand(description, at);
        }
        else if (words[0].equals("recurring")) {
            if (!(words.length > 1)) {
                throw new DukeException("The description of a recurring task cannot be empty.");
            }
            input = input.replaceFirst("recurring ", "");
            int daySplitIndex = input.indexOf("/every");
            if (daySplitIndex == -1) {
                throw new DukeException("Please specify the recurring task day using the '/every' command.");
            }
            else if (daySplitIndex == 0) {
                throw new DukeException("The description of a task cannot be empty!");
            }
            String description = input.substring(0, daySplitIndex - 1);
            if (description.isEmpty()) {
                throw new DukeException("The description of a task cannot be empty.");
            }
            int timeSplitIndex = input.indexOf("/at");
            if (timeSplitIndex == -1) {
                throw new DukeException("Please specify the recurring task timing as well, using the '/at' command.");
            }
            String day = input.substring(daySplitIndex + 1, timeSplitIndex - 1);
            day = day.replaceFirst("every ", "");
            if (day.equals("every")) {
                throw new DukeException("The recurring task day cannot be empty.");
            }
            String time = input.substring(timeSplitIndex + 1);
            time = time.replaceFirst("at ", "");
            if (time.equals("at")) {
                throw new DukeException("The recurring task time cannot be empty.");
            }
            c = new RecurringCommand(description, day, time);
        }
        else {
            throw new DukeException("I'm sorry, but I don't know what that means :-(");
        }
        return c;
    }
}