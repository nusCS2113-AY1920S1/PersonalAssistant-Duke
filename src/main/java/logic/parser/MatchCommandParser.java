package logic.parser;

import common.DukeException;
import common.LoggerController;
import logic.command.Command;
import logic.command.match.MatchMemberCommand;
import logic.command.match.MatchTaskCommand;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//@@author JustinChia1997
public class MatchCommandParser {
    private static final Pattern BASIC_ADD_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");
    public static final String TASK = "task";
    public static final String MEMBER = "member";

    /**
     * Parses match command
     *
     * @param userInput desired class
     */
    public static Command parseMatch(String userInput) throws DukeException {
        final Matcher matcher = BASIC_ADD_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new DukeException("Message is invalid");
        }

        final String matchType = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");
        Command command;
        HashMap<String, String> argumentMultiMap = ArgumentTokenizer.tokenize(arguments);
        String name = argumentMultiMap.get("");

        switch (matchType) {
        case TASK:
            LoggerController.logDebug(MatchCommandParser.class, "match task called");
            return parseMatchTaskCommand(name);
        case MEMBER:
            LoggerController.logDebug(MatchCommandParser.class, "match member called");
            return parseMatchMemberCommand(name);
        default:
            throw new DukeException("match command format is wrong");
        }

    }

    /**
     * Does some input validation before instantiating new command
     */
    private static Command parseMatchTaskCommand(String taskName) throws DukeException {
        if (taskName.length() != 0) {
            return new MatchTaskCommand(taskName);
        } else {
            throw new DukeException("Task name not included");
        }
    }

    /**
     * Does some input validation before instantiating new command
     */
    private static Command parseMatchMemberCommand(String memberName) throws DukeException {
        if (memberName.length() != 0) {
            return new MatchMemberCommand(memberName);
        } else {
            throw new DukeException("Member name not included");
        }
    }

}
