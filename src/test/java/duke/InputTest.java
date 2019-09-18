package duke;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

/**
 * Adapted from https://stackoverflow.com/questions/1119385/junit-test-for-system-out-println.
 * Helper class to test for capturing the console output of duke.
 */
public class InputTest {

    protected PrintStream originalSystemOut;
    protected ByteArrayOutputStream systemOutContent;

    /**
     * Setting stream redirection for duke testing.
     */
    @BeforeEach
    public void redirectSystemOutStream() {
        originalSystemOut = System.out;
        systemOutContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(systemOutContent));
    }

    /**
     * Restoring streams after testing.
     */
    @AfterEach
    public void restoreSystemOutStream() {
        System.setOut(originalSystemOut);
    }
}
