package duke.parser;

import duke.exceptions.DukeException;

import duke.logic.commands.Command;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {

    private static final Pattern GENERAL_COMMAND_FORMAT =
            Pattern.compile("(?<commandType>\\S+)(?<arguments>.*)");

    private static final String ADD_LOCKER_COMMAND = "addlocker";
    private static final String ADD_BATCH_COMMAND = "addbatch";
    private static final String DELETE_LOCKER_COMMAND = "delete";
    private static final String EDIT_LOCKER_COMMAND = "edit";
    private static final String LIST_COMMAND = "list";
    private static final String EXIT_COMMAND = "bye";

    /**
     * this function is used to parse the command entered by the user.
     *
     * @param fullCommand stores the command entered by the user
     * @return objects of type Command depending on the command given by the user
     * @throws DukeException when the user inputs invalid command
     */
    public Command parse(String fullCommand) throws DukeException {
        Matcher commandMatch = GENERAL_COMMAND_FORMAT.matcher(fullCommand.trim());
        if (!commandMatch.matches()) {
            throw new DukeException(" The command entered has invalid format. Type help to check"
                    + "all the commands available in SpongeBob");
        }
        String commandType = commandMatch.group("commandType");
        String arguments = commandMatch.group("arguments");
        switch (commandType.toLowerCase()) {
        case ADD_LOCKER_COMMAND:
            return new AddLockerCommandParser().parse(arguments);
        case ADD_BATCH_COMMAND:
            return new AddBatchCommandParser().parse(arguments);
        case DELETE_LOCKER_COMMAND:
            return new DeleteLockerCommandParser().parse(arguments);
        case EDIT_LOCKER_COMMAND:
            return new EditLockerCommandParser().parse(arguments);
        case LIST_COMMAND:
            return new ListCommandParser().parse();
        case EXIT_COMMAND:
            return new ByeCommandParser().parse();
        default:
            throw new DukeException("Invalid Command");
        }
    }
}

