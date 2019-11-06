package eggventory.logic.commands.add;

import eggventory.commons.enums.CommandType;
import eggventory.commons.exceptions.BadInputException;
import eggventory.logic.commands.Command;
import eggventory.model.StockList;
import eggventory.model.TemplateList;
import eggventory.model.loans.Loan;
import eggventory.storage.Storage;
import eggventory.ui.Ui;
import javafx.util.Pair;
import java.util.ArrayList;

//@@author Deculsion
public class AddTemplateCommand extends Command {

    String name;
    Loan[] loans;

    /**
     * Creates a new addtemplate command.
      * @param type Enum of the command.
     * @param name Name of the template to create.
     * @param loanPairs Array of pairs of loans to add.
     */
    public AddTemplateCommand(CommandType type, String name, ArrayList<Pair<String, String>> loanPairs) {
        super(type);
        Loan[] loans = new Loan[loanPairs.size()];
        for (int i = 0; i < loans.length; i++) {
            loans[i] = new Loan(loanPairs.get(i).getKey(), loanPairs.get(i).getValue());
        }

        this.name = name;
        this.loans = loans;
    }


    @Override
    public String execute(StockList list, Ui ui, Storage storage) throws BadInputException {
        String output;

        if (TemplateList.templateExists(name)) {
            output = "OOPS! A template with that name already exists!";
        } else {
            TemplateList.addTemplate(name, loans);
            output = TemplateList.printTemplateLoans(name);
        }
      
        ui.print(output);

        return output;

    }
}
//@@author
