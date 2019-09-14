package duke;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;

/**
 * Adapted from https://stackoverflow.com/questions/1119385/junit-test-for-system-out-println
 * Helper class to test for capturing the console output of duke.
  */
public class InputTest {

    private final InputStream systemIn = System.in;
    private final OutputStream systemOut = System.out;

    private ByteArrayInputStream testIn;
    private ByteArrayOutputStream testOut;

    @BeforeEach
    public void setUpOutput() {
        testOut =  new ByteArrayOutputStream();
        System.setOut(new PrintStream(testOut));
    }

    void provideInput(String data) {
        testIn = new ByteArrayInputStream(data.getBytes());
        System.setIn(testIn);
    }

    String getOutput() {
        return testOut.toString();
    }

    @AfterEach
    public void restoreSystemInputOutput() {
        System.setIn(systemIn);
        System.setOut((PrintStream) systemOut);
    }
}
