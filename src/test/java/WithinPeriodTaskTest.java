import org.junit.jupiter.api.Test;
import task.WithinPeriodTask;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WithinPeriodTaskTest {

    @Test
    public void withinPeriodTaskTester() {
        WithinPeriodTask withinPeriodTask = new WithinPeriodTask("collect certificate ",
                "02/12/2019 1800", "05/12/2019 1500");
        assertEquals(withinPeriodTask.giveTask(), "[W][✘] collect certificate "
                + "(between: 02/12/2019 1800 and 05/12/2019 1500)");
    }
}
