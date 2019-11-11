package command;

import dictionary.Bank;
import dictionary.Word;
import dictionary.WordBank;
import dictionary.WordCount;
import exception.NoWordFoundException;
import exception.WordAlreadyExistsException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import parser.Parser;
import storage.Storage;
import ui.Ui;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.fail;

/**
 *  Tests for search command.
 */

public class SearchTest {
    public String filename;
    public String excelFileName;
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
     * test execute() in SearchCommand.java
     */
    @Test
    public void searchCommandTest() {
        try {
            SearchCommand searchCommand = new SearchCommand("banana");
            String search = searchCommand.execute(ui, bank, storage);
            Assertions.assertEquals(search, "Here is the meaning of banana: yellow fruit\n");
        } catch (Exception e) {
            fail("execute() in SearchCommand failed: " + e.getMessage());
        }
    }

    /**
     * test if search command is case insensitive.
     */
    @Test
    public void searchCaseInsensitiveTest() {
        try {
            Command searchCommand = Parser.parse("search w/BANANA");
            String search = searchCommand.execute(ui, bank, storage);
            Assertions.assertEquals(search, "Here is the meaning of banana: yellow fruit\n");
            searchCommand = Parser.parse("search w/apple");
            search = searchCommand.execute(ui, bank, storage);
            Assertions.assertEquals(search, "Here is the meaning of apple: red fruit\n");
        } catch (Exception e) {
            fail("Failed to search case sensitive words: " + e.getMessage());
        }
    }

    /**
     * test Oxford search with word in oxford dictionary.
     */
    @Test
    public void oxfordSearchPositiveTest() {
        try {
            String search = OxfordCall.onlineSearch("guava");
            Assertions.assertEquals(search, "an edible, pale orange tropical fruit with pink juicy "
                    + "flesh and a strong sweet aroma.");
        } catch (Exception e) {
            fail("oxfordSearchPositiveTest failed: " + e.getMessage());
        }
    }

    /**
     * test Oxford search with word out of oxford dictionary.
     */
    @Test
    public void oxfordSearchNegativeTest() {
        try {
            OxfordCall.onlineSearch("bananaa");
            fail("oxfordSearchNegativeTest failed.");
        } catch (Exception e) {
            assert (e instanceof NoWordFoundException);
        }
    }

    /**
     * test Oxford search with word in oxford dictionary.
     */
    @Test
    public void spellCheckingTest() {
        try {
            String closeWords = bank.getWordBankObject().getClosedWords("bnn").toString();
            Assertions.assertEquals(closeWords, "[banana]");
        } catch (Exception e) {
            fail("Spell checking failed: " + e.getMessage());
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
            System.out.println("SearchTest: File deleted successfully");
        } else {
            System.out.println("SearchTest: Failed to delete the file");
        }
    }
}
