package oof.logic.command.semester;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayInputStream;

import org.junit.jupiter.api.Test;

import oof.commons.exceptions.command.CommandException;
import oof.model.task.TaskList;
import oof.model.university.SemesterList;
import oof.storage.StorageManager;
import oof.ui.Ui;

public class ViewAllSemesterCommandTest {

    @Test
    public void execute_EmptySemesterList_ExceptionThrown() {
        ByteArrayInputStream in = new ByteArrayInputStream("1".getBytes());
        System.setIn(in);
        SemesterList semesterList = new SemesterList();
        TaskList taskList = new TaskList();
        Ui ui = new Ui();
        StorageManager storageManager = new StorageManager();
        ViewAllSemesterCommand viewAllSemesterCommand = new ViewAllSemesterCommand();
        try {
            viewAllSemesterCommand.execute(semesterList, taskList, ui, storageManager);
        } catch (CommandException e) {
            assertEquals("Semester list is empty!", e.getMessage());
        }
    }
}
