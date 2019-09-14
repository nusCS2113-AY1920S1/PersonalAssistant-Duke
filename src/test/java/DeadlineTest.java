import duke.dukeexception.DukeException;
import duke.task.Deadline;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DeadlineTest {
    List<String> getList(String input) {
        return List.of(input.split(" "));
    }

    @Test
    void constructor_invalidString_exceptionThrown() {
        List.of(
                "invalid string",
                "invalid string /at 20/12/2019 1243",
                "invalid string /by 20/12/2019 12:43",
                "invalid string /by 20/12/2019 1293"
        ).forEach(str -> {
            assertThrows(DukeException.class, () -> {
                List<String> invalidList = getList(str);
                new Deadline(invalidList);
            });
        });
    }

    @Test
    void toString_formatted_correctly() throws DukeException {
        List<String> list = getList("do something /by 20/12/2019 1243");
        assertEquals("[D][✘] do something (by: 20/12/2019 1243)",new Deadline(list).toString());
    }
}
