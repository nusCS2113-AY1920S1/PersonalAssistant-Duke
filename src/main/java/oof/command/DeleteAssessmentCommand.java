package oof.command;

import oof.Storage;
import oof.Ui;
import oof.exception.OofException;
import oof.model.module.Assessment;
import oof.model.module.Module;
import oof.model.module.Semester;
import oof.model.module.SemesterList;
import oof.model.task.TaskList;

/**
 * Represents a Command to delete a specific Assessment.
 */
public class DeleteAssessmentCommand extends Command {

    private int index;
    private static final int EMPTY = 0;
    private Module module;
    private Semester semester;

    /**
     * Constructor for DeleteAssessmentCommand.
     *
     * @param index Represents the index of the Assessment to be deleted.
     */
    public DeleteAssessmentCommand(int index) {
        super();
        this.index = index;
    }

    @Override
    public void execute(SemesterList semesterList, TaskList tasks, Ui ui, Storage storage) throws OofException {
        if (Module.getAssessments().size() == EMPTY) {
            throw new OofException("OOPS!!! Invalid number!");
        }
        Assessment assessment = Module.getAssessment(this.index);
        Module.removeAssessment(this.index);
        ui.printAssessmentRemovalMessage(assessment);
        storage.writeSemesterList(semesterList, semester, module);
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
