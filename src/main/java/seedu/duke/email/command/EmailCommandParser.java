package seedu.duke.email.command;

import seedu.duke.CommandParser;
import seedu.duke.Duke;
import seedu.duke.common.command.Command;
import seedu.duke.common.command.ExitCommand;
import seedu.duke.common.command.FlipCommand;
import seedu.duke.common.command.HelpCommand;
import seedu.duke.common.command.InvalidCommand;
import seedu.duke.email.EmailList;
import seedu.duke.ui.UI;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailCommandParser {
    private static UI ui = Duke.getUI();
    /**
     * Parses the specific part of a user/file input that is relevant to email. A successful parsing always
     * returns an email-relevant Command.
     *
     * @param rawInput  user/file input ready to be parsed.
     * @return an email-relevant Command.
     * @throws CommandParser.UserInputException an exception when the parsing is failed, probably due to the wrong format of
     *                            input
     */
    public static Command parseEmailCommand(String rawInput,
                                            ArrayList<Command.Option> optionList) throws CommandParser.UserInputException {
        if (rawInput.length() <= 6) {
            return new InvalidCommand();
        }
        String input = rawInput.substring(6).strip();
        String emailCommand = input.split(" ")[0];
        switch (emailCommand) {
        case "flip":
            return new FlipCommand();
        case "bye":
            return new ExitCommand();
        case "help":
            return new HelpCommand();
        case "list":
            return new EmailListCommand();
        case "show":
            return parseShowEmailCommand(input);
        case "fetch":
            return new EmailFetchCommand();
        case "update":
            return parseEmailTagCommand(optionList, input);
        default:
            throw new CommandParser.UserInputException("OOPS!!! Enter \'email help\' to get list of methods for "
                    + "email.");
        }
    }

    private static Command parseShowEmailCommand(String input) throws CommandParser.UserInputException {
        Pattern showCommandPattern = Pattern.compile("^show\\s+(?<index>\\d+)\\s*$");
        Matcher showCommandMatcher = showCommandPattern.matcher(input);
        if (!showCommandMatcher.matches()) {
            if (ui != null) {
                ui.showError("Please enter a valid index of task after \'show\'");
            }
            return new InvalidCommand();
        }
        try {
            int index = parseEmailIndex(showCommandMatcher.group("index"));
            return new EmailShowCommand(index);
        } catch (CommandParser.UserInputException e) {
            throw new CommandParser.UserInputException(e.toString());
        }
    }

    private static Command parseEmailTagCommand(ArrayList<Command.Option> optionList,
                                                String input) throws CommandParser.UserInputException {
        Pattern emailTagCommandPattern = Pattern.compile("^update\\s+(?<index>\\d+)\\s*$");
        Matcher emailTagCommandMatcher = emailTagCommandPattern.matcher(input);
        if (!emailTagCommandMatcher.matches()) {
            if (ui != null) {
                ui.showError("Please enter a valid email index after \'update\'");
            }
            return new InvalidCommand();
        }
        ArrayList<String> tags = CommandParser.extractTags(optionList);
        if (tags.size() == 0) {
            if (ui != null) {
                ui.showError("Please enter a tag name after \'-tag\' option");
            }
            return new InvalidCommand();
        }
        try {
            int index = parseEmailIndex(emailTagCommandMatcher.group("index"));
            return new EmailTagCommand(index, tags);
        } catch (CommandParser.UserInputException e) {
            throw new CommandParser.UserInputException(e.toString());
        }
    }

    private static int parseEmailIndex(String input) throws CommandParser.UserInputException {
        EmailList emailList = Duke.getModel().getEmailList();
        int index = Integer.parseInt(input) - 1;
        if (index < 0 || index >= emailList.size()) {
            throw new CommandParser.UserInputException("Invalid index");
        }
        return index;
    }
}
