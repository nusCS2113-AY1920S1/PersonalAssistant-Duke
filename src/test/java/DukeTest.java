import duke.Duke;
import duke.command.AddCommand;
import duke.command.Command;
import duke.command.ExitCommand;
import duke.exception.DukeException;
import duke.parser.Parser;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DukeTest {
    @Test
    public void dummyTest(){
        assertEquals(2, 2);
    }

    @Test
    public void testExitCommand() throws DukeException {
           Duke duke= new Duke("data/tasks.txt");
            Command c= Parser.parse("bye");
         assertTrue(c instanceof ExitCommand);
    }
    @Test
    public void testAddCommand() throws DukeException {
        Duke duke= new Duke("data/tasks.txt");
        Command c=Parser.parse("todo project");
        assertTrue(c instanceof AddCommand);
    }
}
