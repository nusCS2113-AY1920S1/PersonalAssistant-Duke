package javacake.commands;

import javacake.DukeException;
import javacake.Profile;
import javacake.ProgressStack;
import javacake.Storage;
import javacake.Ui;

public class HelpCommand extends Command {
    public HelpCommand(String str) {
        type = CmdType.HELP;
        input = str;
    }

    /**
     * Execute S.O.S.
     * @param progressStack TaskList containing current tasks
     * @param ui the Ui responsible for outputting messages
     * @param storage Storage needed to write the updated data
     * @param profile Profile of the user
     * @throws DukeException Error thrown when unable to close reader
     * @return
     */
    @Override
    public String execute(ProgressStack progressStack, Ui ui, Storage storage, Profile profile) throws DukeException {
        if (input.length() == 4) {
            return mainHelp();
        } else if (input.equals("help back")) {
            return backHelp();
        } else if (input.equals("help exit")) {
            return exitHelp();
        } else if (input.equals("help goto")) {
            return gotoHelp();
        } else if (input.equals("help help")) {
            return helpHelp();
        } else if (input.equals("help list")) {
            return listHelp();
        } else if (input.equals("help score")) {
            return scoreHelp();
        } else {
            throw new DukeException("No such command found. Try again.");
        }
    }

    private String mainHelp() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Here is the list of available commands:\n\n");
        stringBuilder.append("- ").append("back").append("\n");
        stringBuilder.append("- ").append("exit").append("\n");
        stringBuilder.append("- ").append("goto 'X' [X = {1,2,..}]").append("\n");
        stringBuilder.append("- ").append("help").append("\n");
        stringBuilder.append("- ").append("list").append("\n");
        stringBuilder.append("- ").append("score").append("\n");
        stringBuilder.append("\nType ~help 'COMMAND_NAME'~ to learn more in-depth!\n");
        return stringBuilder.toString();
    }

    private String backHelp() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("- ").append("back").append("\n");
        stringBuilder.append("Command to jump back to previous state after calling 'goto X'").append("\n");
        return stringBuilder.toString();
    }

    private String exitHelp() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("- ").append("exit").append("\n");
        stringBuilder.append("Command to exit from the program").append("\n");
        return stringBuilder.toString();
    }

    private String gotoHelp() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("- ").append("goto 'X' [X = {1,2,..}]").append("\n");
        stringBuilder.append("Command to jump to next index of contents displayed after calling 'list'").append("\n");
        return stringBuilder.toString();
    }

    private String helpHelp() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("- ").append("help").append("\n");
        stringBuilder.append("I am trying to help you!!!").append("\n");
        return stringBuilder.toString();
    }

    private String listHelp() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("- ").append("list").append("\n");
        stringBuilder.append("Command to display table-of-contents").append("\n");
        return stringBuilder.toString();
    }

    private String scoreHelp() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("- ").append("score").append("\n");
        stringBuilder.append("Command to get your current overall score for JavaCake!").append("\n");
        return stringBuilder.toString();
    }

}
