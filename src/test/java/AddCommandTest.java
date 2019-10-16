import duke.command.AddCommand;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AddCommandTest {

    @Test
    public void testCovertToLocalDate() {
        final AddCommand a = new AddCommand("homework", "19102019 1000");
        assertEquals(LocalDate.of(2019, 10, 19), a.convertToLocalDate("19102019 1000"));
        final AddCommand b = new AddCommand("homework", "19012019 1000");
        assertEquals(LocalDate.of(2019, 01, 19), a.convertToLocalDate("19012019 1000"));
    }
}
