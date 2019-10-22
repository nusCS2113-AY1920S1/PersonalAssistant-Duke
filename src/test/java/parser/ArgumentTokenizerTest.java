package parser;

import org.junit.jupiter.api.Test;
import parsers.ArgumentTokenizer;
import parsers.DukeParser;
import parsers.SpellingErrorCorrector;
import utils.DukeException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ArgumentTokenizerTest {
    @Test
    public void argumentMultimapTest() throws DukeException {
        String input = "hello, this part is the first part /key1 value1 /key2 value 2/key3 value value value 3";
        HashMap<String, String> argumentMultimap = ArgumentTokenizer.getArgumentMultimap(input);
        assertEquals("hello, this part is the first part", argumentMultimap.get(""));
        assertEquals("value1", argumentMultimap.get("key1"));
        assertEquals("value 2", argumentMultimap.get("key2"));
        assertEquals("value value value 3", argumentMultimap.get("key3"));

        input = "0 1 2 3 4 555 66/key1 value1/key2 value 2/key3 value value value 3   ";
        argumentMultimap = ArgumentTokenizer.getArgumentMultimap(input);
        assertEquals("0 1 2 3 4 555 66", argumentMultimap.get(""));
        assertEquals("value1", argumentMultimap.get("key1"));
        assertEquals("value 2", argumentMultimap.get("key2"));
        assertEquals("value value value 3", argumentMultimap.get("key3"));

        // TODO needs further improvement
//        input = "233333333333/ 1 suibianshenme /key";
//        argumentMultimap = ArgumentTokenizer.getArgumentMultimap(input);
//        assertEquals(false, argumentMultimap.containsKey("1"));
//        assertEquals("", argumentMultimap.get("key"));
    }

}
