package moomoo.command;

import moomoo.feature.Budget;
import moomoo.feature.MooMooException;
import moomoo.feature.ScheduleList;
import moomoo.feature.Ui;
import moomoo.feature.category.CategoryList;
import moomoo.stubs.StorageStub;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ScheduleCommandTest {
    @Test
    void testScheduleCommand() throws MooMooException, IOException {
        File budgetFile = File.createTempFile("budget", ".txt");
        budgetFile.deleteOnExit();

        File scheduleFile = File.createTempFile("schedule", ".txt");
        scheduleFile.deleteOnExit();

        File expenditureFile = File.createTempFile("expenditure", ".txt");
        expenditureFile.deleteOnExit();

        String input = "schedule d/02/11/19 a/100 n/pay school fees";
        ScheduleCommand scheduled = new ScheduleCommand(false, input);
        StorageStub newStorage = new StorageStub();
        ScheduleList newCalendar = new ScheduleList();
        Budget newBudget = new Budget();
        CategoryList newCat = new CategoryList();
        scheduled.execute(newCalendar, newBudget, newCat, newStorage);
        String cow =
                "._______________________________________.\n"
                        + "| ___  ___ _  _ ___ ___  _   _ _    ___ |\n"
                        + "|/ __|/ __| || | __|   \\| | | | |  | __||\n"
                        + "|\\__ \\ (__| __ | _|| |) | |_| | |__| _| |\n"
                        + "||___/\\___|_||_|___|___/ \\___/|____|___||\n"
                        + "|                                       |\n"
                        + "|Date : 02/11/19                        |\n"
                        + "|Task : pay school fees                 |\n"
                        + "|Amount : 100                           |\n"
                        + ".---------------------------------------.\n"
                        + "        \\   ^__^\n"
                        + "         \\  (oo)\\_______\n"
                        + "            (__)\\       )\\/\\\n"
                        + "                ||----w |\n"
                        + "                ||     ||\n";
        assertEquals(cow, Ui.getOutput());
    }

    @Test
    void testScheduleException() throws IOException {
        File budgetFile = File.createTempFile("budget", ".txt");
        budgetFile.deleteOnExit();

        File scheduleFile = File.createTempFile("schedule", ".txt");
        scheduleFile.deleteOnExit();

        File expenditureFile = File.createTempFile("expenditure", ".txt");
        expenditureFile.deleteOnExit();

        ScheduleList newCalendar = new ScheduleList();
        StorageStub newStorage = new StorageStub();
        Budget newBudget = new Budget();
        CategoryList newCat = new CategoryList();
        String input = "schedule d/31/11/19 a/100 n/electricity bills";
        ScheduleCommand newSchedule = new ScheduleCommand(false, input);
        Throwable thrown;
        thrown = assertThrows(MooMooException.class, () -> {
            newSchedule.execute(newCalendar, newBudget, newCat, newStorage);
        });
        assertEquals("MOOO!!! Invalid date input!\n"
                + "Check if your month is within 01-12.\n"
                + "Check if your day input is valid for that month.\n"
                + "Check if your year is a leap year if your day is Feb 29.", thrown.getMessage());

        input = "schedule d/30/11/19 a/asdf n/electricity bills";
        ScheduleCommand testAmountSchedule = new ScheduleCommand(false, input);
        thrown = assertThrows(MooMooException.class, () -> {
            testAmountSchedule.execute(newCalendar, newBudget, newCat, newStorage);
        });
        assertEquals("MOOO!!! Only numbers accepted for amount.", thrown.getMessage());
    }
}
