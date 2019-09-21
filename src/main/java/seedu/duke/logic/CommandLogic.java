package seedu.duke.logic;

public class CommandLogic {

    private static final String NEED_DESCRIPTION = "Adding command description cannot be empty!";
    private static final String REDUCE_WORDS = "Please only type ";
    private static final String WRONG_FORMAT_N0_TIME
    private static final String WRONG_FORMAT_BY = "Deadline needs to have /\"by\" before inputting time!";
    private static final String WRONG_FORMAT_AT = "Event needs to have /\"at\" before inputting time!";


    public static boolean validateDeadline(String rawInput) throws CommandLineException {
        if (isOneWord(rawInput)) {
            throw new CommandLineException(NEED_DESCRIPTION);
        } else if (!rawInput.contains("/by")) {
            throw new CommandLineException(WRONG_FORMAT_BY);
        } else {
            String date = rawInput.split("/by ")[1];

        }
        return true;
    }

    public static boolean validateEvent(String rawInput) throws CommandLineException {
        if (isOneWord(rawInput)) {
            throw new CommandLineException(NEED_DESCRIPTION);
        } else if (!rawInput.contains("/at")) {
            throw new CommandLineException(WRONG_FORMAT_AT);
        }
        return true;
    }

    public static boolean validateToDo(String rawInput) throws CommandLineException {
        if (isOneWord(rawInput)) {
            throw new CommandLineException(NEED_DESCRIPTION);
        } else if (rawInput.contains("/")) {
            throw new CommandLineException(WRONG_FORMAT_TIME);
        }
    }

    public static boolean validateOneWord(String rawInput) throws CommandLineException {
        if (!isOneWord(rawInput)) {
            throw new CommandLineException(REDUCE_WORDS + rawInput.split(" ")[0]);
        }
        return true;
    }

    private static boolean isOneWord(String rawInput) {
        return rawInput.length() == 1;
    }



}
