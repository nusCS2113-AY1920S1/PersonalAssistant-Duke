package duchess.parser.commands;

import duchess.exceptions.DuchessException;
import duchess.logic.commands.Command;
import duchess.logic.commands.DisplayCalendarCommand;
import duchess.model.calendar.CalendarUtil;
import duchess.parser.Parser;
import duchess.parser.Util;

import java.time.LocalDate;
import java.util.Map;

public class CalendarCommandParser {

    /**
     * This class parses the user input to ensure the validity of the commands for calendar interaction.
     *
     * @param parameters user input mapped to keywords
     * @return either displays calendar or exports calendar
     * @throws DuchessException thrown if there are any missing or invalid parameters
     */
    public static Command parse(Map<String, String> parameters) throws DuchessException {
        try {
            String type = parameters.get("general");
            String date = parameters.get("date");
            String view = parameters.get("view");
            boolean isValidView = view.equals(Parser.WEEK_KEYWORD) || view.equals(Parser.DAY_KEYWORD);
            boolean isValidCommand = type.equals(Parser.DISPLAY_KEYWORD) || type.equals(Parser.EXPORT_KEYWORD);
            LocalDate input = Util.parseDate(date);
            int period = CalendarUtil.processDate(input);
            if (period == -1) {
                throw new DuchessException(Parser.CALENDAR_DATE_USAGE);
            } else if (!isValidCommand) {
                throw new DuchessException(Parser.CALENDAR_USAGE);
            } else if (!isValidView) {
                throw new DuchessException(Parser.CALENDAR_VIEW_USAGE);
            }

            boolean isWeek = view.equals(Parser.WEEK_KEYWORD);
            if (type.equals(Parser.DISPLAY_KEYWORD)) {
                return new DisplayCalendarCommand(input, isWeek);
            } else {
                return ExportCommandParser.parse(input, isWeek);
            }
        } catch (Exception e) {
            throw new DuchessException(Parser.CALENDAR_USAGE);
        }
    }
}