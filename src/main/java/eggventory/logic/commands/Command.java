package eggventory.logic.commands;

import eggventory.commons.exceptions.BadInputException;
import eggventory.ui.Ui;
import eggventory.model.StockList;
import eggventory.storage.Storage;
import eggventory.commons.enums.CommandType;


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
    public abstract String execute(StockList list, Ui ui, Storage storage) throws BadInputException;
}
