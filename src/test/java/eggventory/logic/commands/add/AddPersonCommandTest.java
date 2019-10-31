package eggventory.logic.commands.add;

import eggventory.commons.enums.CommandType;
import eggventory.commons.exceptions.BadInputException;
import eggventory.logic.commands.add.AddPersonCommand;
import eggventory.model.PersonList;
import eggventory.model.StockList;
import eggventory.storage.Storage;
import eggventory.ui.Cli;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

//@@author Deculsion
class AddPersonCommandTest {

    private StockList testStockList = new StockList();
    private Cli testCli = new Cli();
    private Storage testStorage = new Storage("","");
    private PersonList personList = new PersonList();

    @Test
    void testExecuteAddPerson_ValidPerson_Succeeds() throws BadInputException {
        String output = new AddPersonCommand(CommandType.ADD, "A12345A", "Akshay Narayan")
                .execute(testStockList, testCli, testStorage);

        assertEquals("Nice, I have added this person for you.\n"
                + "Matriculation No.: A12345A | Name: Akshay Narayan", output);

    }

    @Test
    void testExecuteAddPerson_RepeatedPerson_ThrowsBadInputException() throws BadInputException {
        // Adding of A12345A again not needed as PersonList is static.
        Exception exception = assertThrows(BadInputException.class, () ->
                new AddPersonCommand(CommandType.ADD, "A12345A", "Akshay Narayan")
                        .execute(testStockList,testCli,testStorage)
        );

        assertEquals("The Person with the specified matriculation number already exists!\n Did you"
                + " mean to edit this Person's details instead?", exception.getMessage());
    }
    //@@author
}