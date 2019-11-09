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
     * test if search command is case insensitive.
     */
    @Test
    public void searchCaseInsensitiveTest() {
        try {
            SearchCommand searchCommand = new SearchCommand("BANANA");
            String search = searchCommand.execute(ui, bank, storage);
            Assertions.assertEquals(search, "Here is the meaning of banana: yellow fruit\n");
            searchCommand = new SearchCommand("APPLE");
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
            String closeWords = wordBank.getClosedWords("bnn").toString();
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
        File file1 = new File(filename);
        File file2 = new File(excelFileName);

        if (file1.delete() && file2.delete()) {
            System.out.println("File deleted successfully"); //note the test/file being used
        } else {
            System.out.println("Failed to delete the file");
        }
    }
}
