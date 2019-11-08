package com.algosenpai.app.commands;

import com.algosenpai.app.logic.Logic;
import com.algosenpai.app.logic.command.Command;
import com.algosenpai.app.stats.UserStats;
import com.algosenpai.app.storage.Storage;
import com.algosenpai.app.ui.Ui;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationTest;

import java.io.IOException;

public class LectureCommandTest extends ApplicationTest {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(HistoryCommandTest.class.getResource("/view/MainWindow.fxml"));
        AnchorPane ap = fxmlLoader.load();
        Scene scene = new Scene(ap, 500, 650);
        stage.setScene(scene);
        UserStats stats = UserStats.parseString(Storage.loadData("UserData.txt"));
        Logic logic = new Logic(stats);
        fxmlLoader.<Ui>getController().setLogic(logic, stats);
        stage.setResizable(false);
        stage.setTitle("AlgoSenpai Adventures");
        stage.show();
    }

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() throws Exception {
        FxToolkit.hideStage();
    }

    @Test
    void testLectureWrongFormat() throws IOException {
        UserStats stats = new UserStats("./UserData.txt");
        Logic logic = new Logic(stats);
        logic.executeCommand("hello Sim boy").execute();
        Command command = logic.executeCommand("lecture");
        String actualText = command.execute();
        String expectedText = "No such chapter found. Please select the following:\n"
                + "lecture <chapter name>\n"
                + "linkedlist\n"
                + "sorting\n"
                + "bitmask\n";
        Assertions.assertEquals(expectedText, actualText);
    }

    @Test
    void testLectureSorting() throws IOException {
        UserStats stats = new UserStats("./UserData.txt");
        Logic logic = new Logic(stats);
        logic.executeCommand("hello Sim boy").execute();

        //Selects topic for lecture.
        testLectureSortingSelect(logic);

        String expectedText;

        //Starts the lecture. Assert that desired output is reached.
        expectedText = testLectureSortingStart(logic);

        //Checks to make sure back command does not work when at the start.
        testLectureSortingBackFailsAtBeginning(logic, expectedText);

        //Checks to make sure that next command works.
        testLectureSortingNextWorks(logic);

        String actualText;

        //Checks to make sure back command works.
        actualText = testLectureSortingBackWorks(logic);

        //Checks to make sure that all the other commands are output correctly.
        testLectureSortingRestWorks(logic, actualText);

        //Checks to see if back command works.
        testLectureSortingBackDoesNotWorkAtEnd(logic);

        //Checks to see if next command works.
        testLectureSortingNextDoesNotWork(logic);

    }

    /**
     * Next command does not work upon termination of lecture.
     * @param logic The logic component.
     * @throws IOException exception.
     */
    private void testLectureSortingNextDoesNotWork(Logic logic) throws IOException {
        String actualText;
        String expectedText;
        actualText = logic.executeCommand("next").execute();
        expectedText = "OOPS!!! Error occurred. Please input a valid command. Did you mean... exit?";
        Assertions.assertEquals(expectedText, actualText);
    }

    /**
     * Back does not work upon termination of lecture.
     * @param logic The logic component.
     * @throws IOException exception.
     */
    private void testLectureSortingBackDoesNotWorkAtEnd(Logic logic) throws IOException {
        String actualText;
        String expectedText;
        actualText = logic.executeCommand("back").execute();
        expectedText = "OOPS!!! Error occurred. Please input a valid command. Did you mean... save?";
        Assertions.assertEquals(expectedText, actualText);
    }

    /**
     * Rest of the slides should work as expected.
     * @param logic The logic component.
     * @param actualText The text that was actually outputted.
     * @throws IOException exception.
     */
    private void testLectureSortingRestWorks(Logic logic, String actualText) throws IOException {
        String expectedText;
        for (int i = 0; i < 31; i++) {
            actualText = logic.executeCommand("next").execute();
        }
        expectedText = "\n[End of lecture]\n"
                + "* Type 'quiz x' where x is a chapter to practise what you learnt.\n"
                + "* Type 'menu' to see other commands.\n"
                + "\n" + "32/32\n";
        Assertions.assertEquals(expectedText, actualText);
    }

    /**
     * Back command should work when it is not the end of the lecture or the beginning of the lecture.
     * @param logic The logic component.
     * @return The string containing the expected output.
     * @throws IOException exception.
     */
    private String testLectureSortingBackWorks(Logic logic) throws IOException {
        String actualText;
        String expectedText;
        actualText = logic.executeCommand("back").execute();
        expectedText = "\nSorting is a very classic problem of reordering items (that can be compared, e.g. integers,"
                + " floating-point numbers, strings, etc) of an array (or a list) in a certain order (increasing,"
                + " non-decreasing, decreasing, non-increasing, lexicographical, etc).\n"
                + "\n" + "1/32\n";
        Assertions.assertEquals(expectedText, actualText);
        return actualText;
    }

    /**
     * Next command should work when lecture begins.
     * @param logic The logic component.
     * @throws IOException exception.
     */
    private void testLectureSortingNextWorks(Logic logic) throws IOException {
        String actualText;
        String expectedText;
        actualText = logic.executeCommand("next").execute();
        expectedText = "\nSorting problem has a variety of interesting algorithmic solutions that embody many "
                + "Computer Science ideas:\n"
                + "\n"
                + "Comparison versus non-comparison based strategies,\n"
                + "Iterative versus Recursive implementation,\n"
                + "Divide-and-Conquer paradigm (this or that),\n"
                + "Best/Worst/Average-case Time Complexity analysis,\n"
                + "Randomized Algorithms, etc.\n"
                + "\n" + "2/32\n";
        Assertions.assertEquals(expectedText, actualText);
    }

    /**
     * Back command should fail at the beginning of the lecture at slide 1.
     * @param logic The logic component.
     * @param expectedText The expected text from the user.
     * @throws IOException exception.
     */
    private void testLectureSortingBackFailsAtBeginning(Logic logic, String expectedText) throws IOException {
        String actualText;
        actualText = logic.executeCommand("back").execute();
        Assertions.assertEquals(expectedText, actualText);
    }

    /**
     * Start command should start the lecture.
     * @param logic The logic component.
     * @return The expected text of the user.
     * @throws IOException exception.
     */
    private String testLectureSortingStart(Logic logic) throws IOException {
        String actualText;
        String expectedText;
        actualText = logic.executeCommand("start").execute();
        expectedText = "\nSorting is a very classic problem of reordering items (that can be compared, e.g. integers,"
                + " floating-point numbers, strings, etc) of an array (or a list) in a certain order (increasing,"
                + " non-decreasing, decreasing, non-increasing, lexicographical, etc).\n"
                + "\n" + "1/32\n";
        Assertions.assertEquals(expectedText, actualText);
        return expectedText;
    }

    /**
     * Select command should lock in the chapter and start the quiz.
     * @param logic The logic component.
     * @throws IOException exception.
     */
    private void testLectureSortingSelect(Logic logic) throws IOException {
        String actualText;
        String expectedText;
        actualText = logic.executeCommand("lecture sorting").execute();
        expectedText = "You have selected Chapter 1. Type 'start' to begin.";
        Assertions.assertEquals(expectedText, actualText);
    }

}
