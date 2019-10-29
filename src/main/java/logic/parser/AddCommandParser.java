package logic.parser;

import logic.command.AddMemberCommand;
import logic.command.AddTaskCommand;
import logic.command.Command;
import utils.DukeException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class AddCommandParser {
    private static final Pattern BASIC_ADD_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");
    public static final String FORMAT_WRONG_MESSAGE = "Cannot resolve the model type. \nUsage: add [task/member] [details]";
    public static final String TASK_NO_EMPTY_MESSAGE = "The name of task cannot be empty.";
    public static final String TIME_PATTERN = "dd/MM/yyyy hhmm";
    public static final String MEMBER_NO_NAME_MESSAGE = "Member name needed. \nShould be: add member [member name]";

    //@@author JustinChia1997
    /**
     * Parses add commands.
     */
    public static Command parseAddCommand(String partialCommand) throws DukeException {
        final Matcher matcher = BASIC_ADD_COMMAND_FORMAT.matcher(partialCommand.trim());
        if (!matcher.matches()) {
            throw new DukeException("Message is invalid");
        }

        final String addType = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");

        switch (addType) {
            case AddTaskCommand.COMMAND_WORD:
                return parseAddTask(arguments);

            case AddMemberCommand.COMMAND_WORD:
                return parseAddMember(arguments);

            default:
                throw new DukeException("Command word not found");
        }

    }

    //@@author chenyuheng
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
        String members = argumentMultimap.get("/member");
        if (members != null) {
            command.setMembers(members);
        }
        return command;
    }

    //@author chenyuheng
    public static AddMemberCommand parseAddMember(String userInput) throws DukeException {
        if (userInput != null) {
            return new AddMemberCommand(userInput);
        } else {
            throw new DukeException(MEMBER_NO_NAME_MESSAGE);
        }
    }

}
