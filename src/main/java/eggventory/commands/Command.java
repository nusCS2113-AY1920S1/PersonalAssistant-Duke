package eggventory.commands;

import eggventory.ui.Ui;
import eggventory.StockList;
import eggventory.Storage;
import eggventory.enums.CommandType;

/**
 * This is an abstract class.
 * Command objects are sent from the Parser and executed with StockType or Cli.
 * Commands include: add, delete, find, list.
 */

public abstract class Command {

    protected CommandType type;

    public Command(CommandType type) {
        this.type = type;
    }

    public CommandType getType() {
        return type;
    }

    /**
     * Executes the command. Need to implement if inheriting from Command class.
     */
    public abstract String execute(StockList list, Ui ui, Storage storage);
}
