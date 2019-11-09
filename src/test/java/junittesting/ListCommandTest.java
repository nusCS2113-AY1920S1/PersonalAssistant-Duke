package junittesting;

import javacake.exceptions.CakeException;
import javacake.Logic;
import javacake.storage.StorageManager;
import javacake.ui.Ui;
import javacake.commands.ListCommand;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class ListCommandTest {

    @Test
    public void testListCommand() {
        String line1 = "Here are the 4 subtopics available!";
        String line2 = "1. Java Basics";
        String line3 = "2. Object-Oriented Programming";
        String line4 = "3. Extensions";
        String line5 = "4. Overall Test";
        String line6 = "Key in the index to learn more about the topic or do the quiz!";
        StringBuilder sb = new StringBuilder();
        sb.append(line1).append("\n");
        sb.append(line2).append("\n");
        sb.append(line3).append("\n");
        sb.append(line4).append("\n");
        sb.append(line5).append("\n");
        sb.append(line6).append("\n");
        String expectedOutput = sb.toString();

        try {
            Logic logic = Logic.getInstance();
            Ui ui = new Ui();
            StorageManager sm = new StorageManager();
            ListCommand lc = new ListCommand("list");
            String actualOutput = lc.execute(logic, ui, sm);
            assertEquals(expectedOutput.trim(), actualOutput.trim());
        } catch (CakeException e) {
            e.printStackTrace();
        }
    }

}
