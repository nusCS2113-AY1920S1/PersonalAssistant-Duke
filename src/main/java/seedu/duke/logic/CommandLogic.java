package seedu.duke.logic;

import java.time.LocalDate;
import java.time.LocalDateTime;
import seedu.duke.command.DateTimeParser;

public class CommandLogic {

    private static final String WRONG_FORMAT_NO_DESCRIPTION = "Adding command description cannot be empty!";
    private static final String WRONG_FORMAT_LENGTH = "Please only type \"";
    private static final String WRONG_FORMAT_NO_TIME = "Todo does not need to have a time inputted!";
    private static final String WRONG_FORMAT_BY = "Deadline needs to have /\"by\" before inputting time!";
    private static final String WRONG_FORMAT_AT = "Event needs to have /\"at\" before inputting time!";
    private static final String WRONG_FORMAT_IN = "Ranged Event needs to have /\"in\" before inputting the times!";
    private static final String WRONG_FORMAT_AFTER = "Adding an after ToDo needs to have /\"after\" before inputting the index of linked task!";
    private static final String WRONG_FORMAT_NEEDS = "FixedDurationTasks need to have/\"needs\" before inputting duration!";
    private static final String INVALID_NUMBER = "Please enter a valid number!";
    private static final String INVALID_SNOOZE = "Please only snooze either a specific DD/MM/YYYY or \"<number> <time to delay>\"\n etc: 3 days";



    public static boolean validateDeadline(String rawInput) throws CommandLineException {
        if (isOneWord(rawInput)) {
            throw new CommandLineException(WRONG_FORMAT_NO_DESCRIPTION);
        } else if (!rawInput.contains("/by")) {
            throw new CommandLineException(WRONG_FORMAT_BY);
        } else {
            String date = rawInput.split("/by ")[1];

        }
        return true;
    }

    public static boolean validateEvent(String rawInput) throws CommandLineException {
        if (isOneWord(rawInput)) {
            throw new CommandLineException(WRONG_FORMAT_NO_DESCRIPTION);
        } else if (!rawInput.contains("/at")) {
            throw new CommandLineException(WRONG_FORMAT_AT);
        }
        return true;
    }

    public static boolean validateToDo(String rawInput) throws CommandLineException {
        if (isOneWord(rawInput)) {
            throw new CommandLineException(WRONG_FORMAT_NO_DESCRIPTION);
        } else if (rawInput.contains("/")) {
            throw new CommandLineException(WRONG_FORMAT_NO_TIME);
        }
        return true;
    }

    public static boolean validateRange(String rawInput) throws CommandLineException {
        if (isOneWord(rawInput)) {
            throw new CommandLineException(WRONG_FORMAT_NO_DESCRIPTION);
        } else if (!rawInput.contains("/in")) {
            throw new CommandLineException(WRONG_FORMAT_IN);
        }
        String dates = rawInput.split("/in ")[1];
        String[] datesStr = dates.split(" and ", 2);
        DateTimeParser.getDateTime(datesStr[0]);
        DateTimeParser.getDateTime(datesStr[1]);
        return true;
    }

    public static boolean validateDoAfter(String rawInput) throws CommandLineException {
        if (isOneWord(rawInput)) {
            throw new CommandLineException(WRONG_FORMAT_NO_DESCRIPTION);
        } else if (!rawInput.contains("/after")) {
            throw new CommandLineException(WRONG_FORMAT_AFTER);
        }
        try {
            int number = Integer.parseInt(rawInput.split("/after ", 2)[1]);
        } catch (NumberFormatException e) {
            throw new CommandLineException(INVALID_NUMBER);
        }
        return true;
    }

    public static boolean validateFixedDurationTask(String rawInput) throws CommandLineException {
        if (isOneWord(rawInput)) {
            throw new CommandLineException(WRONG_FORMAT_NO_DESCRIPTION);
        } else if (!rawInput.contains("/needs")) {
            throw new CommandLineException(WRONG_FORMAT_NEEDS);
        }
        return true;
    }

    public static boolean validateNumberCommand(String rawInput) throws CommandLineException {
        if (!isTwoWord(rawInput)) {
            throw new CommandLineException(WRONG_FORMAT_LENGTH + rawInput.split(" ")[0] + " <number ID of task>\"");
        } try {
            int number = Integer.parseInt(rawInput.split(" ")[1]);
        } catch (NumberFormatException e) {
            throw new CommandLineException(INVALID_NUMBER);
        }
        return true;
    }

    public static boolean validateSnooze(String command) throws CommandLineException {
        /**
         * Checks if user is inputting snooze in form of (number of times) (delay) or a specific date.
         */
        if (isTwoWord(command)) {
            try {
                String[] splitCommand = command.split(" ");
                int number = Integer.parseInt(splitCommand[0]);
                    switch (splitCommand[1]) {
                        case "minutes":
                        case "hours":
                        case "days":
                        case "weeks":
                        case "months":
                            return true;
                        default:
                            throw new CommandLineException(INVALID_SNOOZE);
                    }
            } catch (NumberFormatException e) {
                throw new CommandLineException(INVALID_SNOOZE);
            }
        } else if (isOneWord(command)) {
            DateTimeParser.getDateTime(command);
        } else {
            throw new CommandLineException(INVALID_SNOOZE);
        }
        return true;
    }

    public static boolean validateFind(String rawInput) throws CommandLineException {
        if (!isTwoWord(rawInput)) {
            throw new CommandLineException(WRONG_FORMAT_LENGTH + rawInput.split(" ")[0] + " <keyword>\"");
        }
        return true;
    }

    public static boolean validateShow(String rawInput) throws CommandLineException {
        String[] splitInput = rawInput.split(" ");
        if(!isTwoWord(rawInput)) {
            throw new CommandLineException(WRONG_FORMAT_LENGTH + splitInput[0] + " <DD/MM/YYYY>");
        } else {
            DateTimeParser.getDateTime(splitInput[1]);
        }
        return true;
    }

    public static boolean validateOneWord(String rawInput) throws CommandLineException {
        if (!isOneWord(rawInput)) {
            throw new CommandLineException(WRONG_FORMAT_LENGTH + rawInput.split(" ")[0] + "\"");
        }
        return true;
    }

    private static boolean isOneWord(String rawInput) {
        return rawInput.length() == 1;
    }

    private static boolean isTwoWord(String rawInput) {
        return rawInput.length() == 2;
    }



}
