package duke.commands;

import duke.Storage;
import duke.Ui;
import duke.task.TaskList;
import duke.task.Todo;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


class ListCommandTest {
    TaskList tasks = new TaskList();
    Ui ui = new Ui();
    Storage storage;
    ListCommand listCommand = new ListCommand();

    @org.junit.Test
    void testExecute() {
        tasks.add(new Todo("test1"));
        tasks.add(new Todo("test2"));
        tasks.add(new Todo("test3"));

        listCommand.execute(tasks, ui, storage);

        String expected = "_________________________________________\n"
                + "Here are the tasks in your list:\n"
                + "1.[T][✘] test1\n"
                + "2.[T][✘] test2\n"
                + "3.[T][✘] test3\n"
                + "_________________________________________\n";

        assertEquals(expected, ui.showLine());
    }
}