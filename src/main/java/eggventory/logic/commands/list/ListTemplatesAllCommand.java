package eggventory.logic.commands.list;

import eggventory.commons.enums.CommandType;
import eggventory.commons.exceptions.BadInputException;
import eggventory.logic.commands.Command;
import eggventory.model.StockList;
import eggventory.model.TemplateList;
import eggventory.storage.Storage;
import eggventory.ui.Ui;

public class ListTemplatesAllCommand extends Command {
    public ListTemplatesAllCommand(CommandType type) {
        super(type);
    }

    @Override
    public String execute(StockList list, Ui ui, Storage storage) {
        if (TemplateList.isEmpty()) {
            String output = "There are no templates stored.";
            ui.print(output);
            return output;
        }
        ui.drawTable(TemplateList.getAllTemplateLoanStruct());
        return "Success";
    }
}
