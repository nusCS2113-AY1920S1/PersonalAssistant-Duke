package eggventory.logic.commands.add;

import eggventory.commons.enums.CommandType;
import eggventory.commons.exceptions.BadInputException;
import eggventory.logic.commands.add.AddPersonCommand;
import eggventory.model.PersonList;
import eggventory.model.StockList;
import eggventory.storage.Storage;
import eggventory.stubs.StorageStub;
import eggventory.stubs.UiStub;
import eggventory.ui.Cli;
import eggventory.ui.Ui;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


class AddPersonCommandTest {

    private StockList testStockList = new StockList();
    private Ui testCli = new UiStub();
    private Storage testStorage = new StorageStub();
    private PersonList personList = new PersonList();

    @Test
    void testExecuteAddPerson_ValidPerson_Succeeds() throws BadInputException {
        AddPersonCommand cmd =  new AddPersonCommand(CommandType.ADD, "A12343A", "Akshay "
                + "Narayan");
        assertDoesNotThrow(() -> cmd.execute(testStockList, testCli, testStorage));
    }

    //    @Test
    //    void testExecuteAddPerson_RepeatedPerson_ThrowsBadInputException() throws BadInputException {
    //        // Adding of A12345A again not needed as PersonList is static.
    //        Exception exception = Assertions.assertThrows(BadInputException.class, () ->
    //                new AddPersonCommand(CommandType.ADD, "A12345A", "Not Akshay Narayan")
    //                        .execute(testStockList,testCli,testStorage)
    //        );
    //
    //        assertEquals("The Person with the specified matriculation number already exists!\n Did you"
    //                + " mean to edit this Person's details instead?", exception.getMessage());
    //    }
}