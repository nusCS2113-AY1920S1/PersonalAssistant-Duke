package planner.logic.command;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import planner.main.CliLauncher;
import planner.main.InputTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ReminderTest extends InputTest {
    private String expectedBye = "_______________________________\n"
            +
            "Thanks for using ModPlanner!\n"
            +
            "Your data will be stored in file shortly!\n"
            +
            "_______________________________\n"
            +
            "_______________________________\n";

    @DisplayName("show reminder test")
    @Test
    public void testShow() {
        final String test = "reminder one\n";

        final String bye = "bye";
        provideInput(test + bye);
        final String[] hold = {""};
        CliLauncher.main(hold);
        String expectedShowModule = "_______________________________\n"
                +
                "Welcome to ModPlanner, your one stop solution to module planning!\n"
                +
                "Begin typing to get started!\n"
                +
                "_______________________________\n"
                +
                "_______________________________\n"
                +
                "Please remember to update your module information!\n"
                +
                "To do so, you can input the update command in the following format:\n"
                +
                "update module\n"
                +
                expectedBye;
        assertEquals(expectedShowModule, getReplace());
    }
}
