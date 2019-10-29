import Model_Classes.*;
import Operations.TaskList;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProgressBarTest {
    private float total = 5;
    private float done = 5;

    private ProgressBar pg = new ProgressBar(total, done);

    @Test
    void showBar() {
        assertEquals(pg.showBar(), "[= = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =] 100.0%");
    }
}
