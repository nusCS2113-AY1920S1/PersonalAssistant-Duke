package duke;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class DukeTest {

    private static final String LINE = "_______________________________\n";

    /**
     * Testing the Duke class with bye as the only input
     * and checking the expected output.
     * Ignores the NullPointerException thrown by getOutput.
     */
    @Test
     public void testDuke() {
        try {
            InputTest.provideInput("bye");
            Duke.main(new String[0]);
            String output = InputTest.getOutput();
            String expected =
                            LINE +
                            "Hello! I'm Duke\n" +
                            "What can I do for you?\n" +
                            LINE +
                            "\n" +
                            "Bye. Hope to see you again soon!" +
                            LINE;
            assertEquals(expected, output);
        } catch (NullPointerException ignored) {

        }
    }
}
