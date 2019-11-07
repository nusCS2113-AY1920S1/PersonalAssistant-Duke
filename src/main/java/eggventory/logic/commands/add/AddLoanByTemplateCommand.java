package eggventory.logic.commands.add;

import eggventory.commons.enums.CommandType;
import eggventory.commons.exceptions.BadInputException;
import eggventory.logic.commands.Command;
import eggventory.model.LoanList;
import eggventory.model.StockList;
import eggventory.storage.Storage;
import eggventory.ui.Ui;

public class AddLoanByTemplateCommand extends Command {

    private String matricNo;
    private String name;

    /**
     * Adds new loans to a matric using a loan template.
     * @param matricNo Matric number of student to add the loans to.
     * @param name Name of template to get loans from.
     */
    public AddLoanByTemplateCommand(CommandType type, String matricNo,  String name) {
        super(type);
        this.matricNo = matricNo;
        this.name = name;
    }

    @Override
    public String execute(StockList list, Ui ui, Storage storage) throws BadInputException {

        String output = LoanList.addLoanByTemplate(matricNo, name);

        if (output == null) {
            output = "OOPS! Template does not exist!";
        }

        ui.print(output);

        return output;
    }
}
