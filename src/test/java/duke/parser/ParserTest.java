package duke.parser;

import duke.command.DoneCommand;
import duke.command.FindCommand;
import duke.command.AddCommand;
import duke.command.DeleteCommand;
import duke.command.Command;
import duke.command.ListCommand;
import duke.task.Task;
import duke.task.TaskList;
import duke.task.Todo;
import duke.ui.Ui;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class ParserTest {
    Ui ui = new Ui();
    TaskList items = new TaskList();

    @Test
    void parserTest() throws Exception {
        Task task = new Todo("Hi");
        Task task2 = new Todo("there");
        items.add(task);
        items.add(task2);

        Command cmd = Parser.parse("todo Work", items);
        assertTrue(cmd instanceof AddCommand);
        cmd = Parser.parse("deadline basketball /by 19/04/2019 1900", items);
        assertTrue(cmd instanceof AddCommand);
        cmd = Parser.parse("event watch movies /at 20/07/2018 1240", items);
        assertTrue(cmd instanceof AddCommand);
        cmd = Parser.parse("find word", items);
        assertTrue(cmd instanceof FindCommand);
        cmd = Parser.parse("done 1", items);
        assertTrue(cmd instanceof DoneCommand);
        cmd = Parser.parse("list", items);
        assertTrue(cmd instanceof ListCommand);
        cmd = Parser.parse("delete 1", items);
        assertTrue(cmd instanceof DeleteCommand);
    }

    @Test
    public void parserTest_exceptionThrown() {
        try {
            Parser.parse("invalid",items);
            fail(); // the test should not reach this line
        } catch (Exception e) {
            assertEquals("     (>_<) OoPS!!! I'm sorry, but I don't know what that means :-(", e.getMessage());
        }
    }
}
