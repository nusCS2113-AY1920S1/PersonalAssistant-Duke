package eggventory.logic.commands.list;

import eggventory.commons.enums.CommandType;
import eggventory.logic.commands.Command;
import eggventory.model.PersonList;
import eggventory.model.StockList;
import eggventory.storage.Storage;
import eggventory.ui.Ui;

//@@author Deculsion
public class ListPersonCommand extends Command {
    public ListPersonCommand(CommandType type) {
        super(type);
    }

    /**
     * Executes the command.
     * @param stockList Stocklist object.
     * @param ui ui object.
     * @param storage storage objcet.
     */
    public String execute(StockList stockList, Ui ui, Storage storage) {
        String output = "";

        if (PersonList.getSize() == 0) {
            output = "There is nobody in the list";
            ui.print(output);
            return output;
        }

        output = PersonList.listToString();
        ui.print(output);
        ui.drawTable(PersonList.getAllPersonStruct());

        return output;

    }
}
