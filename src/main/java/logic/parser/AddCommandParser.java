package logic.parser;

import logic.command.AddMemberCommand;
import logic.command.AddTaskCommand;
import logic.command.Command;
import utils.DukeException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.ForkJoinPool;

public class AddCommandParser {
    public static final String FORMAT_WRONG_MESSAGE = "Cannot resolve the model type. \nUsage: add [task/member] [details]";
    public static final String TASK_NO_EMPTY_MESSAGE = "The name of task cannot be empty.";
    public static final String TIME_PATTERN = "dd/MM/yyyy hhmm";
    public static final String MEMBER_NO_NAME_MESSAGE = "Member name needed. \nShould be: add member [member name]";

    public static Command parseAdd(String userInput) throws DukeException {
        if (userInput.split(" ", 1).length == 1) {
            throw new DukeException(FORMAT_WRONG_MESSAGE);
        }
        String modelType = userInput.split(" ", 1)[0].trim().toUpperCase();
        String modelDetail = userInput.split(" ", 1)[1].trim();
        if (modelType.length() == 0 || modelDetail.length() == 0) {
            throw new DukeException(FORMAT_WRONG_MESSAGE);
        }
        if (modelType.equals("TASK")) {
            return parseAddTask(modelDetail);
        } else if (modelType.equals("MEMBER")) {
            return parseAddMember(modelDetail);
        }
        throw new DukeException(FORMAT_WRONG_MESSAGE);
    }

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
        if (timeString.length() != 0) {
            SimpleDateFormat ft = new SimpleDateFormat(TIME_PATTERN);
            try {
                Date time = ft.parse(timeString);
                command.setTime(time);
            } catch (ParseException e) {
                throw new DukeException("Time format error. Should be: " + TIME_PATTERN);
            }
        }
        String members = argumentMultimap.get("/to");
        if (members.length() != 0) {
            command.setMembers(members);
        }
        return command;
    }

    public static AddMemberCommand parseAddMember(String userInput) throws DukeException {
        if (userInput.length() != 0) {
            return new AddMemberCommand(userInput);
        } else {
            throw new DukeException(MEMBER_NO_NAME_MESSAGE);
        }
    }
}
