package seedu.duke;

import seedu.duke.common.command.Command;
import seedu.duke.common.command.Command.Option;
import seedu.duke.common.command.InvalidCommand;
import seedu.duke.email.command.EmailCommandParseHelper;
import seedu.duke.task.command.TaskCommandParseHelper;
import seedu.duke.ui.UI;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A class that contains helper functions used to process user inputs. It also contains UserInputException
 * that is used across the project to handle the unexpected user input.
 */
public class CommandParseHelper {

    private static UI ui = Duke.getUI();
    private static InputType inputType = InputType.EMAIL;

    /**
     * Checks if input command is in the correct format.
     *
     * @param commandString input command.
     * @return true if matches pattern, false otherwise.
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
            break;
        }
        return prefix;
    }

    public static InputType getInputType() {
        return inputType;
    }

    /**
     * Sets to the new input type when it is toggled by "flip" command. Also updates the UI display of the
     * prefix.
     *
     * @param newInputType the input type that is going to be changed to.
     */
    public static void setInputType(InputType newInputType) {
        inputType = newInputType;
    }

    /**
     * Flip between Email and Task input type of the command parser.
     */
    public static void flipInputType() {
        if (inputType == InputType.TASK) {
            inputType = InputType.EMAIL;
        } else {
            inputType = InputType.TASK;
        }
    }

    /**
     * Parses input to retrieve options from command string.
     *
     * @param input command string.
     * @return list of all options specified in the command.
     */
    public static ArrayList<Option> parseOptions(String input) {
        String userInput = input;
        ArrayList<Option> optionList = new ArrayList<>();
        Pattern optionPattern = Pattern.compile(".*(?<key>-[\\w]+)\\s+(?<value>[\\w]+[\\s|\\w/]*)\\s*");
        Matcher optionMatcher = optionPattern.matcher(userInput);
        while (optionMatcher.matches()) {
            optionList.add(new Option(optionMatcher.group("key").substring(1),
                    optionMatcher.group("value")));
            userInput = userInput.replaceAll("\\s*(?<key>-[\\w]+)\\s+(?<value>[\\w]+[\\s|\\w/]*)\\s*$", "");
            optionMatcher = optionPattern.matcher(userInput);
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
     * @param commandString the user/file input that is to be parsed to a command
     * @return the parse result, which is a command ready to be executed
     */
    public static Command parseCommand(String commandString) throws UserInputException {
        if (!isCommandFormat(commandString)) {
            if (ui != null) {
                ui.showError("Command is in wrong format");
            }
            return new InvalidCommand();
        }
        ArrayList<Option> optionList = parseOptions(commandString);
        String strippedCommandString = stripOptions(commandString);
        if (inputType == InputType.TASK) {
            return TaskCommandParseHelper.parseTaskCommand(strippedCommandString, optionList);
        } else if (inputType == InputType.EMAIL) {
            return parseEmailCommand(strippedCommandString, optionList);
        } else {
            return new InvalidCommand();
        }
    }

    private static Command parseEmailCommand(String input, ArrayList<Option> optionList) {
        try {
            return EmailCommandParseHelper.parseEmailCommand(input, optionList);
        } catch (UserInputException e) {
            ui.showError(e.getMessage());
            return new InvalidCommand();
        }
    }

    /**
     * Extracts tags from the option list.
     *
     * @param optionList the list of options where the tags are extracted
     * @return the ArrayList of strings
     */
    public static ArrayList<String> extractTags(ArrayList<Option> optionList) {
        ArrayList<String> tagList = new ArrayList<>();
        for (Option option : optionList) {
            if (option.getKey().equals("tag")) {
                tagList.add(option.getValue().strip());
            }
        }
        return tagList;
    }

    /**
     * Extracts time string from the option list.
     *
     * @param optionList the list of options where the time string is extracted
     * @return the time string
     * @throws UserInputException if time option appears more than once
     */
    public static String extractTime(ArrayList<Option> optionList) throws UserInputException {
        String time = "";
        for (Option option : optionList) {
            if (option.getKey().equals("time")) {
                if ("".equals(time)) {
                    time = option.getValue();
                } else {
                    throw new UserInputException("Each task can have only one time option");
                }
            }
        }
        return time;
    }

    /**
     * Two types of input, prefix will be displayed according to this in the userInput text field.
     */
    public enum InputType {
        TASK,
        EMAIL
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
