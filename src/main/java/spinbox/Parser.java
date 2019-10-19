package spinbox;

import spinbox.commands.AddCommand;
import spinbox.commands.Command;
import spinbox.commands.ExitCommand;
import spinbox.commands.MarkCommand;
import spinbox.commands.RemoveCommand;
import spinbox.commands.ViewCommand;
import spinbox.commands.MultipleCommand;

import spinbox.exceptions.SpinBoxException;
import spinbox.exceptions.InputException;

import java.util.ArrayDeque;

public class Parser {
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
                pageData = pageData.concat(" " + tempPageTrace.getLast());
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
                    pageData = pageData.concat(" " + tempPageTrace.getLast());
                }
                break;
            default:
                // means inputPageData is a moduleCode
                pageData = pageData.concat("modules " + pageComponent[0]);
            }
        // append "modules" + moduleCode from input
        } else if (pageComponent.length == 2 || pageComponent.length == 3) {
            if (pageComponent[0].equals("modules")) {
                pageData = pageData.concat("modules " + pageComponent[1]);
            } else {
                throw new InputException("Please input a valid command.");
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

        String[] slashSeparate = input.split(" / ");
        try {
            if (slashSeparate.length == 1) {
                if (input.equals("bye")) {
                    action = "bye";
                } else {
                    throw new InputException("Please give valid command:\n"
                            + "'<action> <page> / <content>' or 'bye'");
                }
            } else {
                content = slashSeparate[1].trim();
                String[] frontComponents = slashSeparate[0].split(" ");
                action = frontComponents[0];
                pageData = slashSeparate[0].replace(action, "").trim();
                pageData = commandBuilder(pageData);
                pageDataComponents = pageData.split(" ");
            }
        } catch (ArrayIndexOutOfBoundsException | StringIndexOutOfBoundsException e) {
            throw new InputException("Please give valid command:\n"
                    + "'<action> <page> / <content>' or 'bye'");
        }

        switch (action) {
        case "bye":
            command = new ExitCommand();
            break;
        case "view":
            command = new ViewCommand(pageData, content);
            break;
        case "add":
            command = new AddCommand(pageDataComponents[1], content);
            break;
        case "remove":
            command = new RemoveCommand(pageDataComponents[1], content);
            break;
        case "mark":
            command = new MarkCommand(pageDataComponents[1], content);
            break;
        case "remove-multiple":
            command = new MultipleCommand(pageDataComponents[1], content);
            break;
        default:
        }
        return command;

        //      **This will be an example of how to turn the input into commands.**
        //
        //        try {
        //            switch (action) {
        //                case "done":
        //                    String moduleCode = pageDataComponents[1];
        //                    command = new DoneCommand(moduleCode, Integer.parseInt(content) - 1);
        //            }
        //        } catch (NumberFormatException e) {
        //            throw new InputException("Please enter an integer for index");
        //        }
    }
}