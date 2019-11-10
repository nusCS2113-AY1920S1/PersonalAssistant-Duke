//@@author yueyuu

package moduletest;

import gazeeebo.exception.DukeException;
import gazeeebo.notes.Assessment;
import gazeeebo.notes.GeneralNotePage;
import gazeeebo.notes.Module;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class ModuleTest {
    private ByteArrayOutputStream output = new ByteArrayOutputStream();
    private PrintStream mine = new PrintStream(output);
    private PrintStream original = System.out;
    private static final String EMPTY_DESCRIPTION = "The description of the command cannot be empty.";
    private Module module = new Module("cg2028");
    private GeneralNotePage gnp = new GeneralNotePage();


    @BeforeEach
    void setupStream() {
        System.setOut(mine);
    }

    @BeforeEach
    void setupPages() {
        module.assessments.add(new Assessment("project", 45));
        module.miscellaneousInfo.add("no webcasts");

    }

    @AfterEach
    void restoreStream() {
        System.out.flush();
        System.setOut(original);
    }

    @Test
    void editName_newNameExists_errorMessageShown() throws DukeException {
        gnp.addModule("cg1111");
        module.editName("cg1111");
        assertEquals("Okay we have successfully added this module:\r\n" + "cg1111\r\n"
                + "You already have a module with the same name. Please use a different name.\r\n", output.toString());
        gnp.deleteModule("cg1111");
    }

    @Test
    void editName_newNameDoesNotAlreadyExist_success() throws DukeException {
        module.editName("cg1112");
        assertEquals("Okay we have successfully updated the module name to:\r\n"
                + "cg1112\r\n", output.toString());
    }

    @Test
    void addAssessment_weightageIsAString_exceptionThrown() {
        try {
            module.addAssessment("exam /ahello");
            fail();
        } catch (DukeException d) {
            assertEquals("Please input a number for the weightage.", d.getMessage());
        }
    }

    @Test
    void addAssessment_weightageIsANegativeNum_exceptionThrown() {
        try {
            module.addAssessment("exam /a-35");
            fail();
        } catch (DukeException d) {
            assertEquals("Please input a positive number for the weightage.", d.getMessage());
        }
    }

    @Test
    void addAssessment_noWeightageGivenAndGotSlashA_exceptionThrown() {
        try {
            module.addAssessment("exam /a");
            fail();
        } catch (DukeException d) {
            assertEquals("Please input a weightage.", d.getMessage());
        }
    }

    @Test
    void addAssessment_noWeightageGivenAndNoSlashA_exceptionThrown() {
        try {
            module.addAssessment("exam ");
            fail();
        } catch (DukeException d) {
            assertEquals("Please input the command in the format \'add assmt /n NAME /a WEIGHTAGE\'.", d.getMessage());
        }
    }

    @Test
    void addAssessment_weightageGivenIsPositive_success() throws DukeException {
        module.addAssessment("exam /a 45");
        assertEquals("Okay we have successfully added this assessment:\r\n"
                + "exam (45%)\r\n", output.toString());
    }

    @Test
    void editAssessmentName_assmtNameNotProvidedAndGotSlashA_exceptionThrown() {
        try {
            module.editAssessmentName("1 /a");
            fail();
        } catch (DukeException d) {
            assertEquals("Please input a new assessment name.", d.getMessage());
        }
    }

    @Test
    void editAssessmentName_assmtNameNotProvidedAndNoSlashA_exceptionThrown() {
        try {
            module.editAssessmentName("1 ");
            fail();
        } catch (DukeException d) {
            assertEquals("Please input the command in the format \'edit assmt /n INDEX /a NEW_NAME\'.",
                    d.getMessage());
        }
    }

    @Test
    void editAssessmentName_noDescriptionAtAll_exceptionThrown() {
        try {
            module.editAssessmentName("");
            fail();
        } catch (DukeException d) {
            assertEquals(EMPTY_DESCRIPTION, d.getMessage());
        }
    }

    @Test
    void editAssessmentName_inputFormatCorrect_success() throws DukeException {
        module.editAssessmentName("1 /a new assmt");
        assertEquals("Okay we have successfully changed the name of \"project (45%)\" to:\r\n"
                + "new assmt\r\n", output.toString());
    }

    @Test
    void editAssessmentWeightage_weightageIsaPositiveNumber_success() throws DukeException {
        module.editAssessmentWeightage("1 /a 34");
        assertEquals("Okay we have successfully changed the weightage to:\r\n"
                + "34%\r\n", output.toString());
    }

    @Test
    void deleteAssessment_indexIsAPositiveNumber_success() throws DukeException {
        module.deleteAssessment("1");
        assertEquals("Okay we have successfully deleted this assessment:\r\n"
                + "project (45%)\r\n", output.toString());
    }

    @Test
    void deleteAssessment_indexIsANegativeNumber_exceptionThrown() {
        try {
            module.deleteAssessment("-10");
        } catch (DukeException d) {
            assertEquals("Sorry there is no such index.", d.getMessage());
        }
    }

    @Test
    void deleteAssessment_indexIsZero_exceptionThrown() {
        try {
            module.deleteAssessment("0");
        } catch (DukeException d) {
            assertEquals("Sorry there is no such index.", d.getMessage());
        }
    }

    @Test
    void deleteAssessment_indexIsAString_exceptionThrown() {
        try {
            module.deleteAssessment("*");
        } catch (DukeException d) {
            assertEquals("Please input a number for the index.", d.getMessage());
        }
    }

    @Test
    void addMiscellaneous_descriptionProvided_success() throws DukeException {
        module.addMiscellaneous("new msc");
        assertEquals("Okay we have successfully added this miscellaneous information:\r\n"
                + "new msc\r\n", output.toString());
    }

    @Test
    void addMiscellaneous_descriptionIsEmpty_exceptionThrown() {
        try {
            module.addMiscellaneous("");
            fail();
        } catch (DukeException d) {
            assertEquals(EMPTY_DESCRIPTION, d.getMessage());
        }
    }

    @Test
    void editMiscellaneous_descriptionProvided_success() throws DukeException {
        module.editMiscellaneous("1 /aedited msc");
        assertEquals("Okay we have successfully changed \"no webcasts\" to:\r\n"
                + "edited msc\r\n", output.toString());
    }

    @Test
    void editMiscellaneous_newMscNotProvidedAndWithSlashA_exceptionThrown() {
        try {
            module.editMiscellaneous("1 /a");
            fail();
        } catch (DukeException d) {
            assertEquals("Please input a new miscellaneous information.", d.getMessage());
        }
    }

    @Test
    void editMiscellaneous_newMscNotProvidedAndWithoutSlashA_exceptionThrown() {
        try {
            module.editMiscellaneous("1");
            fail();
        } catch (DukeException d) {
            assertEquals("Please input the command in the format \'edit msc /n INDEX /a NEW_DESCRIPTION\'.",
                    d.getMessage());
        }
    }

    @Test
    void deleteMiscellaneous_indexIsAPositiveNum_success() throws DukeException {
        module.deleteMiscellaneous("1");
        assertEquals("Okay we have successfully deleted this miscellaneous information:\r\n"
                + "no webcasts\r\n", output.toString());
    }

    @Test
    void deleteMiscellaneous_indexIsZero_exceptionThrown() {
        try {
            module.deleteMiscellaneous("0");
            fail();
        } catch (DukeException d) {
            assertEquals("Sorry there is no such index.", d.getMessage());
        }
    }

    @Test
    void deleteMiscellaneous_indexIsNegative_exceptionThrown() {
        try {
            module.deleteMiscellaneous("-2");
            fail();
        } catch (DukeException d) {
            assertEquals("Sorry there is no such index.", d.getMessage());
        }
    }

    @Test
    void deleteMiscellaneous_indexIsString_exceptionThrown() {
        try {
            module.deleteMiscellaneous("#");
            fail();
        } catch (DukeException d) {
            assertEquals("Please input a number for the index.", d.getMessage());
        }
    }
}