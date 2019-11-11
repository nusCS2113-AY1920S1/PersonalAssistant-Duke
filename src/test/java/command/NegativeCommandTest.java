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
import java.util.ArrayList;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * Positive tests on general functions.
 */
public class NegativeCommandTest {
    private Storage storage;
    private Bank bank;
    private Ui ui;

    /**
     * Create wordup test file.
     * @throws WordAlreadyExistsException when the word to be added already exists
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
     * NegativeTest execute() in AddCommand.java.
     */
    @Test
    public void addCommandNegativeTest() {
        try {
            Word word = new Word("apple", "red fruit");
            AddCommand addCommand = new AddCommand(word);
            String add = addCommand.execute(ui, bank, storage);
            assertEquals(add, " OOPS: This word has already existed in the bank\napple");
        } catch (Exception e) {
            fail("execute() in AddCommand failed: " + e.getMessage());
        }
    }

    /**
     * NegativeTest execute() in DeleteCommand.java.
     */
    @Test
    public void deleteCommandNegativeTest() {
        try {
            DeleteCommand deleteCommand = new DeleteCommand("papaya");
            String delete = deleteCommand.execute(ui, bank, storage);
            assertEquals(delete, " OOPS: I could not find your word: \"papaya\" \n");
        } catch (Exception e) {
            fail("execute() in DeleteCommand failed: " + e.getMessage());
        }
    }

    /**
     * NegativeTest execute() in DeleteCommand.java.
     */
    @Test
    public void searchCommandNegativeTest() {
        try {
            SearchCommand searchCommand = new SearchCommand("papaya");
            String search = searchCommand.execute(ui, bank, storage);
            assertEquals(search, " OOPS: I could not find your word: \"papaya\" \n"
                    + "Unable to locate \"papaya\" in local dictionary.\nLooking up Oxford dictionary.\n\n"
                    + "Here is the meaning of papaya: a tropical fruit shaped like an elongated melon, "
                    + "with edible orange flesh and small black seeds.\n"
                    + "\nAre you looking for these words instead?\nbanana\n");
        } catch (Exception e) {
            fail("execute() in SearchCommand failed: " + e.getMessage());
        }
    }

    /**
     * NegativeTest execute() in AddTagCommand.java.
     */
    @Test
    public void addTagCommandNegativeTest() {
        try {
            ArrayList<String> tags = new ArrayList<String>();
            tags.add("fruit");
            AddTagCommand addTagCommand = new AddTagCommand("papaya", tags);
            String addTag = addTagCommand.execute(ui, bank, storage);
            assertEquals(addTag, " OOPS: I could not find your word: \"papaya\" \n");
        } catch (Exception e) {
            fail("execute() in SearchCommand failed: " + e.getMessage());
        }
    }

    /**
     * NefativeTest execute() in DeleteTagCommand.java.
     */
    @Test
    public void deleteTagCommandNegativeTest() {
        try {
            ArrayList<String> tags = new ArrayList<String>();
            tags.add("fruit");
            DeleteCommand deleteTagCommand = new DeleteCommand("banana", tags);
            String deleteTag = deleteTagCommand.execute(ui,bank,storage);
            assertEquals(deleteTag, "This tag doesn't exist in the word \"banana\"\nfruit\n");
        } catch (Exception e) {
            fail("execute() in SearchCommand failed: " + e.getMessage());
        }
    }

    /**
     * NegativeTest execute() in editCommand.java.
     */
    @Test
    public void editCommandNegativeTest() {
        try {
            EditCommand editCommand = new EditCommand("papaya","orange fruit");
            String edit = editCommand.execute(ui, bank, storage);
            assertEquals(edit, " OOPS: I could not find your word: \"papaya\" \n");
        } catch (Exception e) {
            fail("execute() in EditCommand failed: " + e.getMessage());
        }
    }

    /**
     * NegativeTest execute() in SearchTagCommand.java.
     */
    @Test
    public void searchTagCommandTest() {
        try {
            SearchTagCommand searchTagCommand = new SearchTagCommand("fruit","tag");
            String searchTag = searchTagCommand.execute(ui, bank, storage);
            assertEquals(searchTag, " OOPS: Your tag bank is empty. "
                   + "Please add some tags to your words before viewing it.");
            ArrayList<String> tags = new ArrayList<String>();
            tags.add("fruit");
            AddTagCommand addTagCommand = new AddTagCommand("banana",tags);
            addTagCommand.execute(ui, bank, storage);
            SearchTagCommand searchTagCommand2 = new SearchTagCommand("drink","tag");
            String searchTag2 = searchTagCommand2.execute(ui, bank, storage);
            assertEquals(searchTag2, " OOPS: I could not find your tag: drink\n"
                    + "Here are all of your tags:\nfruit\n");

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
            System.out.println("NegeativeCommandTest: File deleted successfully");
        } else {
            System.out.println("NegeativeCommandTest: Failed to delete the file");
        }
    }
}
