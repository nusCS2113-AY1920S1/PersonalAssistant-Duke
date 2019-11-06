package helpertests;

import org.junit.jupiter.api.Test;
import util.CommandHelper;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CommandHelperTest {
    private CommandHelper commandHelper = new CommandHelper();
    private ArrayList<String> simulatedOutput;
    private String[] expectedOutput;

    @Test
    void alwaysTrue() {
        assertEquals(2, 2);
    }

    @Test
    void gettingConsoleCommands_successfulExecution() {
        simulatedOutput = commandHelper.getCommandsForConsole();
        expectedOutput = new String[] {
            "List of commands available:",
            " - list",
            "Displays all existing projects.",
            "",
            " - create PROJECT_NAME",
            "Creates a project with the specified name.",
            "",
            " - delete PROJECT_INDEX",
            "Deletes specified project.",
            "",
            " - manage PROJECT_INDEX",
            "Selects the specified project to manage.",
            "",
            " - bye",
            "Saves your data and exits ArchDuke.",
            "",
            " - help",
            "Provides a list of all the commands available.",
        };
        assertArrayEquals(expectedOutput, simulatedOutput.toArray());
    }
}
