//@@author carrieng0323852

package com.algosenpai.app.logic.command;

import com.algosenpai.app.exceptions.MenuExceptions;

import java.util.ArrayList;

public class MenuCommand extends Command {

    /**
     * Initializes command to show available commands.
     * @param inputs user input.
     */
    public MenuCommand(ArrayList<String> inputs) {
        super(inputs);
    }

    @Override
    public String execute() {
        if (inputs.size() == 1) {
            return "Senpai will teach you! Try these commands\n"
                    + "hello\n"
                    + "help\n"
                    + "select\n"
                    + "result\n"
                    + "history\n"
                    + "undo\n"
                    + "clear\n"
                    + "reset\n"
                    + "save\n"
                    + "exit\n"
                    + "print\n"
                    + "archive\n"
                    + "review\n"
                    + "menu <command>\n";
        } else {
            try {
                MenuExceptions.checkInput(inputs);
                switch (inputs.get(1)) {
                case "hello":
                    return "hello <name> <boy | girl>";
                case "help":
                    return "help <chapter>";
                case "select":
                    return "select <chapter>";
                case "quiz":
                    return "quiz || quiz < answer | back | next | end >";
                case "result":
                    return "result";
                case "history":
                    return "history <number of commands you'd like to view>";
                case "undo":
                    return "`undo` to reverse a single action or undo <number of steps you'd like to undo> "
                           + "to reverse multiple actions";
                case "clear":
                    return "clear";
                case "reset":
                    return "reset";
                case "save":
                    return "save";
                case "exit":
                    return "exit";
                case "print":
                    return "print <archive | quiz | user> <filename>.pdf";
                case "archive":
                    return "archive <question number>";
                case "review":
                    return "review <question number>";
                case "volume":
                    return "volume <sound level>";
                default:
                    return "Error there is so such command, enter `menu` to get the list of available commands.";
                }
            } catch (MenuExceptions e) {
                return e.getMessage();
            }
        }
    }
}
