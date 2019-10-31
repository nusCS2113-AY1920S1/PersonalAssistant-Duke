package logic;

import common.DukeException;
import common.LoggerController;
import logic.parser.ArgumentTokenizer;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ArgumentTokenizerTest {
    @Test
    public void normalTest() {
        String tc1 = "add task task 1 /at 13/11/2019 1900";
        try {
            HashMap<String, String> tr1 = ArgumentTokenizer.tokenize(tc1);
            assertEquals("add task task 1", tr1.get(""));
            assertEquals("13/11/2019 1900", tr1.get("/at"));
            assertEquals(null, tr1.get("/to"));
        } catch (DukeException e) {
            LoggerController.logDebug(this.getClass(), "tag error caught");
        }
    }
}
