package optix.commands;

import optix.commons.Model;
import optix.commons.Storage;
import optix.ui.Ui;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ByeCommandTest {
    private Ui ui;
    private static File currentDir = new File(System.getProperty("user.dir"));
    private static File filePath = new File(currentDir.toString() + "\\src\\test\\data\\testOptix");
    private Storage storage;
    private Model model;

    @BeforeEach
    void init() {
        this.ui = new Ui();
        this.storage = new Storage(filePath);
        this.model = new Model(storage);
    }

    @Test
    void testExecute() {
        ByeCommand testCommand = new ByeCommand();
        testCommand.execute(model, ui, storage);

        String expected = "__________________________________________________________________________________\n"
                + "Bye. Hope to see you again soon!\n"
                + "__________________________________________________________________________________\n";
        assertEquals(expected, ui.showCommandLine());
        assertTrue(new File(filePath, "optix.txt").exists());
        assertTrue(new File(filePath, "archive.txt").exists());
    }

    @Test
    void testExit() {
        Command c = new ByeCommand();
        assertTrue(c.isExit());
    }

    @AfterAll
    static void cleanUp() {
        File deletedFile = new File(filePath, "optix.txt");
        deletedFile.delete();
    }
}