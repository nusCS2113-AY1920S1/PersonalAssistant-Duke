package spinbox;

import spinbox.commands.AddCommand;
import spinbox.commands.Command;
import spinbox.commands.ExitCommand;
import spinbox.commands.FindCommand;
import spinbox.commands.HelpCommand;
import spinbox.commands.RemoveCommand;
import spinbox.commands.RemoveMultipleCommand;
import spinbox.commands.UpdateCommand;
import spinbox.commands.UpdateMultipleCommand;
import spinbox.commands.ViewCommand;
import spinbox.commands.SetNameCommand;
import spinbox.exceptions.SpinBoxException;
import spinbox.exceptions.InputException;

import java.util.ArrayDeque;

public class Parser {
    private static final String INVALID_COMMAND = "Please provide a valid command:\n"
            + "'<action> <page> / <content>' or 'bye'";
    private static ArrayDeque<String> pageTrace;

    public static void setPageTrace(ArrayDeque<String> pageTraceNew) {
        pageTrace = pageTraceNew;
    }

    /**
     * Builds the required page data for command input. Return page and maybe moduleCode
     * @param inputPageData The page data input.
     * @return Full page data.
     * @throws InputException If the input is invalid.
     */
    private static String commandBuilder(String inputPageData) throws InputException {
        String pageData = "";

        String[] pageComponent = inputPageData.split(" ");
        ArrayDeque<String> tempPageTrace = pageTrace.clone();
        // append page or page + module from pageTrace
        if (pageComponent[0].isEmpty()) {
            pageData = pageData.concat(tempPageTrace.getLast());
            tempPageTrace.removeLast();
            if (tempPageTrace.size() != 0) {
                pageData = pageData.concat(" " + tempPageTrace.getLast().toUpperCase());
            }
        // append page from input and maybe moduleCode from pageTrace
        } else if (pageComponent.length == 1) {
            switch (pageComponent[0]) {
            case "main":
            case "calendar":
                pageData = pageData.concat(pageComponent[0]);
                break;
            case "modules":
                pageData = pageData.concat(pageComponent[0]);
                tempPageTrace.removeLast();
                if (tempPageTrace.size() != 0) {
                    pageData = pageData.concat(" " + tempPageTrace.getLast().toUpperCase());
                }
                break;
            default:
                // means inputPageData is a moduleCode
                pageData = pageData.concat("modules " + pageComponent[0].toUpperCase());
            }
        // append "modules" + moduleCode from input
        } else if (pageComponent.length == 2 || pageComponent.length == 3) {
            if (pageComponent[0].equals("modules")) {
                pageData = pageData.concat("modules " + pageComponent[1].toUpperCase());
            } else {
                throw new InputException(INVALID_COMMAND);
            }
        }
        return pageData;
    }

    /**
     * Parses an input string into a workable command.
     * @param input user typed in this string.
     * @return a Command that can executed.
     * @throws SpinBoxException Storage errors or input errors.
     */
    public static Command parse(String input) throws SpinBoxException {
        Command command = null;
        String content = "";
        String action = "";
        String pageData = "";
        String[] pageDataComponents = new String[10];

        String[] slashSeparate = input.split(" / ", 2);
        try {
            if (slashSeparate.length == 1) {
                if (input.toLowerCase().equals("bye")) {
                    action = "bye";
                } else if (input.toLowerCase().trim().equals("help")) {
                    action = "help";
                    content = "";
                } else {
                    throw new InputException(INVALID_COMMAND);
                }
            } else if (slashSeparate[0].toLowerCase().equals("help") && slashSeparate.length == 2) {
                action = "help";
                content = slashSeparate[1].trim();
            } else {
                content = slashSeparate[1].trim();
                String[] frontComponents = slashSeparate[0].split(" ");
                action = frontComponents[0];
                pageData = slashSeparate[0].replace(action, "").trim();
                pageData = commandBuilder(pageData.toLowerCase());
                pageDataComponents = pageData.split(" ");
            }
        } catch (ArrayIndexOutOfBoundsException | StringIndexOutOfBoundsException e) {
            throw new InputException(INVALID_COMMAND);
        }

        switch (action.toLowerCase()) {
        case "bye":
            command = new ExitCommand();
            break;
        case "view":
            command = new ViewCommand(pageDataComponents, content);
            break;
        case "add":
            command = new AddCommand(pageDataComponents, content);
            break;
        case "remove":
            command = new RemoveCommand(pageDataComponents, content);
            break;
        case "update":
            command = new UpdateCommand(pageDataComponents, content);
            break;
        case "remove-multiple":
            command = new RemoveMultipleCommand(pageDataComponents, content);
            break;
        case "update-multiple":
            command = new UpdateMultipleCommand(pageDataComponents, content);
            break;
        case "find":
            command = new FindCommand(pageDataComponents, content);
            break;
        case "set-name":
            command = new SetNameCommand(pageDataComponents, content);
            break;
        case "help":
            command = new HelpCommand(content);
            break;
        default:
            throw new InputException(INVALID_COMMAND);
        }
        return command;
    }
}