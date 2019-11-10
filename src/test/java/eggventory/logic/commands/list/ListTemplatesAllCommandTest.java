package eggventory.logic.commands.list;

import eggventory.commons.enums.CommandType;
import eggventory.model.StockList;
import eggventory.model.TemplateList;
import eggventory.model.loans.Loan;
import eggventory.storage.Storage;
import eggventory.stubs.StorageStub;
import eggventory.stubs.UiStub;
import eggventory.ui.Ui;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ListTemplatesAllCommandTest {
    private Loan[] loans = new Loan[1];
    private String testTemplateName = "Test Template";

    private StockList testStockList = new StockList();
    private Ui testCli = new UiStub();
    private Storage testStorage = new StorageStub();

    ListTemplatesAllCommandTest() {
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

    @Test
    void execute_HasTemplates_ReturnsStringSuccess() {
        ListTemplatesAllCommand commandUnderTest = new ListTemplatesAllCommand(CommandType.LIST);
        TemplateList.addTemplate(testTemplateName, loans);

        assertEquals("Success",
                commandUnderTest.execute(testStockList, testCli, testStorage));
    }

    @Test
    void execute_NoTemplates_ReturnsStringFailed() {
        ListTemplatesAllCommand commandUnderTest = new ListTemplatesAllCommand(CommandType.LIST);

        assertEquals("There are no templates stored.",
                commandUnderTest.execute(testStockList, testCli, testStorage));
    }
}