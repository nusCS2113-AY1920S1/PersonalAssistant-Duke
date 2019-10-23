package seedu.duke.email.command;

import seedu.duke.CommandParseHelper;
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
     * @param rawInput user/file input ready to be parsed.
     * @return an email-relevant Command.
     * @throws CommandParseHelper.UserInputException an exception when the parsing is failed, probably due to the
     *                                          wrong format of input
     */
    public static Command parseEmailCommand(String rawInput, ArrayList<Command.Option> optionList)
            throws CommandParseHelper.UserInputException {
        String input = stripPrefix(rawInput);
        if (input == null) {
            return new InvalidCommand();
        }
        String emailCommand = extractCommandWord(input);
        return parseByCommandType(optionList, input, emailCommand);
    }

    private static Command parseByCommandType(ArrayList<Command.Option> optionList, String input,
                                              String emailCommand) throws CommandParseHelper.UserInputException {
        switch (emailCommand) {
        case "flip":
            return new FlipCommand();
        case "bye":
            return new ExitCommand();
        case "help":
            return new HelpCommand();
        case "list":
            return parseEmailListCommand(optionList);
        case "show":
            return parseShowEmailCommand(input);
        case "fetch":
            return new EmailFetchCommand();
        case "update":
            return parseEmailTagCommand(optionList, input);
        default:
            throw new CommandParseHelper.UserInputException("OOPS!!! Enter \'email help\' to get list of methods for "
                    + "email.");
        }
    }

    private static String extractCommandWord(String input) {
        return input.split(" ")[0];
    }

    private static String stripPrefix(String rawInput) {
        if (!rawInput.contains("email ")) {
            return null;
        }
        return rawInput.split("email ", 2)[1].strip();
    }

    private static Command parseEmailListCommand(ArrayList<Command.Option> optionList) {
        if (optionList.size() == 0) {
            return new EmailListCommand();
        }
        ArrayList<String> tags = CommandParseHelper.extractTags(optionList);
        if (!tagsNotEmpty(tags)) {
            return new InvalidCommand();
        }
        return new EmailListTagCommand(tags);
    }

    private static Command parseShowEmailCommand(String input) throws CommandParseHelper.UserInputException {
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
        } catch (CommandParseHelper.UserInputException e) {
            throw new CommandParseHelper.UserInputException(e.toString());
        }
    }

    private static Command parseEmailTagCommand(ArrayList<Command.Option> optionList,
                                                String input) throws CommandParseHelper.UserInputException {
        Pattern emailTagCommandPattern = Pattern.compile("^update\\s+(?<index>\\d+)\\s*$");
        Matcher emailTagCommandMatcher = emailTagCommandPattern.matcher(input);
        if (!emailTagCommandMatcher.matches()) {
            if (ui != null) {
                ui.showError("Please enter a valid email index after \'update\'");
            }
            return new InvalidCommand();
        }
        ArrayList<String> tags = CommandParseHelper.extractTags(optionList);
        if (!tagsNotEmpty(tags)) {
            return new InvalidCommand();
        }
        try {
            int index = parseEmailIndex(emailTagCommandMatcher.group("index"));
            return new EmailTagCommand(index, tags);
        } catch (CommandParseHelper.UserInputException e) {
            throw new CommandParseHelper.UserInputException(e.toString());
        }
    }

    private static boolean tagsNotEmpty(ArrayList<String> tags) {
        if (tags.size() == 0) {
            if (ui != null) {
                ui.showError("Please enter a tag name after \'-tag\' option");
            }
            return false;
        }
        return true;
    }

    private static int parseEmailIndex(String input) throws CommandParseHelper.UserInputException {
        EmailList emailList = Duke.getModel().getEmailList();
        int index = Integer.parseInt(input) - 1;
        if (index < 0 || index >= emailList.size()) {
            throw new CommandParseHelper.UserInputException("Invalid index");
        }
        return index;
    }
}
