package javacake.commands;

import javacake.Logic;
import javacake.exceptions.DukeException;
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
        EDIT, BACK, GOTO, HELP, TREE, CREATENOTE, EDITNOTE, DELETENOTE
    }

    public abstract String execute(Logic logic, Ui ui, StorageManager storageManager)
            throws DukeException;

    /**
     * Method to get the type of command.
     * @return Type of command
     */
    public CmdType type() {
        return type;
    }

    /**
     * Method to get the input inside the command.
     * @return String containing user input in command
     */
    public String input() {
        return input;
    }

    /**
     * Method to check whether command is of type exit.
     * @return true if type is exit, false otherwise
     */
    public boolean isExit() {
        return this.type == CmdType.EXIT;
    }
}
