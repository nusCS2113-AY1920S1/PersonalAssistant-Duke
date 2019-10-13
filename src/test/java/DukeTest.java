import duke.Duke;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.text.ParseException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DukeTest {
    @Test
    public void dummyTest() throws FileNotFoundException, ParseException {
        Duke duke = new Duke();
        assertEquals(2,2);
    }
}