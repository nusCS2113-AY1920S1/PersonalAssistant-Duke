package storage;

import duchess.exceptions.DuchessException;
import duchess.model.task.Event;
import duchess.model.task.Task;
import duchess.parser.Util;
import duchess.storage.Store;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class DetectAnomaliesTest {

    List<String> getList(String input) {
        return List.of(input.split(" "));
    }

    @Test
    void clash_returnsTrue() throws DuchessException {
        String taskInput = "meeting /at 12/12/2020 1800 /to 12/12/2020 1900";
        Store store = new Store();
        Task task = new Event("meeting", Util.parseDateTime("12/12/2020 1900"),
                Util.parseDateTime("12/12/2020 1800"));
        store.getTaskList().add(task);
        for (String str : List.of(
                "12/12/2020 1830 /to 12/12/2020 1930",
                "12/12/2020 1730 /to 12/12/2020 1830",
                "12/12/2020 1730 /to 12/12/2020 1930",
                "12/12/2020 1800 /to 12/12/2020 1830",
                "12/12/2020 1900 /to 12/12/2020 1930"
        )) {
            assertTrue(store.isClashing(new Event("party", Util.parseDateTime(str.substring(20)),
                    Util.parseDateTime(str.substring(0,15)))));
        }
    }

    @Test
    void no_clash_returnsFalse() throws DuchessException {
        Store store = new Store();
        Task task = new Event("meeting", Util.parseDateTime("12/12/2020 1900"),
                Util.parseDateTime("12/12/2020 1800"));
        store.getTaskList().add(task);
        for (String str : List.of(
                "12/12/2020 1930 /to 12/12/2020 2000",
                "12/12/2020 1630 /to 12/12/2020 1730"
        )) {
            assertFalse(store.isClashing(new Event("party", Util.parseDateTime(str.substring(20)),
                    Util.parseDateTime(str.substring(0,15)))));
        }
    }

}
