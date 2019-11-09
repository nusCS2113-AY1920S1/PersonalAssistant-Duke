package owlmoney.model.goals;

import org.junit.jupiter.api.Test;
import owlmoney.model.bank.Saving;
import owlmoney.model.goals.exception.GoalsException;
import owlmoney.storage.Storage;
import owlmoney.ui.Ui;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class GoalsListTest {
    private static final String NEWLINE = System.lineSeparator();
    private static final String FILE_PATH = "data/";
    private static final Storage storage = new Storage(FILE_PATH);

    @Test
    void goalsListAddGoals_duplicateGoalsName_throwsException() {
        GoalsList testList = new GoalsList(new Storage("data/"));
        Ui testUi = new Ui();
        Goals newGoals = new Goals("TEST", 100, new Date("10/10/2020"));
        Goals newGoals2 = new Goals("TEST", 100, new Date("10/10/2020"));
        try {
            testList.addToGoals(newGoals, testUi);
        } catch (GoalsException err) {
            System.out.println("Expects success but error was thrown");
        }
        GoalsException thrown = assertThrows(GoalsException.class, () -> testList.addToGoals(newGoals2, testUi));
        assertEquals("There is already a goal with the same name TEST", thrown.toString());
    }

    @Test
    void goalListAddGoals_maxLimit_throwsError() {
        GoalsList testList = new GoalsList(new Storage("data/"));
        Ui testUi = new Ui();

        for (int i = 0; i < 21; i++) {
            Goals newGoals = new Goals(i + "test", 100, new Date("10/10/2020"));
            try {
                testList.addToGoals(newGoals, testUi);
            } catch (GoalsException err) {
                System.out.println("Expects success but error was thrown");
            }
        }

        Goals newGoalsError = new Goals("test", 100, new Date("10/10/2020"));
        GoalsException thrown = assertThrows(GoalsException.class, () -> testList.addToGoals(newGoalsError, testUi),
                "Expected goalsToAdd to throw, but didn't");
        assertEquals("You've reached the limit of 20 goals!", thrown.toString());
    }

    @Test
    void goalsListAddGoals_alrAchieved_throwsError() {
        GoalsList testList = new GoalsList(new Storage("data/"));
        Ui testUi = new Ui();

        Saving newSaving = new Saving("TEST SAVING", 1000, 100);
        Goals newGoals = new Goals("TEST", 100, new Date("10/10/2020"), newSaving);

        GoalsException thrown = assertThrows(GoalsException.class, () -> testList.addToGoals(newGoals, testUi),
                "Expect addToGoals to throw, but didn't");

        assertEquals("You cannot add a goal that is already achieved!", thrown.toString());
    }

    @Test
    void getGoalsListSize_zeroAndOneGoals_returnsZeroFirstThenOne() {
        GoalsList testList = new GoalsList(new Storage("data/"));
        Ui testUi = new Ui();
        assertEquals(0, testList.getGoalListSize());
        Goals newGoals = new Goals("test", 100, new Date("10/10/2020"));
        try {
            testList.addToGoals(newGoals, testUi);

        } catch (GoalsException err) {
            System.out.println("Expects success but error was thrown");
        }
        assertEquals(1, testList.getGoalListSize());
    }

    @Test
    void goalsListDeleteGoals_zeroGoalsAccount_throwsException() {
        GoalsList testList = new GoalsList(new Storage("data/"));
        Ui testUi = new Ui();
        GoalsException thrown = assertThrows(GoalsException.class, () ->
                testList.deleteFromGoalList("test", testUi),
                "Expected to deleteFromGoalsList to throw, but didn't");
        assertEquals("There are no goals set!", thrown.toString());
    }

    @Test
    void goalsListDeleteGoals_goalsDoesNotExist_throwsException() {
        GoalsList testList = new GoalsList(new Storage("data/"));
        Ui testUi = new Ui();
        Goals newGoals = new Goals("test", 100, new Date("10/10/2020"));

        try {
            testList.addToGoals(newGoals, testUi);
        } catch (GoalsException err) {
            System.out.println("Expects success but error was thrown");
        }

        GoalsException thrown = assertThrows(GoalsException.class, () ->
                        testList.deleteFromGoalList("test3", testUi),
                "Expected to deleteFromGoalsList to throw, but didn't");
        assertEquals("There are no goals with the name: test3", thrown.toString());
    }

    @Test
    void goalsListDeleteGoals_deleteOneGoal_success() throws GoalsException {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        GoalsList testList = new GoalsList(new Storage("data/"));
        Ui testUi = new Ui();
        Goals newGoals = new Goals("test", 100, new Date("10/10/2020"));

        try {
            testList.addToGoals(newGoals, testUi);
            assertEquals(1, testList.getGoalListSize());
            outContent.reset();
            testList.deleteFromGoalList("test", testUi);
            String expectedOutput = "Details of the goal being removed:"
                    + NEWLINE + "Item No.  To Accomplish          Amount to save       Saving Account                 "
                    + "Save another         To be achieved by    Goal Achieved        "
                    + NEWLINE + "-----------------------------------------------------------------------------------"
                    + "---------------------------------------------------------"
                    + NEWLINE + "1         test                   $100.00              -NOT TIED-                     "
                    + "$100.00              10 October 2020      N                    "
                    + NEWLINE + "-----------------------------------------------------------------------------------"
                    + "---------------------------------------------------------" + NEWLINE;
            assertEquals(expectedOutput, outContent.toString());
            System.out.println(expectedOutput);
            System.out.println(outContent.toString());
        } catch (GoalsException err) {
            System.out.println("Expects success but error was thrown");
        }
    }

    @Test
    void goalsListListGoal_goalListNotEmpty_listGoals() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        GoalsList testGoals = new GoalsList(new Storage("data/"));
        Goals newGoals = new Goals("test", 100, new Date("10/10/2020"));
        Ui testUi = new Ui();

        try {
            testGoals.addToGoals(newGoals, testUi);
            outContent.reset();
            testGoals.listGoals(testUi);
        } catch (GoalsException err) {
            System.out.println("Expect success but error was thrown");
        }
        String expectedOutput = "Item No.  To Accomplish          Amount to save       Saving Account                 "
                + "Save another         To be achieved by    Goal Achieved        "
                + NEWLINE + "-----------------------------------------------------------------------------------"
                + "---------------------------------------------------------"
                + NEWLINE + "1         test                   $100.00              -NOT TIED-                     "
                + "$100.00              10 October 2020      N                    "
                + NEWLINE + "-----------------------------------------------------------------------------------"
                + "---------------------------------------------------------" + NEWLINE;
        assertEquals(1,testGoals.getGoalListSize());
        assertEquals(expectedOutput,outContent.toString());
    }

    @Test
    void goalsListListGoal_goalListEmpty_throwException() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        GoalsList testGoals = new GoalsList(new Storage("data/"));
        Ui testUi = new Ui();
        testGoals.listGoals(testUi);
        assertEquals("OOPS!!! There are no goals set" + NEWLINE, outContent.toString());
    }

}
