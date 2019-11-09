package Parser;

import Commands.Command;
import Commands.WeekCommand;
import Commons.DukeConstants;
import DukeExceptions.DukeInvalidFormatException;

public class WeekParse extends Parse {
    private String fullCommand;
    private final String invalidEmptyWeek = "Invalid Input.\n"
            + "The week cannot be blank.\nPlease enter the command as follows.\n"
            + "show/week 'x' , where 'x' is a digit between 1 - 13 or \n"
            + "'x' is either 'recess', 'reading', or 'exam'";
    private final String invalidWeek = "Invalid Week. Please enter the command as follows. \n"
            + "show/week 'x' , where 'x' is a digit between 1 - 13 or \n"
            + "'x' is either 'recess', 'reading', or 'exam'";

    public WeekParse(String fullCommand) {
        this.fullCommand = fullCommand;
    }

    @Override
    public Command parse() throws DukeInvalidFormatException {
        if (isValid(fullCommand)) {
            fullCommand = getWeek(fullCommand);
            return new WeekCommand(fullCommand);
        }
        fullCommand = fullCommand.replaceFirst(DukeConstants.SHOW_WEEK_HEADER, "");
        if (fullCommand.trim().isEmpty()) {
            throw new DukeInvalidFormatException(invalidEmptyWeek);
        }
        throw new DukeInvalidFormatException(invalidWeek);
    }

    /**
     * This method checks if the input is valid.
     * @param fullCommand The command input by the user
     * @return true if the input is valid. Otherwise, false
     */
    public static boolean isValid(String fullCommand) {
        String strWeek = fullCommand.replaceFirst(DukeConstants.SHOW_WEEK_HEADER, "");
        if (!strWeek.isEmpty()) {
            char checkSpace = strWeek.charAt(0);
            if (checkSpace != ' ') {
                return false;
            }
        }
        strWeek = strWeek.trim();
        if (strWeek.isEmpty()) {
            return false;
        } else if (strWeek.equals(DukeConstants.WEEK_FORMAT_KEYWORD_RECESS)
                || strWeek.equals(DukeConstants.WEEK_FORMAT_KEYWORD_READING)
                || strWeek.equals(DukeConstants.WEEK_FORMAT_KEYWORD_EXAM)) {
            return true;
        } else {
            try {
                Integer week = Integer.parseInt(strWeek);
                if (week >= 1 && week <= 13) {
                    return true;
                } else {
                    return false;
                }
            } catch (NumberFormatException e) {
                return false;
            }
        }
    }

    /**
     * This method converts the given week format to the format in the lookup table.
     * @param fullCommand The WeekCommand with the selected week
     * @return The week in the lookup table format
     */
    public static String getWeek(String fullCommand) {
        String week = fullCommand.replaceFirst(DukeConstants.SHOW_WEEK_HEADER, "");
        week = week.trim();
        if ((week.equals(DukeConstants.WEEK_FORMAT_KEYWORD_RECESS)
                || week.equals(DukeConstants.WEEK_FORMAT_KEYWORD_READING)
                || week.equals(DukeConstants.WEEK_FORMAT_KEYWORD_EXAM))) {
            week = capitalizeWord(week);
            week = week + DukeConstants.STRING_SPACE_SPLIT_KEYWORD + DukeConstants.WEEK_FORMAT_KEYWORD;
        } else {
            week = DukeConstants.WEEK_FORMAT_KEYWORD + DukeConstants.STRING_SPACE_SPLIT_KEYWORD + week;
        }
        return week;
    }

    /**
     * This method converts the given week format to the WeekCommand format.
     * @param selectedWeek The chosen week
     * @return The week in WeekCommand format
     */
    public static String getWeekCommandFormat(String selectedWeek) {
        String week;
        if ((selectedWeek.toLowerCase().startsWith(DukeConstants.WEEK_FORMAT_KEYWORD_RECESS)
                || selectedWeek.toLowerCase().startsWith(DukeConstants.WEEK_FORMAT_KEYWORD_READING)
                || selectedWeek.toLowerCase().startsWith(DukeConstants.WEEK_FORMAT_KEYWORD_EXAM))
                && selectedWeek.contains("Week")) {
            selectedWeek = selectedWeek.replace(DukeConstants.WEEK_FORMAT_KEYWORD, "");
            selectedWeek = selectedWeek.trim();
            week = DukeConstants.SHOW_WEEK_HEADER
                    + DukeConstants.STRING_SPACE_SPLIT_KEYWORD
                    + selectedWeek.toLowerCase();
        } else {
            week = DukeConstants.SHOW_WEEK_HEADER + selectedWeek.replaceFirst(DukeConstants.WEEK_FORMAT_KEYWORD, "");
        }
        return week;
    }

    private static String capitalizeWord(String word) {
        String firstLetter = word.substring(0,1);
        firstLetter = firstLetter.toUpperCase();
        word = firstLetter + word.substring(1);
        return word;
    }
}
