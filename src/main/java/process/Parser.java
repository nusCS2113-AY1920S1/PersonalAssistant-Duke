package process;

import command.*;
import process.DukeException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Represents a parser to make sense of user input and translate it to commands for Duke
 */
public class Parser {
    /**
     * Interprets user input
     * @param input to be interpreted
     * @return Duke commands based on user input
     * @throws DukeException if Duke cannot make sense of the input
     */
    public static Command parse(String input) throws DukeException { //input validation
        ArrayList<String> command_list = new ArrayList<String>(Arrays.asList("bye", "list", "find", "delete", "done", "todo", "deadline", "event","reschedule", "task", "daily", "weekly", "reminder"));
        String operation;
        String date;
        int index =-1;
        String arg1;
        int command_status = -1;
        if (!input.isBlank()) command_status = command_list.indexOf(input.split(" ")[0]);
        else {
            throw new DukeException("unknown");
        }
        if (command_status > -1) { //keyword 1 is accepted
            String[] operation_list = input.split(" ");
            operation = operation_list[0];
            if ((operation.equals("delete") || operation.equals("done") || operation.equals("reminder") )) {
                if (operation_list.length != 2) throw new DukeException("index error" + input.length());
                try {
                    index = Integer.parseInt(operation_list[1]) -1;
                    if (index < 0) throw new DukeException("index error");
                } catch (NumberFormatException e) {
                    throw new DukeException("index error");
                }
                if (operation.equals("delete")) return new DeleteCommand(index);
                else if(operation.equals("reminder")) return new ReminderCommand(index);
                else return new DoneCommand(index); //done
            } else if (operation.equals("find") || operation.equals("todo")) {
                if (operation_list.length == 1) throw new DukeException("arg1 error "+ operation);
                arg1 = input.substring(5);
                if (arg1.isBlank()) throw new DukeException("arg1 error "+ operation);
                arg1.trim();
                if (operation.equals("find")) return new FindCommand(arg1);
                else return new AddCommand("todo", arg1);
            } else if (operation.equals("deadline")) {
                int by_index = input.indexOf(" /by ");
                if (by_index == -1) throw new DukeException("datetime");
                arg1 = input.substring(8, by_index).trim();
                if (arg1.isBlank()) throw new DukeException("arg1 error "+ operation);
                date = input.substring(by_index + 5).trim();
                if (date.isBlank()) throw new DukeException("datetime");
                return new AddCommand("deadline", arg1, date);
            } else if (operation.equals("event")) {
                int at_index = input.indexOf(" /at ");
                if (at_index == -1) throw new DukeException("datetime");
                arg1 = input.substring(5, at_index).trim();
                if (arg1.isBlank()) throw new DukeException("arg1 error "+ operation);
                date = input.substring(at_index + 5).trim();
                if (date.isBlank()) throw new DukeException("datetime");
                return new AddCommand("event", arg1, date);
            } else if (operation.equals("task")) {
                int delimiterIndex = input.indexOf(" /need ");
                if (delimiterIndex == -1) throw new DukeException("Missing task delimiter. (/need)");
                arg1 = input.substring(4, delimiterIndex).trim();
                if (arg1.isBlank()) throw new DukeException("Missing task description.");
                Matcher m = Pattern.compile("^(\\d+) (\\d+)$").matcher(input.substring(delimiterIndex + 7).trim());
                if (!m.find()) throw new DukeException("Invalid task argument need.\ntask <description> /need <hour(s)> <minute(s)>");
                return new AddCommand(arg1, Integer.parseInt(m.group(1)), Integer.parseInt(m.group(2)));
            } else if (operation.equals("bye")) {
                return new ExitCommand();
            } else if (operation.equals("list")) {
                return new ListCommand();
            } else if (operation.equals("reschedule")) {
                try {
                    index = Integer.parseInt(operation_list[1]) - 1;
                } catch (Exception e) {
                    throw new DukeException("index error");
                }
                int to_index = input.indexOf(" /to ");
                date = input.substring(to_index + 5).trim();
                if (to_index < 0 || date.isBlank()) throw new DukeException("datetime");
                return new RescheduleCommand(index, date);
            } else if (operation.equals("daily") || operation.equals("weekly")) {
                String s = input.substring(input.indexOf(" ") + 1);
                if (s.startsWith("deadline")) {
                    int by_index = s.indexOf(" /by ");
                    if (by_index == -1) throw new DukeException("datetime");
                    arg1 = s.substring(8, by_index).trim();
                    if (arg1.isBlank()) throw new DukeException("arg1 error "+ operation);
                    date = s.substring(by_index + 5).trim();
                    if (date.isBlank()) throw new DukeException("datetime");
                    return new RecurringCommand("deadline", arg1, date, input.substring(0, input.indexOf(" ")));
                } else if (s.startsWith("event")) {
                    int at_index = s.indexOf(" /at ");
                    if (at_index == -1) throw new DukeException("datetime");
                    arg1 = s.substring(5, at_index).trim();
                    if (arg1.isBlank()) throw new DukeException("arg1 error "+ operation);
                    date = s.substring(at_index + 5).trim();
                    if (date.isBlank()) throw new DukeException("datetime");
                    return new RecurringCommand("event", arg1, date, input.substring(0, input.indexOf(" ")));
                }
            }
        }
        throw new DukeException("unknown");
    }
}