package duke.command;

import duke.modules.Task;
import duke.util.Reminder;
import duke.util.Storage;
import duke.util.TaskList;
import duke.util.Ui;
import duke.exceptions.ModEmptyListException;

import java.util.List;
import java.util.Objects;

public class ReportCommand extends Command {

    public ReportCommand() {

    }

    /**
     * Takes in TaskList, Ui and Storage objects which then displays
     * all the actively tracked Tasks in TaskList.
     * @param tasks TaskList object containing current active taskList.
     * @param ui Ui object containing all output methods to user.
     * @param store Storage object which updates stored data.
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage store, Reminder reminder) throws ModEmptyListException {
        boolean isEmpty = tasks.getTasks().isEmpty();
        if (isEmpty) {
            throw new ModEmptyListException();
        } else {
            ui.reportMsg();
            ui.printReportList(tasks.getTasks());

            List<Task> ETemp = tasks.find("[E]");
            ui.printReportTask(ETemp, "event");
            List<Task> DTemp = tasks.find("[D]");
            ui.printReportTask(DTemp, "deadline");
            List<Task> RTemp = tasks.find("[R]");
            ui.printReportTask(RTemp, "recurring");
            List<Task> TTemp = tasks.find("[T]");
            ui.printReportTask(TTemp, "todo");
            List<Task> WTemp = tasks.find("[W]");
            ui.printReportTask(WTemp, "do within");
            List<Task> FTemp = tasks.find("[F]");
            ui.printReportTask(FTemp, "fixed duration");
        }
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
