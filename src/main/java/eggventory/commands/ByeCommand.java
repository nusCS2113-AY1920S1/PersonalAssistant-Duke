package eggventory.commands;

import eggventory.StockList;
import eggventory.Storage;
import eggventory.enums.CommandType;
import eggventory.ui.Ui;

public class ByeCommand extends Command {

    public ByeCommand(CommandType type) {
        super(type);
    }

    /**
     * Closes and exits the application.
     * @param list Unused.
     * @param ui Used to print the goodbye message.
     * @param storage Used to save the stocklist one final time before quitting.
     * @return String object with value null.
     */
    public String execute(StockList list, Ui ui, Storage storage) {
        storage.save(list);
        ui.printExitMessage();

        return null;
    }
}
