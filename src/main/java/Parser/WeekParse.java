package Parser;

import Commands.Command;
import Commands.WeekCommand;
import DukeExceptions.DukeInvalidFormatException;

public class WeekParse extends Parse {
    private String fullCommand;
    private final String invalidWeek = "Invalid Week. Please enter the command as follows. \n" +
            "show/week 'x' , where 'x' is a digit between 1 - 13 or \n" +
            "'x' is either 'recess', 'reading', or 'exam'";

    public WeekParse(String fullCommand) {
        this.fullCommand = fullCommand;
    }

    @Override
    public Command parse() throws DukeInvalidFormatException {
        if (isValid(fullCommand)) {
            fullCommand = getWeek(fullCommand);
            return new WeekCommand(fullCommand);
        }
        else throw new DukeInvalidFormatException(invalidWeek);
    }

    public static boolean isValid(String fullCommand) {
        String strWeek = fullCommand.replaceFirst("show/week", "");
        if(!strWeek.isEmpty()) {
            char checkSpace = strWeek.charAt(0);
            if (checkSpace != ' ') return false;
        }
        strWeek = strWeek.trim();
        if(strWeek.isEmpty()) {
            return false;
        } else if (strWeek.equals("recess") || strWeek.equals("reading") || strWeek.equals("exam")) {
            return true;
        }else {
            try{
                Integer week = Integer.parseInt(strWeek);
                if(week >= 1 && week <= 13) return true;
                else return false;
            } catch (NumberFormatException e) {
                return false;
            }
        }
    }

    public static String getWeek(String fullCommand) {
        String week = fullCommand.replaceFirst("show/week", "");
        week = week.trim();
        if ((week.equals("recess") || week.equals("reading") || week.equals("exam"))) {
            String firstLetter = week.substring(0,1);
            firstLetter = firstLetter.toUpperCase();
            week = firstLetter + week.substring(1);
            week = week + " Week";
        } else {
            week = "Week " + week;
        }
        return week;
    }
}
