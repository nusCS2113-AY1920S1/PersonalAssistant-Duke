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
                    + "chapters\n"
                    + "help\n"
                    + "quiz\n"
                    + "select\n"
                    + "result\n"
                    + "history\n"
                    + "undo\n"
                    + "clear\n"
                    + "save\n"
                    + "reset\n"
                    + "exit\n"
                    + "print\n"
                    + "archive\n"
                    + "review\n"
                    + "volume \n"
                    + "menu <command>\n";
        } else {
            try {
                MenuExceptions.checkInput(inputs);
                switch (inputs.get(1)) {
                case "chapters":
                    return "`chapters` to view the list of chapters";
                case "help":
                    return "`help <chapter>` to view the list of problems on kattis "
                            + "that you may wish to solve for the corresponding chapter";
                case "select":
                    return "`select <chapter>` to attempt a quiz on that chapter";
                case "quiz":
                    return "`quiz` to start quiz || next | back | end | <input your answer> "
                            + "when you are attempting a quiz";
                case "result":
                    return "`result` to view your results of the past quizzes you've attempted before";
                case "history":
                    return "`history <number of commands you'd like to view>` to view your past commands";
                case "undo":
                    return "`undo` to reverse a single action or undo <number of steps you'd like to undo> "
                            + "to reverse multiple actions";
                case "clear":
                    return "`clear` to clear all chats";
                case "save":
                    return "`save` to save all your current data";
                case "exit":
                    return "`exit` to exit the programme";
                case "print":
                    return "`print <archive | quiz | user> <filename>.pdf` to print the selected pdf file";
                case "archive":
                    return "`archive <question number>` to archive a question";
                case "review":
                    return "`review <question number>` used after a quiz to find out the "
                            + "correct answer and method for the selected question in the quiz";
                case "volume":
                    return "`volume <sound level>` to adjust the volume level";
                default:
                    return "Error there is so such command, enter `menu` to get the list of available commands.";
                }
            } catch (MenuExceptions e) {
                return e.getMessage();
            }
        }
    }
}
