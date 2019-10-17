import gazeeebo.Tasks.DoAfter;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DoAfterCommandTest {

    DoAfter testdoAfter = new DoAfter("read book","read book","return book");

    @Test
    void testtoString() {
        assertEquals("DA|ND|return book|read book", testdoAfter.toString());
    }

    @Test
    void testlistformat() {
        assertEquals("[DA][ND] return book(/after:read book)", testdoAfter.listFormat());
    }

}
