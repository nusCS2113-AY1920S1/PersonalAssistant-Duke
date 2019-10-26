package duchess.parser.commands;

import duchess.exceptions.DuchessException;
import duchess.logic.commands.Command;
import duchess.logic.commands.DeleteLessonCommand;

import java.util.Map;

public class DeleteLessonCommandParser {
    /**
     * Returns a command to delete lessons based on user input.
     *
     * @param parameters processed user input
     * @return the command to execute
     * @throws DuchessException if the user input is invalid
     */
    public static Command parse(Map<String, String> parameters) throws DuchessException {
        String description = parameters.get("type");
        String moduleCode = parameters.get("code");

        return new DeleteLessonCommand(description, moduleCode);
    }
}
