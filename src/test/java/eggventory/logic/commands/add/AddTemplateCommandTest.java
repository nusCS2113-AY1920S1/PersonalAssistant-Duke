package eggventory.logic.commands.add;

import eggventory.commons.enums.CommandType;
import eggventory.commons.exceptions.BadInputException;
import eggventory.model.StockList;
import eggventory.model.TemplateList;
import eggventory.model.loans.Loan;
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
class AddTemplateCommandTest {
    private ArrayList<Pair<String,String>> loanPairs = new ArrayList<>();
    private String testTemplateName;

    private StockList testStockList = new StockList();
    private Ui testCli = new UiStub();
    private Storage testStorage = new StorageStub();

    AddTemplateCommandTest() {
        testTemplateName = "Test Template";
        loanPairs.add(new Pair<>("TestCode", "1"));
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

    @Test
    void execute_NewTemplate_ReturnsSuccessString() throws BadInputException {
        AddTemplateCommand testCommand = new AddTemplateCommand(CommandType.ADD, testTemplateName, loanPairs);
        assertEquals("StockCode: TestCode, Quantity: 1\n",
                testCommand.execute(testStockList, testCli, testStorage));
    }

    @Test
    void execute_ExistingTemplate_ReturnsFailString() throws BadInputException {
        TemplateList.addTemplate(testTemplateName, new Loan[]{new Loan("TestCode", "1")});
        AddTemplateCommand testCommand = new AddTemplateCommand(CommandType.ADD, testTemplateName, loanPairs);
        assertEquals("OOPS! A template with that name already exists!",
                testCommand.execute(testStockList, testCli, testStorage));
    }
    //@@author
}