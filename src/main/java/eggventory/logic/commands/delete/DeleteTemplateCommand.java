package eggventory.logic.commands.delete;

import eggventory.commons.enums.CommandType;
import eggventory.commons.exceptions.BadInputException;
import eggventory.logic.commands.Command;
import eggventory.model.StockList;
import eggventory.model.TemplateList;
import eggventory.storage.Storage;
import eggventory.ui.Ui;

//@@author patwaririshab
public class DeleteTemplateCommand extends Command {
    private String templateName;

    public DeleteTemplateCommand(CommandType type, String templateName) {
        super(type);
        this.templateName = templateName;
    }

    @Override
    public String execute(StockList list, Ui ui, Storage storage) throws BadInputException {
        String output;
        TemplateList.deleteTemplate(templateName);
        output = (String.format("Nice, I have deleted this template for you: %s", templateName));

        ui.print(output);
        return output;
    }
}
//@@author
