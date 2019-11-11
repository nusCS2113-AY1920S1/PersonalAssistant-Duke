package eggventory.logic.commands.delete;

import eggventory.commons.enums.CommandType;
import eggventory.commons.exceptions.BadInputException;
import eggventory.logic.commands.Command;
import eggventory.model.PersonList;
import eggventory.model.StockList;
import eggventory.model.loans.Person;
import eggventory.storage.Storage;
import eggventory.ui.Ui;

//@@author patwaririshab
public class DeletePersonCommand extends Command {
    private String matricNo;



    public DeletePersonCommand(CommandType type, String matricNo) {
        super(type);
        this.matricNo = matricNo;
    }

    @Override
    public String execute(StockList list, Ui ui, Storage storage) throws BadInputException {
        String output;
        try {
            Person deletedPerson = PersonList.delete(matricNo);
            output = (String.format("Nice, I have deleted this person for you: %s", deletedPerson.getName()));
        } catch (BadInputException e) {
            output = (String.format("No person with matric no: %s was found. Please recheck your input", matricNo));
        }

        ui.print(output);
        ui.drawTable(PersonList.getAllPersonStruct());
        return output;
    }
}
//@@author
