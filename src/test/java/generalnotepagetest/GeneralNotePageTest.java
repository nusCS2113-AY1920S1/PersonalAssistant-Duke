//@@author yueyuu

package generalnotepagetest;

import gazeeebo.exception.DukeException;
import gazeeebo.notes.GeneralNotePage;
import gazeeebo.notes.Module;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class GeneralNotePageTest extends GeneralNotePage {
    private ByteArrayOutputStream output = new ByteArrayOutputStream();
    private PrintStream mine = new PrintStream(output);
    private PrintStream original = System.out;
    private static final String EMPTY_DESCRIPTION = "The description of the command cannot be empty.";

    @BeforeEach
    void setupStream() {
        System.setOut(mine);
    }

    @BeforeEach
    void createNewNotePage() {
        goal = "i want to be a rock star";
        modules.add(new Module("cs2101"));
    }

    @AfterEach
    void restoreStream() {
        System.out.flush();
        System.setOut(original);
    }


    @Test
    void viewGeneralNotePageTest() {
        viewGeneralNotePage();
        assertEquals("Goal: i want to be a rock star\r\n"
                + "\r\n"
                + "Modules:\r\n"
                + "1. cs2101\r\n", output.toString());
    }

    @Test
    void editGoal_userInputsAGoal_success() throws DukeException {
        editGoal("hello");
        assertEquals("Okay we have successfully updated your goal to:\r\n" + "hello\r\n", output.toString());
    }

    @Test
    void editGoal_userDoesNotInputGoal_exceptionThrown() {
        try {
            editGoal("");
            fail();
        } catch (DukeException d) {
            assertEquals(EMPTY_DESCRIPTION, d.getMessage());
        }
    }

    @Test
    void addModule_addingAnExistingModule_errorMessageShown() throws DukeException {
        addModule("cs2101");
        assertEquals("You already have a module with the same name. "
                + "Please add a module with a different name.\r\n", output.toString());
    }

    @Test
    void addModule_addingANotExistingModule_success() throws DukeException {
        addModule("cs2113t");
        assertEquals("Okay we have successfully added this module:\r\n"
                + "cs2113t\r\n", output.toString());
    }


    @Test
    void deleteModule_moduleNonExistent_errorMessageShown() throws DukeException {
        deleteModule("cg2028");
        assertEquals("Sorry there is no such module.\r\n", output.toString());
    }

    @Test
    void deleteModule_moduleExists_success() throws DukeException {
        deleteModule("cs2101");
        assertEquals("Okay we have successfully deleted this module:\r\n"
                + "cs2101\r\n", output.toString());
    }
}