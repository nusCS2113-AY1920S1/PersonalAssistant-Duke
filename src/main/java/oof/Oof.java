package oof;

import oof.model.task.Assessment;
import oof.model.module.Lesson;
import oof.model.module.Module;
import oof.model.module.Semester;
import oof.model.module.SemesterList;
import oof.command.Command;
import oof.exception.OofException;
import oof.model.task.TaskList;

import java.io.IOException;

/**
 * Represents a Personal Assistant bot. An Oof object corresponds to three other classes,
 * namely called Storage, Ui and TaskList.
 */
public class Oof {

    private Storage storage;
    private SemesterList semesterList;
    private Ui ui;
    private Reminder reminder;
    private TaskList taskList;

    /**
     * Constructor for Oof for instantiation of other classes Ui, Storage and TaskList.
     */
    public Oof() {
        ui = new Ui();
        storage = new Storage();
        reminder = new Reminder();
        try {
            semesterList = new SemesterList(storage.readSemesterList());
        } catch (IOException | OofException e) {
            semesterList = new SemesterList();
        }
        try {
            taskList = new TaskList(storage.readTaskList(semesterList));
        } catch (IOException | OofException e) {
            taskList = new TaskList();
        }
    }

    /**
     * Executes command entered by user.
     *
     * @param line Command to be tested.
     * @throws OofException Exceptions thrown by Command classes.
     */
    public boolean executeCommand(String line) throws OofException {
        Command command = CommandParser.parse(line);
        command.execute(semesterList, taskList, ui, storage);
        return command.isExit();
    }

    /**
     * Runs the Personal Assistant.
     */
    private void run() {
        ui.hello();
        reminder.checkDeadline(taskList, ui, storage);
        boolean isExit = false;
        while (!isExit) {
            try {
                ui.printCommandPrompt();
                String line = ui.scanLine();
                isExit = executeCommand(line);
            } catch (OofException exception) {
                ui.printOofException(exception);
            }
        }
    }

    public TaskList getTaskList() {
        return taskList;
    }

    public Storage getStorage() {
        return storage;
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