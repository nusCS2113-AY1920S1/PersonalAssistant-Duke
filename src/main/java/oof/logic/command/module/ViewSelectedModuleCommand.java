package oof.logic.command.module;

import oof.logic.command.Command;
import oof.model.semester.SelectedInstance;
import oof.ui.Ui;
import oof.logic.command.module.exceptions.ModuleNotSelectedException;
import oof.model.semester.Module;
import oof.model.semester.SemesterList;
import oof.model.task.TaskList;
import oof.storage.StorageManager;

//@@author KahLokKee

/**
 * Represents a command to view selected Module.
 */
public class ViewSelectedModuleCommand extends Command {

    public ViewSelectedModuleCommand() {
    }

    /**
     * Retrieves and prints seleceted module.
     *
     * @param semesterList   Instance of SemesterList that stores Semester objects.
     * @param taskList       Instance of TaskList that stores Task objects.
     * @param ui             Instance of Ui that is responsible for visual feedback.
     * @param storageManager Instance of Storage that enables the reading and writing of Task
     *                       objects to hard disk.
     * @throws ModuleNotSelectedException if module instance is not selected.
     */
    @Override
    public void execute(SemesterList semesterList, TaskList taskList, Ui ui, StorageManager storageManager)
            throws ModuleNotSelectedException {
        SelectedInstance selectedInstance = SelectedInstance.getInstance();
        Module module = selectedInstance.getModule();
        if (module == null) {
            throw new ModuleNotSelectedException("OOPS!! No module selected.");
        }
        ui.printCurrentlySelectedModule(module);
    }
}
