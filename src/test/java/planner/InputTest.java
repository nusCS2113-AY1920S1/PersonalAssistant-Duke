//@@author namiwa

package planner;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

/**
 * Modified from Stack Overflow answer @@author Antonio Vinicius Menezes Medei:
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

    /**
     * Credits to @@author MaVRoSCy from this StackOverflow post:
     * https://stackoverflow.com/questions/17103660/print-string-with-escape-non-printable-characters
     * Shows un-printable characters in the given string.
     * @param input Any string parameter, used for checking output from commandline.
     * @return Escapes printed string, converting to escaped expression.
     */
    public static String removeUnicodeAndEscapeChars(String input) {
        StringBuilder buffer = new StringBuilder(input.length());
        for (int i = 0; i < input.length(); i++) {
            if ((int) input.charAt(i) > 256) {
                buffer.append("\\u").append(Integer.toHexString((int) input.charAt(i)));
            } else {
                if (input.charAt(i) == '\n') {
                    buffer.append("\\n");
                } else if (input.charAt(i) == '\t') {
                    buffer.append("\\t");
                } else if (input.charAt(i) == '\r') {
                    buffer.append("\\r");
                } else if (input.charAt(i) == '\b') {
                    buffer.append("\\b");
                } else if (input.charAt(i) == '\f') {
                    buffer.append("\\f");
                } else if (input.charAt(i) == '\'') {
                    buffer.append("\\'");
                } else if (input.charAt(i) == '\"') {
                    buffer.append("\\");
                } else if (input.charAt(i) == '\\') {
                    buffer.append("\\\\");
                } else {
                    buffer.append(input.charAt(i));
                }
            }
        }
        return buffer.toString();
    }
}
