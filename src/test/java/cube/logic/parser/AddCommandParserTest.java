package cube.logic.parser;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AddCommandParserTest {
    @Test
    public void findFullStringTest () {
        String test1 = "add super banana";
        String test2 = "add super banana -t this one -p 12.30";
        ParserUtil test = new ParserUtil();

        assertEquals(test.findFullString(test1.split(" "),1),"super banana");
        assertEquals(test.findFullString(test2.split(" "),4),"this one");
    }
}