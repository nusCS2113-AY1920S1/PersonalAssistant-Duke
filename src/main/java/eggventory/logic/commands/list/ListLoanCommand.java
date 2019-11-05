package eggventory.logic.commands.list;

import eggventory.commons.enums.CommandType;
import eggventory.commons.exceptions.BadInputException;
import eggventory.logic.commands.Command;
import eggventory.model.LoanList;
import eggventory.model.StockList;
import eggventory.storage.Storage;
import eggventory.ui.Ui;

public class ListLoanCommand extends Command {
    public ListLoanCommand(CommandType type) {
        super(type);
    }

    @Override
    public String execute(StockList list, Ui ui, Storage storage) throws BadInputException {
        String output = LoanList.printLoans();

        ui.drawTable(LoanList.getAllLoansStruct());
        ui.print(output);

        return output;
    }
}
