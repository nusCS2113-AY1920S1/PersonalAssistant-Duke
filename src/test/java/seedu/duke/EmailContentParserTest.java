package seedu.duke;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.duke.email.EmailContentParseHelper.editDistance;

public class EmailContentParserTest {
    @Test
    public void editDistanceTest() {
        assertEquals(1, editDistance("a", "b"));
        assertEquals(1, editDistance("a", ""));
        assertEquals(2, editDistance("a", "bc"));
        assertEquals(1, editDistance("a", "ba"));
        assertEquals(4, editDistance("food", "money"));
        assertEquals(3, editDistance("kitten", "sitting"));
    }
}
