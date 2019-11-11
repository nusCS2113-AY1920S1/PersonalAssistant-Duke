package duchess.parser.commands;

import duchess.exceptions.DuchessException;
import duchess.logic.commands.Command;
import duchess.logic.commands.ViewScheduleCommand;

import java.util.List;

public class ViewScheduleCommandParser {
    private static final String DAY_KEYWORD = "day";
    private static final String WEEK_KEYWORD = "week";
    private static final String USAGE_SCHEDULE_DATE_DAY_WEEK = "Usage: schedule <date> (day | week)";

    /**
     * Parses input for view schedule command.
     *
     * @param words input
     * @return view schedule command
     * @throws DuchessException thrown for invalid input
     */
    public static Command parse(List<String> words) throws DuchessException {
        try {
            String view = words.get(2);
            boolean isInvalidView = !view.equals(DAY_KEYWORD) && !view.equals(WEEK_KEYWORD);
            if (isInvalidView) {
                throw new IllegalArgumentException();
            }
            String date = words.get(1);
            return new ViewScheduleCommand(date, view);
        } catch (Exception e) {
            throw new DuchessException(USAGE_SCHEDULE_DATE_DAY_WEEK);
        }
    }
}
