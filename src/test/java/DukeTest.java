import org.junit.jupiter.api.Test;

import java.text.ParseException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class DukeTest {

    @Test
    public void testUi() {
        Ui ui = new Ui();
        assertEquals(" ____        _        \n"
                + "|  _ \\ _   _| | _____ \n"
                + "| | | | | | | |/ / _ \\\n"
                + "| |_| | |_| |   <  __/\n"
                + "|____/ \\__,_|_|\\_\\___|\n", ui.showWelcome());
    }
//    JUnit testing
//    @Test
//   public void test() {
//        DeadlineCommand deadlineCommand = new DeadlineCommand();
//        try {
//            deadlineCommand.makeDate("Sunday");
//        } catch(ParseException e) {
//            assertEquals("Unparseable date: 'Sunday'", e.getMessage());
//        }
//    }
}