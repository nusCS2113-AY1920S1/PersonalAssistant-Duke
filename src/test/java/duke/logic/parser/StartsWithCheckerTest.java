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

class StartsWithCheckerTest {
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
    void someCommandStartsWithInputKeyword() {
        ArrayList<String> commandList = new ArrayList<>(Arrays.asList("bye", "help", "list", "delete", "find",
                "done", "edit", "task", "autoassign"));
        ArrayList<String> commandsToTest = new ArrayList<>(Arrays.asList("by", "h", "l", "del", "fi",
                "do", "e", "t", "auto"));
        for (int i = 0; i < commandList.size(); i++) {
            final String input = "Y";
            provideInput(input);
            String filteredString = StartsWithChecker.checkStartsWithAnyCommand(commandsToTest.get(i));
            assertEquals(filteredString, commandList.get(i));
        }
    }

    @Test
    void someCommandStartsWithInputKeywordButReject() {
        ArrayList<String> commandList = new ArrayList<>(Arrays.asList("bye", "help", "list", "delete", "find",
                "done", "edit", "task", "autoassign"));
        ArrayList<String> commandsToTest = new ArrayList<>(Arrays.asList("by", "h", "l", "del", "fi",
                "do", "e", "t", "auto"));
        for (int i = 0; i < commandList.size(); i++) {
            final String input = "N";
            provideInput(input);
            String filteredString = StartsWithChecker.checkStartsWithAnyCommand(commandsToTest.get(i));
            assertNotEquals(filteredString, commandList.get(i));
        }
    }

    @Test
    void noCommandStartsWithInputKeyword() {
        ArrayList<String> commandList = new ArrayList<>(Arrays.asList("bye", "help", "list", "delete", "find",
                "done", "edit", "task", "autoassign"));
        ArrayList<String> commandsToTest = new ArrayList<>(Arrays.asList("ye", "elp", "ist", "elete", "ind",
                "one", "dit", "ask", "utoassign"));
        for (int i = 0; i < commandList.size(); i++) {
            String filteredString = StartsWithChecker.checkStartsWithAnyCommand(commandsToTest.get(i));
            assertNotEquals(filteredString, commandList.get(i));
        }
    }

    void commandIsSubsetOfKeyWord() {
        ArrayList<String> commandList = new ArrayList<>(Arrays.asList("bye", "help", "list", "delete", "find",
                "done", "edit", "task", "autoassign"));
        ArrayList<String> commandsToTest = new ArrayList<>(Arrays.asList("byee", "helpp", "listt", "deletee", "findd",
                "donee", "editt", "taskk", "autoassignn"));
        for (int i = 0; i < commandList.size(); i++) {
            String filteredString = StartsWithChecker.checkStartsWithAnyCommand(commandsToTest.get(i));
            assertNotEquals(filteredString, commandList.get(i));
        }
    }
}
