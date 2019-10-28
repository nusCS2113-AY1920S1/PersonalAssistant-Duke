package command;

import dictionary.Bank;
import dictionary.Word;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import storage.Storage;
import ui.Ui;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class AddTest {
    public String filename = "C:\\Users\\user\\gitclones\\main\\src\\test\\WordUpTest.txt";

    /**
     * Create wordup test file.
     * @throws FileNotFoundException if filename is not found
     * @throws UnsupportedEncodingException if encoding is not supported
     */
    @BeforeEach
    public void createWordUpTestFile() throws FileNotFoundException, UnsupportedEncodingException {
        PrintWriter writer = new PrintWriter(filename, "UTF-8");
        writer.println("apple: red fruit");
        writer.println("orange: orange fruit");
        writer.println("banana: yellow fruit");
        writer.println("kiwi: green fruit");
        writer.close();
    }

    /**
     * Test execute() in AddCommand.java.
     */
    @Test
    public void executeAddCommandTest() {
        try {
            Storage storage = new Storage(filename);
            Bank bank = new Bank(storage);
            Ui ui = new Ui();
            Word word = new Word("hello", "greeting word");
            AddCommand addCommand = new AddCommand(word);
            String add = addCommand.execute(ui, bank, storage);
            assertNotEquals(add, "Got it. I've added this word:\n" + "hello: greeting word\n");
        } catch (Exception e) {
            fail("execute() in AddCommand failed: " + e.getMessage());
        }
    }

    /**
     * Delete wordup test file.
     */
    @AfterEach
    public void deleteWordUpTestFile() {
        File file = new File(filename);
        if (file.delete()) {
            System.out.println("File deleted successfully");
        } else {
            System.out.println("Failed to delete the file");
        }
    }

}
