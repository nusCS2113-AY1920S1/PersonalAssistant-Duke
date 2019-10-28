package command;

import dictionary.Bank;
import dictionary.Word;
import dictionary.WordBank;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
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


public class CommandTest {
    public String filename;
    public Storage storage;
    public Bank bank;
    public Ui ui;
    public WordBank wordBank;

    /**
     * Create wordup test file.
     * @throws FileNotFoundException if filename is not found
     * @throws UnsupportedEncodingException if encoding is not supported
     */
    @BeforeEach
    public void createWordUpTestFile() throws FileNotFoundException, UnsupportedEncodingException {
        filename = "C:\\Users\\user\\gitclones\\main\\src\\test\\WordUpTest.txt";
        PrintWriter writer = new PrintWriter(filename, "UTF-8");
        writer.println("apple: red fruit");
        writer.println("orange: orange fruit");
        writer.println("banana: yellow fruit");
        writer.println("kiwi: green fruit");
        writer.close();
        storage = new Storage(filename);
        bank = new Bank(storage);
        ui = new Ui();
        wordBank = new WordBank(storage.loadFile());
    }

    /**
     * Test execute() in AddCommand.java.
     */
    @Test
    public void testAddCommand() {
        try {
            Word word = new Word("hello", "greeting word");
            AddCommand addCommand = new AddCommand(word);
            String add = addCommand.execute(ui, bank, storage);
            assertNotEquals(add, "Got it. I've added this word:\n" + "hello: greeting word\n");
        } catch (Exception e) {
            fail("execute() in AddCommand failed: " + e.getMessage());
        }
    }

    /**
     * test execute() in DeleteCommand.java.
     */
    @Test
    public void testDeleteCommand() {
        try {
            DeleteCommand deleteCommand = new DeleteCommand("orange");
            String delete = deleteCommand.execute(ui, bank, storage);
            assertNotEquals(delete, "Noted. I've removed this word:\n" + "orange: orange fruit\n");

        } catch (Exception e) {
            fail("execute() in DeleteCommand failed: " + e.getMessage());
        }
    }

    /**
     * test generateQuiz() in QuizCommand.java
     */
    @Test
    public void testGenerateQuiz() {
        try {
            QuizCommand quizCommand = new QuizCommand();
            String quiz = quizCommand.generateQuiz(wordBank);
            Assertions.assertTrue((quiz.equals("apple: red fruit")) || (quiz.equals("orange: orange fruit"))
                    || (quiz.equals("banana: yellow fruit")) || (quiz.equals("kiwi: green fruit")));
        } catch (Exception e) {
            fail("generateQuiz failed: " + e.getMessage());
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
