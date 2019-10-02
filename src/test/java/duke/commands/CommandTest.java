package duke.commands;

import duke.Storage;
import duke.TaskList;
import duke.Ui;
import duke.enums.CommandType;
import duke.exceptions.BadInputException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CommandTest {
    TaskList newList = new TaskList();
    Ui ui = new Ui();
    Storage storage = new Storage("");

    @Test
    void getTypeDefault() {
        assertEquals(CommandType.BAD, new Command().getType());
    }

    @Test
    void getType() {
        assertEquals(CommandType.TODO, new Command(CommandType.TODO).getType());
        assertEquals(CommandType.DEADLINE, new Command(CommandType.DEADLINE).getType());
        assertEquals(CommandType.EVENT, new Command(CommandType.EVENT).getType());
        assertEquals(CommandType.REMINDER, new Command(CommandType.REMINDER).getType());
        assertEquals(CommandType.DONE, new Command(CommandType.DONE).getType());
        assertEquals(CommandType.SNOOZE, new Command(CommandType.SNOOZE).getType());
        assertEquals(CommandType.LIST, new Command(CommandType.LIST).getType());
        assertEquals(CommandType.VIEW, new Command(CommandType.VIEW).getType());
        assertEquals(CommandType.BYE, new Command(CommandType.BYE).getType());
    }

    @Test
    void execute_printList_empty() throws BadInputException {
        OutputStream os = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(os);
        System.setOut(ps);

        new Command(CommandType.LIST).execute(newList, ui, storage);
        assertEquals("The list is currently empty.\n", os.toString());
    }

    @Test
    void execute_printList_success() throws BadInputException {
        OutputStream os = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(os);
        System.setOut(ps);

        new AddCommand(CommandType.TODO, "Test TODO", "").execute(newList, ui, storage);
        new Command(CommandType.LIST).execute(newList, ui, storage);
        assertEquals("1. [T] [X] Test TODO\n", os.toString());
    }
}
