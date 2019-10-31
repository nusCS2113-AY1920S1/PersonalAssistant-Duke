package duchess.parser.commands;

import duchess.exceptions.DuchessException;
import duchess.logic.commands.AddLessonCommand;
import duchess.logic.commands.Command;
import duchess.parser.Parser;
import duchess.parser.Util;

import java.time.LocalDateTime;
import java.util.Map;

public class LessonCommandParser {
    /**
     * Returns a command to add lessons based on user input.
     *
     * @param parameters processed user input
     * @return the command to execute
     * @throws DuchessException if the user input is invalid
     */
    public static Command parse(Map<String, String> parameters) throws DuchessException {
        try {
            String description = parameters.get("type");
            String moduleCode = parameters.get("code");

            String timeFirst = parameters.get("time");
            String timeSecond = parameters.get("to");

            if (timeFirst == null || timeSecond == null
                    || description == null || moduleCode == null) {
                throw new IllegalArgumentException();
            }

            LocalDateTime startTimeString = Util.parseDateTime(timeFirst);
            LocalDateTime endTimeString = Util.parseDateTime(timeSecond);

            return new AddLessonCommand(description, startTimeString, endTimeString, moduleCode);
        } catch (IllegalArgumentException e) {
            throw new DuchessException(Parser.LESSON_USAGE);
        }
    }
}
