package oof.command;

import oof.Storage;
import oof.Ui;
import oof.exception.OofException;
import oof.model.module.Lesson;
import oof.model.module.Module;
import oof.model.module.Semester;
import oof.model.module.SemesterList;
import oof.model.task.TaskList;

/**
 * Represents a Command to delete a specific Lesson.
 */
public class DeleteLessonCommand extends Command {

    private int index;
    private static final int EMPTY = 0;
    private Module module;
    private Semester semester;

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
        if (Module.getLessons().size() == EMPTY) {
            throw new OofException("OOPS!!! Invalid number!");
        }
        Lesson lesson = Module.getLesson(this.index);
        Module.removeLesson(this.index);
        ui.printLessonRemovalMessage(Module.getModuleCode(), lesson);
        storage.writeSemesterList(semesterList, semester, module);
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
