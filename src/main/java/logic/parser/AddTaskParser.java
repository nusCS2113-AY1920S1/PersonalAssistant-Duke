package logic.parser;

import logic.command.AddTaskCommand;
import common.DukeException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class AddTaskParser {

    public static final String FORMAT_WRONG_MESSAGE = "Cannot resolve the model type. "
            + "\nUsage: add [task/member] [details]";
    public static final String TASK_NO_EMPTY_MESSAGE = "The name of task cannot be empty.";
    public static final String TIME_PATTERN = "dd/MM/yyyy hhmm";

    //@@author chenyuheng

    /**
     * parses arguments of addtask into a multimap
     */
    public static AddTaskCommand parseAddTask(String userInput) throws DukeException {
        HashMap<String, String> argumentMultimap = ArgumentTokenizer.tokenize(userInput);
        String name = argumentMultimap.get("");
        AddTaskCommand command;
        if (name.length() == 0) {
            throw new DukeException(TASK_NO_EMPTY_MESSAGE);
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
