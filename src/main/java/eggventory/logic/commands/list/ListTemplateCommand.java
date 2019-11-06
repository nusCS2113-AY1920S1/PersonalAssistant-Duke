package eggventory.logic.commands.list;

import eggventory.commons.enums.CommandType;
import eggventory.commons.exceptions.BadInputException;
import eggventory.logic.commands.Command;
import eggventory.model.StockList;
import eggventory.model.TemplateList;
import eggventory.storage.Storage;
import eggventory.ui.Ui;

public class ListTemplateCommand extends Command {

    String name;

    public ListTemplateCommand(CommandType type, String name) {
        super(type);
        this.name = name;
    }

    @Override
    public String execute(StockList list, Ui ui, Storage storage) throws BadInputException {
        String output = "";
        if (!TemplateList.templateExists(name)) {
            output = "OOPS That template name does not exist!";
            ui.print(output);
            return output;
        } else {
            output = TemplateList.printTemplateLoans(name);
            ui.drawTable(TemplateList.getTemplateLoanStruct(name));
        }

        ui.print(output);

        return output;
    }
}
