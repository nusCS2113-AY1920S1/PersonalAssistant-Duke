package duke.command;

import duke.core.CommandManager;
import duke.core.DukeException;
import duke.core.Ui;
import duke.storage.CmdFreqStorage;
import duke.task.Task;
import duke.patient.PatientManager;
import duke.storage.PatientStorage;
import duke.storage.PatientTaskStorage;
import duke.storage.TaskStorage;
import duke.relation.PatientTaskList;
import duke.task.TaskManager;

public class UpdateTaskCommand extends Command {
    private String command;
    private boolean hasBeenAddedBefore = false;

    /**
     * .
     *
     * @param command .
     */
    public UpdateTaskCommand(String command) {
        this.command = command;
    }

    /**
     * .
     *
     * @param patientTask        .
     * @param taskManager        .
     * @param patientManager     .
     * @param ui                 .
     * @param patientTaskStorage .
     * @param taskStorage        .
     * @param patientStorage     .
     * @throws DukeException .
     */
    @Override
    public void execute(PatientTaskList patientTask, TaskManager taskManager, PatientManager patientManager,
                        Ui ui, PatientTaskStorage patientTaskStorage,
                        TaskStorage taskStorage, PatientStorage patientStorage, CmdFreqStorage cmdFreqStorage,
                        CommandManager commandManager) throws DukeException {
        this.hasBeenAddedBefore = true;
        String commandName = this.getClass().getSimpleName();
        if (!hasBeenAddedBefore) {
            commandManager.getCmdFreqTable().put(commandName, 1);
        }
        int count = commandManager.getCmdFreqTable().containsKey(commandName)
                    ? commandManager.getCmdFreqTable().get(commandName) : 0;
        commandManager.getCmdFreqTable().put(commandName, count + 1);

        String[] tempCommand = command.split(" ", 3);
        char firstChar = tempCommand[0].charAt(0);
        if (firstChar == '#') {
            int id;
            try {
                id = Integer.parseInt(tempCommand[0].substring(1, tempCommand[0].length()));
                Task taskToBeUpdated = taskManager.getTask(id);
                if (tempCommand[1].toLowerCase().equals("description")) {
                    taskToBeUpdated.setDescription(tempCommand[2]);
                } else {
                    throw new DukeException("You can only update 'Description' of the task");
                }

                taskStorage.save(taskManager.getTaskList());
                cmdFreqStorage.save(commandManager.getCmdFreqTable());

                ui.showUpdatedSuccessfully();
                ui.showTaskInfo(taskToBeUpdated);
            } catch (Exception e) {
                throw new DukeException(
                        "Please follow the format 'update patient #<id> <Name/NRIC/Room> <new information>'.");
            }

        }
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
