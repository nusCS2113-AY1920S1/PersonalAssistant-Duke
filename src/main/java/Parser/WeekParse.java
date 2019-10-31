package Parser;

import Commands.Command;
import Commands.WeekCommand;
import DukeExceptions.DukeInvalidCommandException;
import DukeExceptions.DukeInvalidFormatException;

public class WeekParse extends Parse {
    private String fullCommand;
    private final String invalidInput = "Invalid input. Please enter the command as follows. \n" +
            "Week 'x' , where 'x' is a digit between 1 - 13";
    private final String invalidWeek = "Invalid Week. Please enter the command as follows. \n" +
            "Week 'x' , where 'x' is a digit between 1 - 13";

    public WeekParse(String fullCommand) {
        this.fullCommand = fullCommand;
    }

    @Override
    public Command parse() throws DukeInvalidFormatException {
        /*
        String strWeek = fullCommand.replaceFirst("Week", "");
        strWeek = strWeek.trim();
        if(strWeek.isEmpty()) {
            throw new DukeInvalidFormatException(invalidInput);
        } else {
            try{
                Integer week = Integer.parseInt(strWeek);
                if(week >= 1 && week <= 13) return new WeekCommand(fullCommand.trim());
                else throw new DukeInvalidFormatException(invalidWeek);
            } catch (NumberFormatException e) {
                throw new DukeInvalidFormatException(invalidWeek);
            }
        }
        */
        if (isValid(fullCommand)) return new WeekCommand(fullCommand);
        else throw new DukeInvalidFormatException(invalidWeek);
    }

    public static boolean isValid(String fullCommand) throws DukeInvalidFormatException {
        String strWeek = fullCommand.replaceFirst("Week", "");
        if(!strWeek.isEmpty()) {
            char checkSpace = strWeek.charAt(0);
            if (checkSpace != ' ') return false;
        }
        strWeek = strWeek.trim();
        if(strWeek.isEmpty()) {
            //throw new DukeInvalidFormatException(invalidInput);
            return false;
        } else {
            try{
                Integer week = Integer.parseInt(strWeek);
                if(week >= 1 && week <= 13) return true;
                else return false;
            } catch (NumberFormatException e) {
                //throw new DukeInvalidFormatException(invalidWeek);
                return false;
            }
        }
    }
}
