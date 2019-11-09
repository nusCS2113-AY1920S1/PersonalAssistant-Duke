package duchess.commands;

import duchess.exceptions.DuchessException;
import duchess.logic.commands.ReminderCommand;
import duchess.parser.Parser;
import duchess.storage.Storage;
import duchess.storage.Store;
import duchess.ui.Ui;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ReminderCommandTest {
    private final Storage storage = new Storage("text_file");
    private final Store store = new Store();
    private final Ui ui = new Ui();

    @Test
    public void execute_withoutDeadlines_success() throws DuchessException {
        assertTrue(store.getTaskList().size() == 0);
        ReminderCommand reminderCommand = new ReminderCommand();
        reminderCommand.execute(store, ui, storage);

        assertTrue(store.getTaskList().size() == 0);
    }

    @Test
    public void execute_withDeadlines_success() throws DuchessException {
        String firstDeadline = "add deadline /name homework assignment /by 23/12/2019 1800 /module nil /weightage nil";
        String secondDeadline = "add deadline /name school project tasks /by 01/11/2020 0900 /module nil /weightage 20";

        // Adding the firstDeadline
        Parser parser = new Parser();
        parser.parse(firstDeadline).execute(store, ui, storage);

        // Adding the secondDeadline
        parser.parse(secondDeadline).execute(store, ui, storage);

        parser.parse("reminder").execute(store, ui, storage);

        assertTrue(store.getTaskList().size() == 2);
    }
}