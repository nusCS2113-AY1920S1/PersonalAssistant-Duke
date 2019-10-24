package duke.task;

import entertainment.pro.model.Period;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PeriodTest {
    @Test
    public void test() {
        assertEquals("watch anime (Period: 11th September 2019 to 20th September 2019)",
            new Period("watch anime", "P", "11th September 2019", "20th September 2019").toMessage());
    }
}
