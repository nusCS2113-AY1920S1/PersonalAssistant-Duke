package eggventory.logic.commands.add;

import eggventory.commons.enums.CommandType;
import eggventory.commons.exceptions.BadInputException;
import eggventory.model.PersonList;
import eggventory.model.StockList;
import eggventory.model.loans.Person;
import eggventory.storage.Storage;
import eggventory.stubs.StorageStub;
import eggventory.stubs.UiStub;
import eggventory.ui.Ui;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

//@@author cyanoei
class AddPersonCommandTest {

    private StockList testStockList = new StockList();
    private Ui testCli = new UiStub();
    private Storage testStorage = new StorageStub();

    @BeforeEach
    void resetPersonList() throws BadInputException {
        if (PersonList.getSize() == 0) {
            return;
        }

        ArrayList<Person> persons = PersonList.getPersonList();
        ArrayList<String> matrics = new ArrayList<>();

        for (Person person : persons) {
            matrics.add(person.getMatricNo());
        }

        for (String matric : matrics) {
            PersonList.delete(matric);
        }
    }

    @Test
    void testExecuteAddPerson_ValidPerson_Succeeds() {
        AddPersonCommand cmd =  new AddPersonCommand(CommandType.ADD, "A12343A", "Akshay "
                + "Narayan");
        assertDoesNotThrow(() -> cmd.execute(testStockList, testCli, testStorage));
    }

    @Test
    void testExecuteAddPerson_RepeatedPerson_ThrowsBadInputException() {
        AddPersonCommand cmd =  new AddPersonCommand(CommandType.ADD, "A1", "John Doe");
        assertDoesNotThrow(() -> cmd.execute(testStockList, testCli, testStorage));
        assertThrows(BadInputException.class, () -> cmd.execute(testStockList, testCli, testStorage));
    }
    //@@author
}