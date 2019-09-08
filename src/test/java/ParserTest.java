import command.Command;
import org.junit.jupiter.api.Test;
import process.DukeException;
import process.Parser;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ParserTest {
    @Test
    public void dummyTest(){
        assertEquals(2, 2);
    }
    @Test
    public void Test() {
        try {
            Command a = Parser.parse("todo x");
            Command b = Parser.parse("deadline x /by 12/12/12 1234 ");
            Command c = Parser.parse("event x /at 12/12/12 1234");
            Command d = Parser.parse("find x");
            Command e = Parser.parse("delete 1");
            Command f = Parser.parse("list");
            Command g = Parser.parse("bye");
            Command h = Parser.parse("done 1");
        } catch (DukeException e) {
            assert false;
        }
        try {
            Command j = Parser.parse("x");
            assert false;
        } catch (DukeException e) {
            assert true;
        }
    }
}