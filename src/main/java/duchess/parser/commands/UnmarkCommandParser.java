package duchess.parser.commands;

import duchess.exceptions.DuchessException;
import duchess.logic.commands.Command;
import duchess.logic.commands.UnmarkTaskCommand;
import duchess.parser.Parser;

import java.util.Map;

public class UnmarkCommandParser {
    /**
     * Returns a command to delete a module based on user input.
     *
     * @param parameters processed user input
     * @return the command to execute
     * @throws DuchessException if the user input is invalid
     */
    public static Command parse(Map<String, String> parameters) throws DuchessException {
        try {
            String type = parameters.get("general");
            if (type.equals(Parser.TASK_KEYWORD)) {
                int number = Integer.parseInt(parameters.get("no"));
                return new UnmarkTaskCommand(number);
            } else {
                throw new IllegalArgumentException();
            }
        } catch (IndexOutOfBoundsException | IllegalArgumentException | NullPointerException e) {
            throw new DuchessException(Parser.UNMARK_USAGE);
        }
    }
}
