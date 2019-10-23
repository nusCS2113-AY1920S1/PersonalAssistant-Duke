package duke.storage;

import duke.task.TaskList;
import duke.task.Task;
import duke.task.Todo;
import duke.task.Deadline;
import duke.task.Event;
import duke.ui.Ui;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

import java.io.IOException;

//@@author talesrune
class StorageTest {

    @Test
    void storageTest() throws Exception {
        Ui ui = new Ui();
        TaskList items = new TaskList();

        Task task = new Todo("store items");
        Task task2 = new Deadline("assignment", "08/04/2019 1000");
        Task task3 = new Event("party", "02/12/2019 1430");
        items.add(task);
        items.add(task2);
        items.add(task3);

        TaskList items2 = null;
        Storage storage;
        storage = new Storage("data/dukeTest.txt");
        try {
            items2 = new TaskList(storage.read());
        } catch (IOException e) {
            ui.showLoadingError();
            fail();
        }

        for (int i = 0; i < items2.size(); i++) {
            assertEquals(items.get(i).getDescription(),items2.get(i).getDescription());
            assertEquals(items.get(i).getStatusIcon(),items2.get(i).getStatusIcon());
        }
    }
}