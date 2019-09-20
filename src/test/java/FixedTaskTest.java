import org.junit.jupiter.api.Test;
import process.DukeException;
import task.FixedTask;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FixedTaskTest {
    @Test
    public void validArgTest(){
        try {
            FixedTask ft = new FixedTask("Build tree house.", true, 330);
        } catch (DukeException e) {
            assert false;
        }
        assert true;
    }

    @Test
    public void invalidArgTest(){
        FixedTask ft;
        boolean hasError = false;
        try {
            ft = new FixedTask("Build tree house.", true, 0);
        } catch (DukeException e) {
            hasError = true;
        }
        assertTrue(hasError);
        hasError = false;
        try {
            ft = new FixedTask("Build tree house.", true, -60);
        } catch (DukeException e) {
            hasError = true;
        }
        assertTrue(hasError);
    }

    @Test
    public void toStringTest(){
        try {
            FixedTask ft = new FixedTask("Build tree house.", false, 301);
            assertEquals("[F][X] Build tree house. (Requires: 5 Hours 1 Minute)", ft.toString());
        } catch (DukeException e) {
            assert false;
        }
        assert true;
    }
}
