package duke.command;

import duke.core.CommandManager;
import duke.core.DukeException;
import duke.core.Ui;
import duke.patient.PatientManager;
import duke.statistic.CommandCounter;
import duke.storage.CounterStorage;
import duke.storage.PatientStorage;
import duke.storage.PatientTaskStorage;
import duke.storage.TaskStorage;
import duke.relation.PatientTaskList;
import duke.task.Task;
import duke.task.TaskManager;

public class AddStandardTaskCommand extends Command {
    private Task newStandardTask;

    /**
     * .
     *
     * @param taskDescription .
     */
    public AddStandardTaskCommand(String taskDescription) {
        super();
        this.newStandardTask = new Task(taskDescription);

    }

    /**
     * .
     *
     * @param patientTask        .
     * @param taskList           .
     * @param patientList        .
     * @param ui                 .
     * @param patientTaskStorage .
     * @param taskStorage        .
     * @param patientStorage     .
     * @throws DukeException .
     */
    @Override
    public void execute(PatientTaskList patientTask, TaskManager taskList, PatientManager patientList,
                        Ui ui, PatientTaskStorage patientTaskStorage, TaskStorage taskStorage,
                        PatientStorage patientStorage, CounterStorage counterStorage,
                        CommandCounter commandCounter) throws DukeException {
        this.hasBeenAddedBefore = true;
        String commandName = this.getClass().getSimpleName();
        commandCounter.runCommandCounter(this.hasBeenAddedBefore, commandCounter.getCommandTable(), commandName);
        taskList.addTask(newStandardTask);
        taskStorage.save(taskList.getTaskList());
        counterStorage.save(commandCounter.getCommandTable());
        ui.taskAdded(newStandardTask);
    }

    @Override
    public boolean isExit() {
        return false;
    }

}
