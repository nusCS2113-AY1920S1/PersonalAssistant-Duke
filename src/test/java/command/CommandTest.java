package command;

import dictionary.Bank;
import dictionary.Word;
import dictionary.WordBank;
import dictionary.WordCount;
import exception.WordAlreadyExistsException;
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

import static org.junit.jupiter.api.Assertions.fail;

/**
 * Positive tests on general functions.
 */
public class CommandTest {
    public String filename;
    public String excelFileName;
    public Storage storage;
    public Bank bank;
    public Ui ui;
    public WordBank wordBank;
    public WordCount wordCount;
    /**
     * Create wordup test file.
     * @throws FileNotFoundException if filename is not found
     * @throws UnsupportedEncodingException if encoding is not supported
     */

    @BeforeEach
    public void createWordUpTestFile() throws WordAlreadyExistsException, FileNotFoundException,
            UnsupportedEncodingException {

        filename = "testdata\\WordUp.txt";
        excelFileName = "testdata\\WordUp.xlsx";

        PrintWriter writer = new PrintWriter(filename, "UTF-8");
        writer.println("apple: red fruit");
        writer.println("orange: orange fruit");
        writer.println("banana: yellow fruit");
        writer.println("kiwi: green fruit");
        writer.close();

        storage = new Storage("\\testdata");
        ui = new Ui();
        bank = storage.loadExcelFile();

        wordCount = bank.getWordCountObject();
        wordBank = bank.getWordBankObject();

        wordBank.addWord(new Word("apple","red fruit"));
        wordBank.addWord(new Word("orange","orange fruit"));
        wordBank.addWord(new Word("banana","yellow fruit"));
        wordBank.addWord(new Word("kiwi","green fruit"));

        wordCount.addWord(new Word("apple","red fruit"));
        wordCount.addWord(new Word("orange","orange fruit"));
        wordCount.addWord(new Word("banana","yellow fruit"));
        wordCount.addWord(new Word("kiwi","green fruit"));

        storage.writeWordBankExcelFile(wordBank);
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
            Assertions.assertEquals(add, "Got it. I've added this word:\n" + "hello: greeting word");
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
            Assertions.assertEquals(delete, "Noted. I've removed this word:\n" + "orange: orange fruit");

        } catch (Exception e) {
            fail("execute() in DeleteCommand failed: " + e.getMessage());
        }
    }

    /**
     * test execute() in EditCommand.java
     */
    @Test
    public void testEditCommand() {
        try {
            EditCommand editCommand = new EditCommand("kiwi", "a kind of bird");
            String edit = editCommand.execute(ui, bank, storage);
            Assertions.assertEquals(edit, "Got it. I've edited this word:\n" + "kiwi: a kind of bird");
        } catch (Exception e) {
            fail("execute() in EditCommand failed: " + e.getMessage());
        }
    }

    /**
     * test execute() in HistoryCommand.java
     */
    @Test
    public void testHistoryCommand() {
        try {
            HistoryCommand historyCommand = new HistoryCommand(2);
            String history = historyCommand.execute(ui, bank, storage);
            System.out.println(history);
            Assertions.assertEquals(history,
                    "Here are the last 2 words you have added:\nkiwi: green fruit\nbanana: yellow fruit\n");
        } catch (Exception e) {
            fail("execute() in HistoryCommand failed: " + e.getMessage());
        }
    }

    /**
     * test execute() in ListCommand.java
     */
    @Test
    public void testListCommand() {
        try {
            ListCommand listCommand = new ListCommand("asc");
            String list = listCommand.execute(ui, bank, storage);
            Assertions.assertEquals(list,
                    "Here are your words:\napple: red fruit\nbanana: yellow fruit\n"
                            + "kiwi: green fruit\norange: orange fruit\n");
        } catch (Exception e) {
            fail("execute() in HistoryCommand failed: " + e.getMessage());
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
     * test execute() in SearchCommand.java
     */
    @Test
    public void testSearchCommand() {
        try {
            SearchCommand searchCommand = new SearchCommand("banana");
            String search = searchCommand.execute(ui, bank, storage);
            Assertions.assertEquals(search, "Here is the meaning of banana: yellow fruit\n");
        } catch (Exception e) {
            fail("execute() in SearchCommand failed: " + e.getMessage());
        }
    }

    /**
     * Delete wordup test file.
     */
    @AfterEach
    public void deleteWordUpTestFile() {
        File file1 = new File(filename);
        File file2 = new File(excelFileName);

        if (file1.delete() && file2.delete()) {
            System.out.println("File deleted successfully"); //note the test/file being used
        } else {
            System.out.println("Failed to delete the file");
        }
    }
}
