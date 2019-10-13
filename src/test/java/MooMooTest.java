import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MooMooTest {
    @Test
    public void testResponse() throws IOException {
        MooMoo moomoo = new MooMoo();

        String input = "bye";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        ByteArrayOutputStream outArray = new ByteArrayOutputStream();
        PrintStream out = new PrintStream(outArray);
        System.setOut(out);

        moomoo.run();

        ByteArrayInputStream inputStream = new ByteArrayInputStream(outArray.toByteArray());

        Scanner scanner = new Scanner(inputStream);
        String result = scanner.useDelimiter("\\A").next();

        String expectedValue = "   ^____^\n"
                + "   ( oo )\\_______\n"
                + "   (____)\\       )\\/\\\n"
                + "         ||----w |\n"
                + "         ||     ||\n"
                + "MOOOOOOOO\n"
                + "Welcome to MooMooMoney! Your one-stop budgeting and expenses tracker!\n"
                + "What can MooMoo do for you today?\n"
                + "Hope you had a great time using MooMooMoney!\n"
                + "See you next time :)\n";

        assertEquals(expectedValue, result.replaceAll("\\r", ""));
    }
}