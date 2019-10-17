import javacake.DukeException;
import javacake.ProgressStack;
import javacake.Ui;
import javacake.Profile;
import javacake.Storage;
import javacake.commands.ListCommand;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ListCommandTest {

    @Test
    public void testListCommand() throws DukeException {
        String line1 = "Here are the 4 subtopics available!";
        String line2 = "1. Java Basics";
        String line3 = "2. Object-Oriented Programming";
        String line4 = "3. Extensions";
        String line5 = "4. Overall Quiz";
        String line6 = "Key in the index to learn more about the topic!";
        StringBuilder sb = new StringBuilder();
        sb.append(line1).append("\n");
        sb.append(line2).append("\n");
        sb.append(line3).append("\n");
        sb.append(line4).append("\n");
        sb.append(line5).append("\n");
        sb.append(line6).append("\n");
        String expectedOutput = sb.toString();

        try {
            ProgressStack ps = new ProgressStack();
            Ui ui = new Ui();
            Profile p = new Profile();
            Storage s = new Storage();
            ListCommand lc = new ListCommand();
            String actualOutput = lc.execute(ps, ui, s, p);
            assertEquals(expectedOutput, actualOutput);
        } catch (DukeException e) {
            throw new DukeException(e.getMessage());
        }
    }

}
