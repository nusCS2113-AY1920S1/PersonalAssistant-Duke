package command;

import dictionary.Bank;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import storage.Storage;
import ui.Ui;

import java.io.File;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class SetReminderCommandTest {

    private Storage storage;
    private Bank bank;
    private Ui ui;
    protected static ArrayList<String> reminderWordList;
    protected String userResponse;

    @BeforeEach
    void testSetup() {
        ui = new Ui();
        bank = new Bank();
        storage = new Storage("setReminderCommandTestData.txt", "setReminderCommandTest.xlsx", "setReminderCommandTestReminder.txt");
    }

    @Test
    void setReminderCaseOneTest() {
        try {
            String uiResponse = "Please enter the list of words.\n" + "Enter an empty line to end input";
            Command testSetReminderObject = new SetReminderCommand(1);
            assertEquals(uiResponse, testSetReminderObject.execute(ui, bank, storage));
            System.out.println("SetReminderCommandTest: executeCaseOneTest() passed.");
        } catch (Exception e) {
            fail("executeCaseOneTest() in SetReminderCommandTest failed: " + e.getMessage());
        }
    }

    @Test
    void setReminderCaseTwoTest() {
        try {
            String uiResponse = "Enter next word or an empty line to end input\n";
            Command testSetReminderObject = new SetReminderCommand(2, "testWord");
            assertEquals(uiResponse, testSetReminderObject.execute(ui, bank, storage));
        } catch (Exception e) {
            fail("executeCaseTwoTest() in SetReminderCommandTest failed: " + e.getMessage());
        }
    }

    @Test
    void setReminderCaseThreeTest() {
        try {
            String uiResponse = "Please enter the date and time of the reminder in the format:"
                    + "dd-MM-yyyy HHmm";
            Command testSetReminderObject = new SetReminderCommand(3);
            assertEquals(uiResponse, testSetReminderObject.execute(ui, bank, storage));
        } catch (Exception e) {
            fail("executeCaseThreeTest() in SetReminderCommandTest failed: " + e.getMessage());
        }
    }

    @Test
    void setReminderCaseFourTest() {
        try {
            reminderWordList = new ArrayList<>();
            reminderWordList.add("testWord1");
            reminderWordList.add("testWord2");
            userResponse = "12-02-2019 0000";
            String uiResponse = "Done! You will be reminded on:\n" + "Tue Feb 12 00:00:00 SGT 2019"
                    + " to study these words:\n" + "testWord1\n" + "testWord2\n";
            Command testSetReminderObject = new SetReminderCommand(4, reminderWordList, userResponse);
            assertEquals(uiResponse, testSetReminderObject.execute(ui, bank, storage));
        } catch (Exception e) {
            fail("executeCaseFourTest() in SetReminderCommandTest failed: " + e.getMessage());
        }
    }

    @AfterEach
    void cleanUp() {
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