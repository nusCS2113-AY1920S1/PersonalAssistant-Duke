package planner;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

/**
 * Adapted from https://stackoverflow.com/questions/1119385/junit-test-for-system-out-println.
 * Helper class to test for capturing the console output of planner.
 */
public class InputTest {

    protected ByteArrayInputStream testIn;
    protected ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    protected ByteArrayOutputStream errContent = new ByteArrayOutputStream();

    protected final InputStream originalIn = System.in;
    protected final PrintStream originalOut = System.out;
    protected final PrintStream originalErr = System.err;


    /**
     * Setting stream redirection for planner testing.
     */
    @BeforeEach
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    protected void provideInput(String data) {
        testIn = new ByteArrayInputStream(data.getBytes());
        System.setIn(testIn);
    }

    /**
     * Restoring streams after testing.
     */
    @AfterEach
    public void restoreStreams() {
        System.setIn(originalIn);
        System.setOut(originalOut);
        System.setErr(originalErr);
    }
}
