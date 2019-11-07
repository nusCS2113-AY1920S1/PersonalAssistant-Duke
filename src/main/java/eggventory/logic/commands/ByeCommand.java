package eggventory.logic.commands;

import eggventory.model.LoanList;
import eggventory.model.PersonList;
import eggventory.model.StockList;
import eggventory.model.TemplateList;
import eggventory.storage.Storage;
import eggventory.commons.enums.CommandType;
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

    /**
     * Closes and exits the application.
     * @param list Unused.
     * @param ui Used to print the goodbye message.
     * @param storage Used to save the stocklist one final time before quitting.
     * @return String object with value null.
     */
    public String executeSaveMoreLists(StockList list, Ui ui, Storage storage, LoanList loanList,
                                       PersonList personList, TemplateList templateList) {
        storage.save(list, loanList, personList, templateList);
        ui.printExitMessage();

        return null;
    }
}
