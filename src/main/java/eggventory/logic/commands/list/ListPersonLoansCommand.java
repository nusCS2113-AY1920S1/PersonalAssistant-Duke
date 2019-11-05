package eggventory.logic.commands.list;

import eggventory.commons.enums.CommandType;
import eggventory.commons.exceptions.BadInputException;
import eggventory.logic.commands.Command;
import eggventory.model.LoanList;
import eggventory.model.PersonList;
import eggventory.model.StockList;
import eggventory.storage.Storage;
import eggventory.ui.Ui;

//@@author Deculsion
public class ListPersonLoansCommand extends Command {
    String matricNo;

    public ListPersonLoansCommand(CommandType type, String matricNo) {
        super(type);
        this.matricNo = matricNo;
    }

    /**
     * Executes the command.
     * @param list The stock list.
     * @param ui The ui.
     * @param storage The Storage.
     */
    @Override
    public String execute(StockList list, Ui ui, Storage storage) throws BadInputException {
        String output = "";

        output += LoanList.printPersonLoans(matricNo);

        ui.drawTable(LoanList.getPersonLoansStruct(matricNo));
        ui.print(output);

        return output;
    }
}
