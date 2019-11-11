package eggventory.logic.commands.add;

import eggventory.commons.enums.CommandType;
import eggventory.commons.exceptions.BadInputException;
import eggventory.model.PersonList;
import eggventory.model.StockList;
import eggventory.model.TemplateList;
import eggventory.model.loans.Loan;
import eggventory.model.loans.Person;
import eggventory.storage.Storage;
import eggventory.stubs.StorageStub;
import eggventory.stubs.UiStub;
import eggventory.ui.Ui;
import javafx.util.Pair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

//@@author Deculsion
class AddLoanByTemplateCommandTest {

    private ArrayList<Pair<String,String>> loanPairs = new ArrayList<>();
    private String testTemplateName = "Test Template";
    private String testStockCode = "TestCode";
    private String testQuantity = "1";
    private String testMatric = "TestMatric";
    private Loan[] loans = new Loan[1];


    private StockList testStockList = new StockList();
    private Ui testCli = new UiStub();
    private Storage testStorage = new StorageStub();

    AddLoanByTemplateCommandTest() {
        loanPairs.add(new Pair<>("TestCode", "1"));
        loans[0] = new Loan("TestCode", "1");
    }

    @BeforeEach
    void resetTemplateList() {
        if (TemplateList.isEmpty()) {
            return;
        }

        String str = TemplateList.printTemplateNames();
        String[] names = str.split("\n");

        for (int i = 1; i < names.length; i++) {
            TemplateList.deleteTemplate(names[i]);
        }
    }

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
    void execute_HasTemplate_ReturnSuccessString() throws BadInputException {
        String expectedOutput = "The following loans have been added to " + testMatric + ":\n"
                + testStockCode + ": " + testQuantity + "\n";

        TemplateList.addTemplate(testTemplateName, loans);
        PersonList.add(testMatric, "");

        AddLoanByTemplateCommand commandUnderTest =
                new AddLoanByTemplateCommand(CommandType.ADD, testMatric, testTemplateName);

        assertEquals(expectedOutput, commandUnderTest.execute(testStockList, testCli, testStorage));
    }

    @Test
    void execute_TemplateDoesNotExist_ReturnFailString() throws BadInputException {
        String expectedOutput = "OOPS! Template does not exist!";
        PersonList.add(testMatric, "");

        AddLoanByTemplateCommand commandUnderTest =
                new AddLoanByTemplateCommand(CommandType.ADD, testMatric, testTemplateName);

        assertEquals(expectedOutput, commandUnderTest.execute(testStockList, testCli, testStorage));
    }
    //@@author
}