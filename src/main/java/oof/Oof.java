package oof;

import oof.command.Command;
import oof.exception.ParserException;
import oof.exception.StorageFileCorruptedException;
import oof.exception.command.CommandException;
import oof.model.module.SemesterList;
import oof.model.task.TaskList;
import oof.storage.StorageManager;

/**
 * Represents a Personal Assistant bot. An Oof object corresponds to three other classes,
 * namely called Storage, Ui and TaskList.
 */
public class Oof {

    private StorageManager storageManager;
    private SemesterList semesterList;
    private Ui ui;
    private Reminder reminder;
    private TaskList taskList;

    /**
     * Constructor for Oof for instantiation of other classes Ui, Storage and TaskList.
     */
    public Oof() {
        ui = new Ui();
        storageManager = new StorageManager();
        reminder = new Reminder();
        try {
            semesterList = new SemesterList(storageManager.readSemesterList());
        } catch (NullPointerException | StorageFileCorruptedException e) {
            semesterList = new SemesterList();
        }
        try {
            taskList = new TaskList(storageManager.readTaskList(semesterList));
        } catch (NullPointerException | StorageFileCorruptedException e) {
            taskList = new TaskList();
        }
    }

    /**
     * Executes command entered by user.
     *
     * @param line Command to be tested.
     * @throws CommandException if command fails to execute.
     * @throws ParserException if command cannot be parsed.
     */
    public boolean executeCommand(String line) throws CommandException, ParserException {
        Command command = CommandParser.parse(line);
        command.execute(semesterList, taskList, ui, storageManager);
        return command.isExit();
    }

    /**
     * Runs the Personal Assistant.
     */
    private void run() {
        ui.hello();
        reminder.checkDeadline(taskList, ui, storageManager);
        boolean isExit = false;
        while (!isExit) {
            try {
                ui.printCommandPrompt();
                String line = ui.scanLine();
                if (line.trim().isEmpty()) {
                    continue;
                }
                isExit = executeCommand(line);
            } catch (CommandException | ParserException exception) {
                ui.printCommandException(exception);
            }
        }
    }

    public TaskList getTaskList() {
        return taskList;
    }

    public StorageManager getStorageManager() {
        return storageManager;
    }

    /**
     * This is the main method which makes use of run method.
     *
     * @param args Unused.
     */
    public static void main(String[] args) {
        new Oof().run();
    }
}