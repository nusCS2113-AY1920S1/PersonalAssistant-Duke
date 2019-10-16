package spinbox;

import spinbox.commands.AddCommand;
import spinbox.commands.Command;
import spinbox.commands.ExitCommand;
import spinbox.commands.ViewCommand;

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
        StringBuilder pageData = new StringBuilder();

        String[] pageComponent = inputPageData.split(" ");
        ArrayDeque<String> tempPageTrace = pageTrace.clone();
        // append page or page + module from pageTrace
        if (pageComponent[0].isEmpty()) {
            pageData.append(tempPageTrace.getLast());
            tempPageTrace.removeLast();
            if (tempPageTrace.size() != 0) {
                pageData.append(" ").append(tempPageTrace.getLast());
            }
        // append page from input and maybe moduleCode from pageTrace
        } else if (pageComponent.length == 1) {
            switch (pageComponent[0]) {
            case "main":
            case "calendar":
                pageData.append(pageComponent[0]);
                break;
            case "modules":
                pageData.append(pageComponent[0]);
                tempPageTrace.removeLast();
                if (tempPageTrace.size() != 0) {
                    pageData.append(" ").append(tempPageTrace.getLast());
                }
                break;
            default:
                // means inputPageData is a moduleCode
                if (tempPageTrace.getLast().equals("modules")) {
                    pageData.append("modules ").append(pageComponent[0]);
                } else {
                    throw new InputException("Invalid input.");
                }
            }
        // append "modules" + moduleCode from input
        } else if (pageComponent.length == 2) {
            if (pageComponent[0].equals("modules")) {
                pageData.append("modules ").append(pageComponent[1]);
            } else {
                throw new InputException("Please input a valid command.");
            }
        }
        return pageData.toString();
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

        String[] colonSeparate = input.split(" : ");
        try {
            if (colonSeparate.length == 1) {
                if (input.equals("bye")) {
                    action = "bye";
                } else {
                    throw new InputException("Please give valid command:\n"
                            + "'<action> <page> : <content>' or 'bye'");
                }
            } else {
                content = colonSeparate[1].trim();
                String[] frontComponents = colonSeparate[0].split(" ");
                action = frontComponents[0];
                pageData = colonSeparate[0].replace(action.concat(" "), "");
                pageData = commandBuilder(pageData);
                pageDataComponents = pageData.split(" ");
            }
        } catch (ArrayIndexOutOfBoundsException | StringIndexOutOfBoundsException e) {
            throw new InputException("Please give valid command:\n"
                    + "'<action> <page> : <content>' or 'bye'");
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