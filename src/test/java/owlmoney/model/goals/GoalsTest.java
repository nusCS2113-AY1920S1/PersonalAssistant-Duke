package owlmoney.model.goals;

import org.junit.jupiter.api.Test;
import owlmoney.model.bank.Saving;
import owlmoney.model.goals.exception.GoalsException;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.text.ParseException;
import java.util.Date;

public class GoalsTest {
    @Test
    void goalsStub_normalGoalsStub_success() throws ParseException {
        GoalsStub testGoals = new GoalsStub();

        double goalsAmount = testGoals.getGoalsAmount();
        double expectedAmount = 500;
        assertEquals(expectedAmount, goalsAmount);

        String goalsName = testGoals.getGoalsName();
        String expectedName = "TEST GOALS";
        assertEquals(expectedName, goalsName);

        String goalsDate = testGoals.getGoalsDate();
        String expectedDate = "20 October 2020";
        assertEquals(expectedDate, goalsDate);

        String goalsSavingAcc = testGoals.getSavingAccount();
        String expectedSavingAcc = "-NOT TIED-";
        assertEquals(expectedSavingAcc, goalsSavingAcc);

        String goalsRemaining = testGoals.getRemainingAmount();
        String expectedRemaining = "500.00";
        assertEquals(expectedRemaining, goalsRemaining);

        int goalsNumDays = testGoals.convertDateToDays();
        int expectedNumDays = 357;
        assertEquals(expectedNumDays, goalsNumDays);

        String goalsGetStatus = testGoals.getStatus();
        String expectedGetStatus = "N";
        assertEquals(goalsGetStatus, expectedGetStatus);
    }

    @Test
    void goalsStub_normalGoalsStubWithSavingAcc_success() throws ParseException {
        GoalsStub testGoals = new GoalsStub("Jun Saving");

        double goalsAmount = testGoals.getGoalsAmount();
        double expectedAmount = 5000;
        assertEquals(expectedAmount, goalsAmount);

        String goalsName = testGoals.getGoalsName();
        String expectedName = "TEST SAVING GOALS";
        assertEquals(expectedName, goalsName);

        String goalsDate = testGoals.getGoalsDate();
        String expectedDate = "20 October 2020";
        assertEquals(expectedDate, goalsDate);

        String goalsSavingAcc = testGoals.getSavingAccount();
        String expectedSavingAcc = "Jun Saving";
        assertEquals(expectedSavingAcc, goalsSavingAcc);

        int goalsNumDays = testGoals.convertDateToDays();
        int expectedNumDays = 357;
        assertEquals(expectedNumDays, goalsNumDays);

        String goalsGetStatus = testGoals.getStatus();
        String expectedGetStatus = "N";
        assertEquals(goalsGetStatus, expectedGetStatus);
    }

    @Test
    void goalsStubSetAcc_goalsStubNewAccount_success() throws ParseException {
        GoalsStub testGoals = new GoalsStub();
        testGoals.setGoalsName("Jun");
        String goalsNewName = testGoals.getNewName();
        String expectedNewName = "Jun";
        assertEquals(expectedNewName, goalsNewName);

        GoalsStub testSavingGoals = new GoalsStub("Saving");
        testSavingGoals.setGoalsName("Jun Saving");
        String goalsSavingNewName = testSavingGoals.getNewName();
        String expectedSavingNewName = "Jun Saving";
        assertEquals(expectedSavingNewName, goalsSavingNewName);
    }

    @Test
    void goalsStubSetAmount_goalsStubNewAmount_success() throws ParseException {
        GoalsStub testGoals = new GoalsStub();
        testGoals.setGoalsAmount(100);
        double goalsNewAmount = testGoals.getNewAmount();
        double expectedNewAmount = 100;
        assertEquals(expectedNewAmount, goalsNewAmount);

        GoalsStub testSavingGoals = new GoalsStub("Jun");
        testSavingGoals.setGoalsAmount(500);
        double goalsSavingNewAmount = testSavingGoals.getNewAmount();
        double expectedSavingNewAmount = 500;
        assertEquals(expectedSavingNewAmount, goalsSavingNewAmount);

        double goalsSavingRemainAmount = testSavingGoals.getNewRemainAmount();
        double expectedSavingRemainAmount = 400;
        assertEquals(expectedSavingRemainAmount, goalsSavingRemainAmount);
    }

    @Test
    void goals_normalGoals_success() {
        Goals testGoals = new Goals("TEST GOALS", 1000, new Date("10/20/2020"));

        String actualName = testGoals.getGoalsName();
        String expectedName = "TEST GOALS";
        assertEquals(expectedName, actualName);

        double actualAmount = testGoals.getGoalsAmount();
        double expectedAmount = 1000;
        assertEquals(expectedAmount, actualAmount);

        String actualDate = testGoals.getGoalsDate();
        String expectedDate = "20 October 2020";
        assertEquals(expectedDate, actualDate);

        testGoals.isDone(Double.parseDouble(testGoals.getRemainingAmount()));
        boolean actualGetRawStatus = testGoals.getRawStatus();
        boolean expectedGetRawStatus = false;
        assertEquals(expectedGetRawStatus, actualGetRawStatus);

        String actualStatus = testGoals.getStatus();
        String expectedStatus = "N";
        assertEquals(expectedStatus, actualStatus);
    }

    @Test
    void goals_normalSavingGoals_success() {
        Saving saving = new Saving("TEST SAVING", 1000, 100);
        Goals testGoals = new Goals("TEST SAVING GOALS", 1000,
                new Date("10/20/2020"), saving);

        String actualName = testGoals.getGoalsName();
        String expectedName = "TEST SAVING GOALS";
        assertEquals(expectedName, actualName);

        double actualAmount = testGoals.getGoalsAmount();
        double expectedAmount = 1000;
        assertEquals(expectedAmount, actualAmount);

        String actualDate = testGoals.getGoalsDate();
        String expectedDate = "20 October 2020";
        assertEquals(expectedDate, actualDate);

        String actualSavingName = testGoals.getSavingAccount();
        String expectedSavingName = "TEST SAVING";
        assertEquals(expectedSavingName, actualSavingName);

        String actualRemainingAmount = testGoals.getRemainingAmount();
        String expectedRemainingAmount = "0.00";
        assertEquals(expectedRemainingAmount, actualRemainingAmount);

        testGoals.isDone(Double.parseDouble(testGoals.getRemainingAmount()));
        boolean actualGetRawStatus = testGoals.getRawStatus();
        boolean expectedGetRawStatus = true;
        assertEquals(expectedGetRawStatus, actualGetRawStatus);

        String actualStatus = testGoals.getStatus();
        String expectedStatus = "Y";
        assertEquals(expectedStatus, actualStatus);

    }

    @Test
    void goalsSetAmount_newAmount_success() {
        Goals testGoals = new Goals("TEST GOALS", 1000,
                new Date("10/20/2020"));
        testGoals.setGoalsAmount(10000);
        double actualNewAmount = testGoals.getGoalsAmount();
        double expectedNewAmount = 10000;
        assertEquals(expectedNewAmount, actualNewAmount);

        Saving saving = new Saving("TEST SAVING", 1000, 100);
        Goals testSavingGoals = new Goals("TEST SAVING GOALS", 1000,
                new Date("10/20/2020"), saving);
        testSavingGoals.setGoalsAmount(10000);
        double actualNewSavingAmount = testGoals.getGoalsAmount();
        double expectedNewSavingAmount = 10000;
        assertEquals(expectedNewSavingAmount, actualNewSavingAmount);

        String actualNewRemainAmount = testSavingGoals.getRemainingAmount();
        String expectedNewRemainAmount = "9000.00";
        assertEquals(expectedNewRemainAmount, actualNewRemainAmount);
    }

    @Test
    void goalsSetName_newName_success() {
        Goals testGoals = new Goals("TEST GOALS", 1000,
                new Date("10/20/2020"));
        testGoals.setGoalsName("NEW TEST GOALS");
        String actualNewName = testGoals.getGoalsName();
        String expectedNewName = "NEW TEST GOALS";
        assertEquals(expectedNewName, actualNewName);
    }

    @Test
    void goalsSetDate_newDate_success() {
        Goals testGoals = new Goals("TEST GOALS", 1000,
                new Date("01/15/2020"));
        testGoals.setGoalsDate(new Date("10/20/2020"));
        String actualNewDate = testGoals.getGoalsDate();
        String expectedNewDate = "20 October 2020";
        assertEquals(expectedNewDate, actualNewDate);

        Date actualDateInDateFormat = testGoals.getGoalsDateInDateFormat();
        Date expectedDateInDateFormat = new Date("10/20/2020");
        assertEquals(expectedDateInDateFormat, actualDateInDateFormat);
    }

    @Test
    void goalsSetSavingAcc_newSavingAcc_success() throws GoalsException {
        Goals testGoals = new Goals("TEST GOALS", 1000,
                new Date("01/15/2020"));
        testGoals.setSavingAccount(new Saving("TEST SAVING ACC", 100, 100));
        String actualNewSavingAcc = testGoals.getSavingAccount();
        String expectedNewSavingAcc = "TEST SAVING ACC";
        assertEquals(expectedNewSavingAcc, actualNewSavingAcc);

        String actualNewRemainAmount = testGoals.getRemainingAmount();
        String expectedNewRemainAmount = "900.00";
        assertEquals(expectedNewRemainAmount, actualNewRemainAmount);
    }
}
