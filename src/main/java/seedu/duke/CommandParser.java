package seedu.duke;

import seedu.duke.email.command.EmailCommandParser;
import seedu.duke.email.command.EmailTagCommand;
import seedu.duke.common.command.Command;
import seedu.duke.common.command.Command.Option;
import seedu.duke.common.command.InvalidCommand;
import seedu.duke.email.EmailList;
import seedu.duke.email.command.EmailShowCommand;
import seedu.duke.task.command.TaskCommandParser;
import seedu.duke.ui.UI;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A class that contains helper functions used to process user inputs. It also contains UserInputException
 * that is used across the project to handle the unexpected user input.
 */
public class CommandParser {

    private static UI ui = Duke.getUI();

    /**
     * Two types of input, prefix will be displayed according to this in the userInput text field.
     */
    public enum InputType {
        TASK,
        EMAIL
    }

    private static InputType inputType = InputType.TASK;

    /**
     * Checks if input command is in the correct format.
     *
     * @param commandString input command
     * @return true if matches pattern, false otherwise
     */
    public static boolean isCommandFormat(String commandString) {
        return commandString.matches(
                "(?:task|email\\s)(?:\\s*([\\w]+)[\\s|\\w]*)(?:\\s+"
                        + "(-[\\w]+\\s+[\\w]+[\\s|\\w/]*))*");
    }

    /**
     * Get input prefix for userInput text field in GUI.
     *
     * @return current prefix.
     */
    public static String getInputPrefix() {
        String prefix = "";
        switch (inputType) {
        case TASK:
            prefix = "task ";
            break;
        case EMAIL:
            prefix = "email ";
            break;
        default:
            prefix = "";
        }
        return prefix;
    }

    /**
     * Sets to the new input type when it is toggled by "flip" command.
     * Also updates the UI display of the prefix.
     *
     * @param newInputType the input type that is going to be changed to
     */
    public static void setInputType(InputType newInputType) {
        inputType = newInputType;
    }

    public static InputType getInputType() {
        return inputType;
    }

    /**
     * Flip between Email and Task input type of the command parser.
     */
    public static void filpInputType() {
        if (inputType == InputType.TASK) {
            inputType = InputType.EMAIL;
        } else {
            inputType = InputType.TASK;
        }
    }

    /**
     * Parses input to retrieve options from command string.
     *
     * @param input command string
     * @return list of all options specified in the command
     */
    public static ArrayList<Option> parseOptions(String input) {
        ArrayList<Option> optionList = new ArrayList<>();
        Pattern optionPattern = Pattern.compile(".*(?<key>-[\\w]+)\\s+(?<value>[\\w]+[\\s|\\w/]*)\\s*");
        Matcher optionMatcher = optionPattern.matcher(input);
        while (optionMatcher.matches()) {
            optionList.add(new Option(optionMatcher.group("key").substring(1),
                    optionMatcher.group("value")));
            input = input.replaceAll("\\s*(?<key>-[\\w]+)\\s+(?<value>[\\w]+[\\s|\\w/]*)\\s*$", "");
            optionMatcher = optionPattern.matcher(input);
        }
        return optionList;
    }

    public static String stripOptions(String input) {
        return input.replaceAll("\\s*(?<key>-[\\w]+)\\s+(?<value>[\\w]+[\\s|\\w/]*)\\s*", "");
    }

    /**
     * Parses the user/file input as command. It returns a command that is not yet executed. It also needs to
     * get a UI from Duke to display the messages.
     *
     * @param input the user/file input that is to be parsed to a command
     * @return the parse result, which is a command ready to be executed
     */
    public static Command parseCommand(String input) throws UserInputException {
        if (!isCommandFormat(input)) {
            if (ui != null) {
                ui.showError("Command is in wrong format");
            }
            return new InvalidCommand();
        }
        ArrayList<Option> optionList = parseOptions(input);
        input = stripOptions(input);
        if (inputType == InputType.TASK) {
            return TaskCommandParser.parseTaskCommand(input, optionList, ui);
        } else if (inputType == InputType.EMAIL) {
            try {
                return EmailCommandParser.parseEmailCommand(input, optionList, ui);
            } catch (UserInputException e) {
                ui.showError(e.getMessage());
                return new InvalidCommand();
            }
        } else {
            return new InvalidCommand();
        }
    }


    public static ArrayList<String> extractTags(ArrayList<Option> optionList) {
        ArrayList<String> tagList = new ArrayList<>();
        for (Option option : optionList) {
            if (option.getKey().equals("tag")) {
                tagList.add(option.getValue().strip());
            }
        }
        return tagList;
    }

    public static String extractTime(ArrayList<Option> optionList) throws UserInputException {
        String time = "";
        for (Option option : optionList) {
            if (option.getKey().equals("time")) {
                if (time == "") {
                    time = option.getValue();
                } else {
                    throw new UserInputException("Each task can have only one time option");
                }
            }
        }
        return time;
    }

    /**
     * An type of exception dedicated to handling the unexpected user/file input. The message contains more
     * specific information.
     */
    public static class UserInputException extends Exception {
        private String msg;

        /**
         * Instantiates the exception with a message, which is ready to be displayed by the UI.
         *
         * @param msg the message that is ready to be displayed by UI.
         */
        public UserInputException(String msg) {
            super();
            this.msg = msg;
        }

        /**
         * Converts the exception ot string by returning its message, so that it can be displayed by the UI.
         *
         * @return the message of the exception
         */
        @Override
        public String getMessage() {
            return msg;
        }
    }
}
