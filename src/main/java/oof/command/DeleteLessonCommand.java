package oof.command;

import oof.SelectedInstance;
import oof.Storage;
import oof.Ui;
import oof.exception.OofException;
import oof.model.module.Lesson;
import oof.model.module.Module;
import oof.model.module.SemesterList;
import oof.model.task.TaskList;

/**
 * Represents a Command to delete a specific Lesson.
 */
public class DeleteLessonCommand extends Command {

    private int index;
    private static final int EMPTY = 0;

    /**
     * Constructor for DeleteLessonCommand.
     *
     * @param index Represents the index of the Lesson to be deleted.
     */
    public DeleteLessonCommand(int index) {
        super();
        this.index = index;
    }

    @Override
    public void execute(SemesterList semesterList, TaskList tasks, Ui ui, Storage storage) throws OofException {
        SelectedInstance selectedInstance = SelectedInstance.getInstance();
        Module module = selectedInstance.getModule();
        if (module.isLessonIndexValid(index)) {
            throw new OofException("OOPS!!! Invalid number!");
        }
        Lesson lesson = module.getLesson(this.index);
        module.removeLesson(this.index);
        ui.printLessonRemovalMessage(module.getModuleCode(), lesson);
        storage.writeSemesters(semesterList);
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
