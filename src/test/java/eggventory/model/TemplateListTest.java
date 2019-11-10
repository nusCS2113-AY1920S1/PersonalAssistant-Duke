package eggventory.model;

import eggventory.model.loans.Loan;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class TemplateListTest {

    private Loan[] loans = new Loan[1];
    private String testTemplateName = "Test Template";

    TemplateListTest() {
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
    void addTemplate_NewTemplate_ReturnsTrue() {
        assertTrue(TemplateList.addTemplate(testTemplateName, loans));
    }

    @Test
    void addTemplate_DuplicateTemplate_ReturnsFalse() {
        TemplateList.addTemplate(testTemplateName, loans);
        assertFalse(TemplateList.addTemplate(testTemplateName, loans));
    }

    @Test
    void deleteTemplate_TemplateExists_ReturnsTrue() {
        TemplateList.addTemplate(testTemplateName, loans);
        assertTrue(TemplateList.deleteTemplate(testTemplateName));
    }

    @Test
    void deleteTemplate_TemplateDoesNotExist_ReturnsFalse() {
        assertFalse(TemplateList.deleteTemplate("No delete"));
    }

    @Test
    void getTemplateLoans_TemplateExists_ReturnsLoanArr() {
        TemplateList.addTemplate(testTemplateName, loans);
        Loan[] testLoans = TemplateList.getTemplateLoans(testTemplateName);
        assertNotNull(testLoans);
        assertEquals(loans[0], testLoans[0]);
    }

    @Test
    void getTemplateLoans_TemplateDoesNotExist_ReturnsNull() {
        TemplateList.addTemplate(testTemplateName, loans);
        assertNull(TemplateList.getTemplateLoans("Fake Name"));
    }

    @Test
    void templateExists_TemplateExists_ReturnsTrue() {
        TemplateList.addTemplate(testTemplateName, loans);
        assertTrue(TemplateList.templateExists(testTemplateName));
    }

    @Test
    void templateExists_TemplateNotExists_ReturnsFalse() {
        assertFalse(TemplateList.templateExists(testTemplateName));
    }

    @Test
    void printTemplateLoans_TemplateExists_ReturnsTemplatesString() {
        TemplateList.addTemplate(testTemplateName, loans);

        assertEquals("StockCode: TestCode, Quantity: 1\n",
                TemplateList.printTemplateLoans(testTemplateName));
    }

    @Test
    void printTemplateLoans_NoTemplates_ReturnsNoTemplatesString() {
        assertEquals("The template does not exist!\n",
                TemplateList.printTemplateLoans(testTemplateName));
    }
}