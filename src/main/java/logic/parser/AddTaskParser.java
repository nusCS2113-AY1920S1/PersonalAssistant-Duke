package logic.parser;

import logic.command.AddTaskCommand;
import common.DukeException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class AddTaskParser {

    public static final String TIME_PATTERN = "dd/MM/yyyy hhmm";
    public static final String FORMAT_WRONG_MESSAGE = "Usage: add task [task name] \n"
            + "(optional) /at " + TIME_PATTERN + "\n"
            + "(optional) /to [member(s) name(s)]\n"
            + "eg: add task exam /at 23/11/2019 1300 /to Alice";

    //@@author chenyuheng
    /**
     * Parse an add Task command.
     * @param userInput Partial parsed add task command. (Command without "add task")
     * @return A parsed AddTaskCommand
     * @throws DukeException If command format is invalid.
     */
    public static AddTaskCommand parseAddTask(String userInput) throws DukeException {
        HashMap<String, String> argumentMultimap = ArgumentTokenizer.tokenize(userInput);
        String name = argumentMultimap.get("");
        AddTaskCommand command;
        if (name.length() == 0) {
            throw new DukeException(FORMAT_WRONG_MESSAGE);
        } else {
            command = new AddTaskCommand(name);
        }
        String timeString = argumentMultimap.get("/at");
        if (timeString != null) {
            SimpleDateFormat ft = new SimpleDateFormat(TIME_PATTERN);
            try {
                Date time = ft.parse(timeString);
                command.setTime(time);
            } catch (ParseException e) {
                throw new DukeException("Time format error. Should be: " + TIME_PATTERN);
            }
        }
        String members = argumentMultimap.get("/to");
        if (members != null) {
            command.setMembers(members);
        }
        String skills = argumentMultimap.get("/skill");
        if (skills != null) {
            command.setReqSkill(skills);
        }
        return command;
    }

}
