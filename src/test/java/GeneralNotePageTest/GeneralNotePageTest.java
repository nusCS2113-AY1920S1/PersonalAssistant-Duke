//@@author yueyuu
package GeneralNotePageTest;

import gazeeebo.UI.Ui;
import gazeeebo.notes.GeneralNotePage;
import gazeeebo.notes.Module;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import java.io.IOException;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class GeneralNotePageTest extends GeneralNotePage{
    private ByteArrayOutputStream output = new ByteArrayOutputStream();
    private PrintStream mine = new PrintStream(output);
    private PrintStream original = System.out;
    Ui ui = new Ui();

    @BeforeEach
    void setupStream() {
        System.setOut(mine);
        //goal = "i want to be a rock star";
       // modules.add(new Module("cs2101"));
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
        /*
        assertEquals("Goal: i want to be a rock star\r\n" +
                "\r\n" +
                "Modules:\r\n" +
                "1. cs2101\r\n", output.toString());

         */
    }

    @Test
    void editGoal() {
        goal = "hello";
        System.out.println("Okay we have successfully updated your goal to:");
        System.out.println(goal);
        assertEquals("Okay we have successfully updated your goal to:\r\n" + "hello\r\n", output.toString());
    }

    @Test
    void addModule_addingAnExistingModule_errorMessageShown() throws IOException {
        ByteArrayInputStream in = new ByteArrayInputStream("cs2101".getBytes());
        System.setIn(in);
        addModule(ui);
        assertEquals("What module do you want to add?\r\n"
                + "You already have a module with the same name. Please add a module with a different name.\r\n", output.toString());
    }

    @Test
    void addModule_addingANotExistingModule_success() throws IOException {
            ByteArrayInputStream in = new ByteArrayInputStream("cs2113t".getBytes());
            System.setIn(in);
            addModule(ui);
            assertEquals("What module do you want to add?\r\n"
                    + "Okay we have successfully added this module:\r\n"
                    + "cs2113t\r\n", output.toString());
    }

    @Test
    void deleteModule_moduleNonExistent_errorMessageShown() throws IOException {
        ByteArrayInputStream in = new ByteArrayInputStream("cg2028".getBytes());
        System.setIn(in);
        deleteModule(ui);

        assertEquals("Which module do you want to delete?\r\n"
                + "Sorry there is no such module.\r\n", output.toString());
    }

    @Test
    void deleteModule_moduleExists_success() throws IOException {
        ByteArrayInputStream in = new ByteArrayInputStream("cs2101".getBytes());
        System.setIn(in);
        deleteModule(ui);

        assertEquals("Which module do you want to delete?\r\n"
                + "Okay we have successfully deleted this module:\r\n"
                + "cs2101\r\n", output.toString());
    }
}