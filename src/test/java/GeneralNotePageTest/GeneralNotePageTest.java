package GeneralNotePageTest;

import gazeeebo.notes.GeneralNotePage;
import gazeeebo.notes.Module;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class GeneralNotePageTest extends GeneralNotePage{
    private ByteArrayOutputStream output = new ByteArrayOutputStream();
    private PrintStream mine = new PrintStream(output);
    private PrintStream original = System.out;

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
        assertEquals("Goal: " + goal + "\n\n" + "Modules:\n" + "1. cs2101\n", output.toString());
    }

    @Test
    void editGoal() {
    }

    @Test
    void addModule() {
    }

    @Test
    void deleteModule() {
    }
}