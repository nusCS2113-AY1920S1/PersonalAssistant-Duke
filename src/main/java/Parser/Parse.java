package Parser;

import Commands.Command;

/**
 * Abstract class Parse with methods representing all the Command subclasses to be
 * carried out when an input is entered by the user.
 */
public abstract class Parse {
    public abstract Command parse() throws Exception;

    /**
     * This method checks if user input mod code actually fits the characteristic of a mod code
     * @param string The string of mod code
     * @return true if it matches the characteristics of a mod code.
     */
    public boolean isModCode(String string) {
        if (string.length() < 6){
            return  false;
        } else if (string.substring(0, 2).matches("\\w+") && string.substring(2, 6).matches("\\d+")) {
            Integer size = string.length();
            if (size - 6 > 0 && string.substring(6, 7).matches("\\d+")) return false;
            return true;
        } else if (string.length() >= 7 && string.substring(0, 3).matches("\\w+") && string.substring(3, 7).matches("\\d+")) {
            Integer size = string.length();
            if (size -  7 > 0 && string.substring(7,8).matches("\\d+")) return false;
            return true;
        } else {
            return false;
        }
    }

    /**
     * This method checks if the user input start and end time actually fits the characteristics of a 24-hour time format.
     * @param input The string that contains the  date, start and end time fields
     * @return true if it matches the characteristics of a 24-hour time format
     */
    public boolean isValidTimePeriod(String input) {
        String[] dateTimeStringSplit = input.trim().split("/from");
        String[] timeStringSplit = dateTimeStringSplit[1].split("/to");
        String start = timeStringSplit[0].trim();
        String end = timeStringSplit[1].trim();
        if (start.length() != 4 || end.length() != 4) return false;
        else if((start.matches("[0-9]+") && start.length() > 2) || (end.matches("[0-9]") && end.length() > 2)) {
            Integer intStart = Integer.parseInt(start);
            Integer intEnd = Integer.parseInt(end);
            if(!isValidTwoFourHourFormat(intStart) || !isValidTwoFourHourFormat(intEnd)) {
                return false;
            } else if (intStart > intEnd) {
                return false;
            }
            return true;
        }
        return false;
    }

    /**
     * This method checks if the time given is in a 24-hour time format
     * @param intTime The time given
     * @return true if the time given is valid. Otherwise, false.
     */
    public boolean isValidTwoFourHourFormat(Integer intTime) {
        if (intTime < 0 || intTime > 2359) {
            return false;
        } else {
            Integer intHour = intTime / 100;
            Integer intMinute = intTime % 100;
            if (intHour > 23  || intMinute > 59) {
                return false;
            } else {
                return true;
            }
        }
    }

    /**
     * This method checks if the user input time actually fits the characteristics of a 24-hour time format.
     * @param input The string that contains the date and time field
     * @return true if it matches the characteristics of a 24-hour time format
     */
    public boolean isValidTime(String input) {
        String[] spiltInput = input.split(" ");
        String time = spiltInput[spiltInput.length-1];
        if(time.length() != 4) {
            return false;
        }
        if (time.matches("[0-9]+") && time.length() > 2) {
            Integer intTime = Integer.parseInt(time);
            return isValidTwoFourHourFormat(intTime);
        }
        return false;
    }

    /**
     * This method checks if the description of task if valid
     * @param input String array of ModCode and description
     * @return true if description of task is valid
     */
    public boolean isValidDescription(String[] input) {
        if(input.length == 1) {
            return false;
        }
        String description = input[1].trim();
        if(description.isEmpty()) {
            return false;
        }
        return true;
    }

    /**
     * This method checks if both the ModCode and description of the task is valid
     * @param input String of ModCode and description
     * @return true if ModCode and description of the task is valid
     */
    public boolean isValidModCodeAndDescription(String input) {
        if (input.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }
}
