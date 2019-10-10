import duchess.exceptions.DuchessException;
import duchess.logic.commands.ViewScheduleCommand;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class ViewScheduleCommandTest {

    @Test
    void viewScheduleCommand_inValidDate_duchessExceptionThrown() {
        List.of(
                List.of("2/3/2019", "day"),
                List.of("32/01/2019", "week"),
                List.of("12/02", "week")
        ).forEach(str -> {
            assertThrows(DuchessException.class, () -> {
                new ViewScheduleCommand(str.get(0), str.get(1));
            });
        });
    }
}
