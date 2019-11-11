package parser;

import commands.Command;
import commands.WeekCommand;
import commons.DukeConstants;
import dukeexceptions.DukeInvalidFormatException;

public class WeekParse extends Parse {
    private String fullCommand;

    public WeekParse(String fullCommand) {
        this.fullCommand = fullCommand;
    }

    @Override
    public Command parse() throws DukeInvalidFormatException {
        if (isValid(fullCommand)) {
            fullCommand = getWeek(fullCommand);
            return new WeekCommand(fullCommand);
        }
        fullCommand = fullCommand.replaceFirst(DukeConstants.SHOW_WEEK_HEADER, DukeConstants.NO_FIELD);
        if (fullCommand.trim().isEmpty()) {
            throw new DukeInvalidFormatException(DukeConstants.INVALID_EMPTY_WEEK);
        }
        throw new DukeInvalidFormatException(DukeConstants.INVALID_WEEK);
    }

    /**
     * This method checks if the input is valid.
     * @param fullCommand The command input by the user
     * @return true if the input is valid. Otherwise, false
     */
    public static boolean isValid(String fullCommand) {
        String strWeek = fullCommand.replaceFirst(DukeConstants.SHOW_WEEK_HEADER, DukeConstants.NO_FIELD);
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
        String week = fullCommand.replaceFirst(DukeConstants.SHOW_WEEK_HEADER, DukeConstants.NO_FIELD);
        week = week.trim();
        if ((week.equals(DukeConstants.WEEK_FORMAT_KEYWORD_RECESS)
                || week.equals(DukeConstants.WEEK_FORMAT_KEYWORD_READING)
                || week.equals(DukeConstants.WEEK_FORMAT_KEYWORD_EXAM))) {
            week = capitalizeWord(week);
            week = week + DukeConstants.BLANK_SPACE + DukeConstants.WEEK_FORMAT_KEYWORD;
        } else {
            week = DukeConstants.WEEK_FORMAT_KEYWORD + DukeConstants.BLANK_SPACE + week;
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
                && selectedWeek.contains(DukeConstants.WEEK_FORMAT_KEYWORD)) {
            selectedWeek = selectedWeek.replace(DukeConstants.WEEK_FORMAT_KEYWORD, DukeConstants.NO_FIELD);
            selectedWeek = selectedWeek.trim();
            week = DukeConstants.SHOW_WEEK_HEADER
                    + DukeConstants.BLANK_SPACE
                    + selectedWeek.toLowerCase();
        } else {
            week = DukeConstants.SHOW_WEEK_HEADER + selectedWeek.replaceFirst(DukeConstants.WEEK_FORMAT_KEYWORD,
                    DukeConstants.NO_FIELD);
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
