//@@author yueyuu
package gazeeebo.notes;

import gazeeebo.UI.Ui;
import gazeeebo.exception.DukeException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class GeneralNotePageTest2 extends GeneralNotePage{
    private ByteArrayOutputStream output = new ByteArrayOutputStream();
    private PrintStream mine = new PrintStream(output);
    private PrintStream original = System.out;
    private Ui ui = new Ui();

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
    void restoreStream(){
        System.out.flush();
        System.setOut(original);
    }

    @Test
    void viewGeneralNotePageTest() {
        viewGeneralNotePage();
        assertEquals("Goal: i want to be a rock star\r\n" +
                "\r\n" +
                "Modules:\r\n" +
                "1. cs2101\r\n", output.toString());
    }

    @Test
    void editGoal() {
        goal = "hello";
        System.out.println("Okay we have successfully updated your goal to:");
        System.out.println(goal);
        assertEquals("Okay we have successfully updated your goal to:\r\n" + "hello\r\n", output.toString());
    }

    @Test
    void addModule_addingAnExistingModule_errorMessageShown() throws DukeException {
        addModule("cs2101");
        assertEquals("You already have a module with the same name. " +
                "Please add a module with a different name.\r\n", output.toString());
    }

    @Test
    void deleteModule() {
    }
}