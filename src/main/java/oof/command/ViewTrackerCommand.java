package oof.command;

import oof.Storage;
import oof.Ui;
import oof.exception.OofException;
import oof.model.module.SemesterList;
import oof.model.task.TaskList;

public class ViewTrackerCommand extends Command {

    @Override
    public void execute(SemesterList semesterList, TaskList tasks, Ui ui, Storage storage) throws OofException {

    }

    @Override
    public boolean isExit() {
        return false;
    }
}
