package oof.command;

import oof.SelectedInstance;
import oof.Ui;
import oof.exception.OofException;
import oof.model.module.Module;
import oof.model.module.Semester;
import oof.model.module.SemesterList;
import oof.model.task.TaskList;
import oof.storage.StorageManager;

/**
 * Command to select a module.
 */
public class SelectModuleCommand extends Command {

    private int index;

    /**
     * Constructor for SelectModuleCommand.
     *
     * @param index Module index in the semester.
     */
    public SelectModuleCommand(int index) {
        super();
        this.index = index;
    }

    /**
     * Selects a module in a semester.
     *
     * @param semesterList   Instance of SemesterList that stores Semester objects.
     * @param taskList       Instance of TaskList that stores Task objects.
     * @param ui             Instance of Ui that is responsible for visual feedback.
     * @param storageManager Instance of Storage that enables the reading and writing of Task
     *                       objects to hard disk.
     * @throws OofException if user input invalid commands.
     */
    @Override
    public void execute(SemesterList semesterList, TaskList taskList, Ui ui, StorageManager storageManager)
            throws OofException {
        try {
            SelectedInstance selectedInstance = SelectedInstance.getInstance();
            Semester semester = selectedInstance.getSemester();
            Module module = semester.getModule(index);
            selectedInstance.selectModule(module);
            ui.printSelectModuleMessage(module);
        } catch (IndexOutOfBoundsException e) {
            throw new OofException("OOPS!! The index is out of bounds.");
        } catch (NumberFormatException e) {
            throw new OofException("OOPS!! The index is invalid.");
        }
    }
}
