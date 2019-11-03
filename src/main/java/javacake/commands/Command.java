package javacake.commands;

import javacake.Logic;
import javacake.exceptions.CakeException;
import javacake.storage.StorageManager;
import javacake.ui.Ui;

public abstract class Command {
    protected CmdType type;
    protected String input;

    /**
     * Types of commands that are possible.
     */
    public enum CmdType {
        EXIT, LIST, FIND, DONE, DELETE, TODO, DEADLINE, REMIND, VIEWSCH,
        EDIT, BACK, GOTO, QUIZ, HELP, TREE, CREATENOTE, EDITNOTE, LISTNOTE,
        DELETENOTE
    }

    public abstract String execute(Logic logic, Ui ui, StorageManager storageManager)
            throws CakeException;

    /**
     * Checks if input command has no other parameter appended.
     * @param inputCommand Command input from user.
     * @throws CakeException If parameter is appended to command.
     */
    public static void checksParam(String inputCommand) throws CakeException {
        String bySpaces = "\\s+";
        String[] subStrings = inputCommand.split(bySpaces);
        if (subStrings.length > 1) {
            throw new CakeException("Please ensure there is no parameter(s) for this command");
        }
    }


    /**
     * Method to check whether command is of type exit.
     * @return true if type is exit, false otherwise
     */
    public boolean isExit() {
        return this.type == CmdType.EXIT;
    }
}
