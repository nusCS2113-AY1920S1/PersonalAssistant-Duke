package duke.command.dukecommands;

import duke.command.Command;
import duke.exception.DukeException;
import duke.storage.Storage;
import duke.task.duketasks.Task;
import duke.list.tasklist.TaskList;
import duke.ui.Ui;

import java.util.ArrayList;

import static duke.common.Messages.COMMAND_CONFIRM;
import static duke.common.Messages.COMMAND_CONFIRMSCHEDULE;
import static duke.common.Messages.ERROR_MESSAGE_UNKNOWN_INDEX;
import static duke.common.Messages.MESSAGE_TENTATIVE;
import static duke.common.Messages.ERROR_MESSAGE_EMPTY_INDEX;
import static duke.common.Messages.MESSAGE_FOLLOWUP_EMPTY_INDEX;
import static duke.common.Messages.MESSAGE_FOLLOWUP_CONFIRM;
import static duke.common.Messages.ERROR_MESSAGE_RANDOM;

public class ConfirmScheduleCommand extends Command {

    public ConfirmScheduleCommand(String userInput) {
        this.userInput = userInput;
    }

    /**
     * Validates that user inputs an integer value for the index.
     * @param input String containing integer input from user for the index
     * @return true if the user inputs an integer and false otherwise
     */
    public static boolean isParsable(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) throws DukeException {
        ArrayList<Task> scheduleTasks = new ArrayList<>();
        ArrayList<Integer> integerArrayList = new ArrayList<>();
        if (userInput.trim().equals(COMMAND_CONFIRMSCHEDULE)) {
            int i = 0;
            for (Task task : taskList.getTaskList()) {
                i++;
                if (task.getTaskType() == Task.TaskType.TENTATIVESCHEDULING) {
                    scheduleTasks.add(task);
                    integerArrayList.add(i);
                }
            }
            int j = 0;
            System.out.println(MESSAGE_TENTATIVE);
            for (Task task : scheduleTasks) {
                System.out.println("      " + integerArrayList.get(j++) + ". " + task.toString());
            }
            System.out.println(MESSAGE_FOLLOWUP_CONFIRM);
        } else if (userInput.trim().equals(COMMAND_CONFIRM)) {
            throw new DukeException(ERROR_MESSAGE_EMPTY_INDEX + MESSAGE_FOLLOWUP_EMPTY_INDEX);
        } else if (userInput.trim().charAt(7) == ' ') {
            String description = userInput.split("\\s",2)[1].trim();
            if (isParsable(description)) {
                //converting string to integer
                int index = Integer.parseInt(description);
                Task.TaskType currTaskType = taskList.getTask(index - 1).getTaskType();
                if (currTaskType == Task.TaskType.TENTATIVESCHEDULING) {
                    taskList.scheduledTask(index - 1);
                    storage.saveFile(taskList);
                } else {
                    throw new DukeException(MESSAGE_FOLLOWUP_CONFIRM);
                }
            } else {
                throw new DukeException(ERROR_MESSAGE_UNKNOWN_INDEX);
            }
        } else {
            throw new DukeException(ERROR_MESSAGE_RANDOM);
        }
    }

    @Override
    public boolean isExit() {
        return false;
    }
}