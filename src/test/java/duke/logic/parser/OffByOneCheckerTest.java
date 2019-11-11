package duke.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import duke.storage.Storage;
import duke.ui.Ui;

class OffByOneCheckerTest {
    private static final String FILE_PATH = "data/editCommandTest.json";

    private static final Ui ui = new Ui();
    private static final Storage storage = new Storage(FILE_PATH);
    private final InputStream systemIn = System.in;
    private final PrintStream systemOut = System.out;

    private ByteArrayInputStream testIn;
    private ByteArrayOutputStream testOut;

    @BeforeEach
    public void setUpOutput() {
        testOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(testOut));
    }

    private void provideInput(String data) {
        testIn = new ByteArrayInputStream(data.getBytes());
        System.setIn(testIn);
    }

    private String getOutput() {
        return testOut.toString();
    }

    @AfterEach
    public void restoreSystemInputOutput() {
        System.setIn(systemIn);
        System.setOut(systemOut);
    }

    @Test
    void offByZero() {
        ArrayList<String> commandList = new ArrayList<>(Arrays.asList("bye", "help", "list", "delete", "find",
                "done", "edit", "task", "autoassign"));
        for (int i = 0; i < commandList.size(); i++) {
            final String input = "Y";
            provideInput(input);
            assertEquals(OffByOneChecker.offByOne(commandList.get(i)), commandList.get(i));
        }
    }

    @Test
    void offByOne() {
        ArrayList<String> commandList = new ArrayList<>(Arrays.asList("bye", "help", "list", "delete", "find",
                "done", "edit", "task", "autoassign"));
        ArrayList<String> commandsToTest = new ArrayList<>(Arrays.asList("bee", "yelp", "gist", "delrte", "fund",
                "donj", "rdit", "tdsk", "autoafsign"));
        for (int i = 0; i < commandList.size(); i++) {
            final String input = "Y";
            provideInput(input);
            assertEquals(OffByOneChecker.offByOne(commandsToTest.get(i)), commandList.get(i));
        }
    }

    @Test
    void offByOneTrueButReject() {
        ArrayList<String> commandList = new ArrayList<>(Arrays.asList("bye", "help", "list", "delete", "find",
                "done", "edit", "task", "autoassign"));
        ArrayList<String> commandsToTest = new ArrayList<>(Arrays.asList("bee", "yelp", "gist", "delrte", "fund",
                "donj", "rdit", "tdsk", "autoafsign"));
        for (int i = 0; i < commandList.size(); i++) {
            final String input = "N";
            provideInput(input);
            assertNotEquals(OffByOneChecker.offByOne(commandsToTest.get(i)), commandList.get(i));
        }
    }

    @Test
    void offByMoreThanOne() {
        ArrayList<String> commandList = new ArrayList<>(Arrays.asList("bye", "help", "list", "delete", "find",
                "done", "edit", "task", "autoassign"));
        ArrayList<String> commandsToTest = new ArrayList<>(Arrays.asList("qee", "yely", "gisr", "delrtw", "funj",
                "dfnj", "rdif", "tdsf", "autfafsign"));
        for (int i = 0; i < commandList.size(); i++) {
            assertNotEquals(OffByOneChecker.offByOne(commandsToTest.get(i)), commandList.get(i));
        }
    }

    @Test
    void extraLetters() {
        ArrayList<String> commandList = new ArrayList<>(Arrays.asList("bye", "help", "list", "delete", "find",
                "done", "edit", "task", "autoassign"));
        ArrayList<String> commandsToTest = new ArrayList<>(Arrays.asList("byee", "helpe", "liste", "deletew", "findt",
                "edone", "vedit", "gtask", "hautoassign"));
        for (int i = 0; i < commandList.size(); i++) {
            assertNotEquals(OffByOneChecker.offByOne(commandsToTest.get(i)), commandList.get(i));
        }
    }

    @Test
    void lessLetters() {
        ArrayList<String> commandList = new ArrayList<>(Arrays.asList("bye", "help", "list", "delete", "find",
                "done", "edit", "task", "autoassign"));
        ArrayList<String> commandsToTest = new ArrayList<>(Arrays.asList("by", "hel", "lis", "delet", "fin",
                "one", "dit", "ask", "utoassign"));
        for (int i = 0; i < commandList.size(); i++) {
            assertNotEquals(OffByOneChecker.offByOne(commandsToTest.get(i)), commandList.get(i));
        }
    }
}
