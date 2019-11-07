package oof.logic.command.module;

import oof.logic.command.Command;
import oof.model.semester.SelectedInstance;
import oof.ui.Ui;
import oof.commons.exceptions.command.InvalidArgumentException;
import oof.model.semester.Module;
import oof.model.semester.Semester;
import oof.model.semester.SemesterList;
import oof.model.task.TaskList;
import oof.storage.StorageManager;

//@@author KahLokKee

/**
 * Represents a Command to delete a specific Module.
 */
public class DeleteModuleCommand extends Command {

    private int index;

    /**
     * Constructor for DeleteModuleCommand.
     *
     * @param index Represents the index of the Module to be deleted.
     */
    public DeleteModuleCommand(int index) {
        super();
        this.index = index;
    }

    /**
     * Deletes module from semester.
     *
     * @param semesterList   Instance of SemesterList that stores Semester objects.
     * @param tasks          Instance of TaskList that stores Task objects.
     * @param ui             Instance of Ui that is responsible for visual feedback.
     * @param storageManager Instance of Storage that enables the reading and writing of Task
     *                       objects to hard disk.
     * @throws InvalidArgumentException if user input contains invalid commands.
     */
    @Override
    public void execute(SemesterList semesterList, TaskList tasks, Ui ui, StorageManager storageManager)
            throws InvalidArgumentException {
        SelectedInstance selectedInstance = SelectedInstance.getInstance();
        Semester semester = selectedInstance.getSemester();
        if (!semester.isIndexValid(index)) {
            throw new InvalidArgumentException("OOPS!!! Invalid number!");
        }
        Module module = semester.getModule(index);
        semester.deleteModule(index);
        ui.printModuleRemovalMessage(module);
        storageManager.writeSemesterList(semesterList);
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
