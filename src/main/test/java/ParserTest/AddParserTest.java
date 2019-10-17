package java.ParserTest;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import cube.logic.parser.AddCommandParser;

public class AddParserTest {


    @Test
    public void findFullStringTest () {
        String test1 = "add -n super banana";
        String test2 = "add -n super banana -t this one -p 12.30";
        AddCommandParser test = new AddCommandParser();

        assertEquals(test.findFullString(test1.split(" "),1),"super banana");
        assertEquals(test.findFullString(test2.split(" "),4),"this one");
    }
}
