package command;

import dictionary.Bank;
import dictionary.Word;

import exception.WordAlreadyExistsException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import storage.Storage;
import ui.Ui;

import java.io.File;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * Positive tests on general functions.
 */
public class CommandTest {
    private Storage storage;
    private Bank bank;
    private Ui ui;

    /**
     * Create wordup test file.
     * @throws WordAlreadyExistsException
     */
    @BeforeEach
    public void createWordUpTestFile() throws WordAlreadyExistsException {
        storage = new Storage("commandTestData.txt", "commandTest.xslx", "commandTestReminder.txt");
        ui = new Ui();
        bank = new Bank();

        storage.writeStorage("apple: red fruit", true, "wordup", bank);
        storage.writeStorage("orange: orange fruit", true, "wordup", bank);
        storage.writeStorage("banana: yellow fruit", true, "wordup", bank);
        storage.writeStorage("kiwi: green fruit", true, "wordup", bank);

        bank.addWord(new Word("apple","red fruit"));
        bank.addWord(new Word("orange","orange fruit"));
        bank.addWord(new Word("banana","yellow fruit"));
        bank.addWord(new Word("kiwi","green fruit"));
    }

    /**
     * Test execute() in AddCommand.java.
     */
    @Test
    public void addCommandTest() {
        try {
            Word word = new Word("hello", "greeting word");
            AddCommand addCommand = new AddCommand(word);
            String add = addCommand.execute(ui, bank, storage);
            assertEquals(add, "Got it. I've added this word:\n" + "hello: greeting word");
        } catch (Exception e) {
            fail("execute() in AddCommand failed: " + e.getMessage());
        }
    }

    /**
     * test execute() in DeleteCommand.java.
     */
    @Test
    public void deleteCommandTest() {
        try {
            DeleteCommand deleteCommand = new DeleteCommand("orange");
            String delete = deleteCommand.execute(ui, bank, storage);
            assertEquals(delete, "Noted. I've removed this word:\n" + "orange: orange fruit");

        } catch (Exception e) {
            fail("execute() in DeleteCommand failed: " + e.getMessage());
        }
    }

    /**
     * test execute() in EditCommand.java
     */
    @Test
    public void editCommandTest() {
        try {
            EditCommand editCommand = new EditCommand("kiwi", "a kind of bird");
            String edit = editCommand.execute(ui, bank, storage);
            assertEquals(edit, "Got it. I've edited this word:\n" + "kiwi: a kind of bird");
        } catch (Exception e) {
            fail("execute() in EditCommand failed: " + e.getMessage());
        }
    }

    /**
     * test execute() in HistoryCommand.java
     */
    @Test
    public void historyCommandTest() {
        try {
            HistoryCommand historyCommand = new HistoryCommand(2);
            String history = historyCommand.execute(ui, bank, storage);
            System.out.println(history);
            assertEquals(history,
                    "Here are the last 2 words you have added:\nkiwi: green fruit\nbanana: yellow fruit\n");
        } catch (Exception e) {
            fail("execute() in HistoryCommand failed: " + e.getMessage());
        }
    }

    /**
     * test execute() in ListCommand.java
     */
    @Test
    public void listCommandTest() {
        try {
            ListCommand listCommand = new ListCommand("asc");
            String list = listCommand.execute(ui, bank, storage);
            assertEquals(list,
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
    public void generateQuizTest() {
        try {
            QuizCommand quizCommand = new QuizCommand();
            String quiz = quizCommand.generateQuiz(bank.getWordBankObject());
            assertTrue((quiz.equals("apple: red fruit")) || (quiz.equals("orange: orange fruit"))
                    || (quiz.equals("banana: yellow fruit")) || (quiz.equals("kiwi: green fruit")));
        } catch (Exception e) {
            fail("generateQuiz failed: " + e.getMessage());
        }
    }

    /**
     * test execute() in SearchCommand.java
     */
    @Test
    public void searchCommandTest() {
        try {
            SearchCommand searchCommand = new SearchCommand("banana");
            String search = searchCommand.execute(ui, bank, storage);
            assertEquals(search, "Here is the meaning of banana: yellow fruit\n");
        } catch (Exception e) {
            fail("execute() in SearchCommand failed: " + e.getMessage());
        }
    }

    @Test
    public void helpCommandTest() {
        try {
            SearchCommand searchCommand = new SearchCommand("banana");
            String search = searchCommand.execute(ui, bank, storage);
            assertEquals(search, "Here is the meaning of banana: yellow fruit\n");
        } catch (Exception e) {
            fail("execute() in SearchCommand failed: " + e.getMessage());
        }
    }


    /**
     * Delete wordup test file.
     */
    @AfterEach
    public void deleteWordUpTestFile() {
        File dataFile = new File(Storage.DATA_FILE_PATH);
        File reminderFile = new File(Storage.REMINDER_FILE_PATH);
        File excelFile = new File(Storage.EXCEL_PATH);
        if ((dataFile.delete()) && (reminderFile.delete()) && (excelFile.delete())) {
            System.out.println("SetReminderCommandTest: File deleted successfully");
        } else {
            System.out.println("SetReminderCommandTest: Failed to delete the file");
        }
    }
}
