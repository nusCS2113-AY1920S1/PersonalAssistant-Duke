package eggventory.model;

import eggventory.model.loans.Loan;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;

class TemplateListTest {

    Loan[] loans = new Loan[1];

    public TemplateListTest() {
        loans[0] = new Loan("TestCode", "1");
    }

    @Test
    void addTemplate() {
        assertTrue(TemplateList.addTemplate("Test Template", loans));
        assertFalse(TemplateList.addTemplate("Test Template", loans));
    }

    @Test
    void deleteTemplate() {
        TemplateList.addTemplate("Test Template", loans);

        assertFalse(TemplateList.deleteTemplate("No delete"));
        assertTrue(TemplateList.deleteTemplate("Test Template"));
    }

    @Test
    void getTemplateLoans() {
        TemplateList.addTemplate("Test Name", loans);

        assertNull(TemplateList.getTemplateLoans("Fake Name"));

    }

    @Test
    void printTemplateLoans() {
        TemplateList.addTemplate("Test Template", loans);

        assertEquals("StockCode: TestCode, Quantity: 1\n",
                TemplateList.printTemplateLoans("Test Template"));
    }
}