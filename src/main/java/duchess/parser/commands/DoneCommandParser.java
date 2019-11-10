package duchess.parser.commands;

import duchess.exceptions.DuchessException;
import duchess.logic.commands.Command;
import duchess.logic.commands.DoneGradeCommand;
import duchess.logic.commands.DoneTaskCommand;
import duchess.parser.Parser;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class DoneCommandParser {

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
                return new DoneTaskCommand(number);
            } else if (type.equals(Parser.GRADE_KEYWORD)) {
                String moduleCode = parameters.get(Parser.MODULE_KEYWORD);
                if (moduleCode == null) {
                    throw new IllegalArgumentException();
                }
                String marks = parameters.get(Parser.MARKS_KEYWORD);
                int marksObtained;
                int maxMarks;
                List<String> marksList = Arrays.asList(marks.split("/"));
                marksObtained = Integer.parseInt(marksList.get(0));
                maxMarks = Integer.parseInt(marksList.get(1));
                if (marksObtained < 0 || maxMarks < 1 || marksObtained > maxMarks) {
                    throw new IllegalArgumentException();
                }
                int number = Integer.parseInt(parameters.get("no"));
                return new DoneGradeCommand(moduleCode, number, marksObtained, maxMarks);
            } else {
                throw new IllegalArgumentException();
            }
        } catch (IndexOutOfBoundsException | IllegalArgumentException | NullPointerException e) {
            throw new DuchessException(Parser.DONE_USAGE);
        }
    }
}
