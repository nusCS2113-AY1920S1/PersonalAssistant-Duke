package Duke;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.io.*;

/**
 * Adapted from https://stackoverflow.com/questions/1119385/junit-test-for-system-out-println
 * Helper class to test for capturing the console output of duke.
  */
public class InputTest {


    private final InputStream systemIn = System.in;
    private final OutputStream systemOut = System.out;

    private static ByteArrayInputStream testIn;
    private static ByteArrayOutputStream testOut;

    @BeforeEach
    public void setUpOutput() {
        testOut =  new ByteArrayOutputStream();
        System.setOut(new PrintStream(testOut));
    }

    static void provideInput(String data) {
        testIn = new ByteArrayInputStream(data.getBytes());
        System.setIn(testIn);
    }

    static String getOutput() {
        return testOut.toString();
    }

    @AfterEach
    public void restoreSystemInputOutput() {
        System.setIn(systemIn);
        System.setOut((PrintStream) systemOut);
    }
}
