import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ByeCommandTest {

    @Test
    void checkBye() throws DukeException {
        Command c = new ByeCommand();
        assertFalse(c.isExit);
        c.execute(new TaskList(),new Ui(),new FileHandling("storeData.txt"));
        assertTrue(c.isExit);
    }
}
