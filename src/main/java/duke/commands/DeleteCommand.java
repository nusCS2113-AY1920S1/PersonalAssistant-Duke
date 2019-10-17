package duke.commands;

import duke.tasks.DoAfter;
import duke.tasks.Task;
import duke.DoAfterList;
import duke.DukeException;
import duke.TaskList;
import duke.Storage;
import duke.Ui;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * A class that represents the command to delete an item from the task list.
 */
public class DeleteCommand extends Command<TaskList> {

    private int index;

    /**
     * Constructor for the duke.Commands.Command created to delete a task from the duke.TaskList
     *
     * @param message the input message that resulted in the creation of the duke.Commands.Command
     * @throws DukeException if an exception occurs in the parsing of the message
     */
    public DeleteCommand(String message) throws DukeException {
        this.message = message;
        try {
            index = Integer.parseInt(message.substring(7));
        } catch (Exception e) {
            throw new DukeException("","other");
        }
    }

    /**
     * Modifies the task list in use and returns the messages intended to be displayed.
     *
     * @param taskList the duke.TaskList object that contains the task list
     * @param ui the Ui object that determines the displayed output of duke.Duke
     * @param storage the storage
     * @return the string to be displayed in duke.Duke
     * @throws DukeException if an exception occurs in the parsing of the message or in IO
     */
    public String execute(TaskList taskList, Ui ui, Storage storage) throws DukeException {
        if (taskList.getSize() == 0) {
            throw new DukeException("","empty");
        }
        if (index > taskList.getSize() || index < 1) {
            throw new DukeException("","index");
        } else {
            int counter = 0;
            Set<Integer> indexSet = new HashSet<>();

            ArrayList<Task> finalTasksRemoveList = getRemovedTasks(index, taskList);

            //populating the indexSet with the indexes of all the tasks to be deleted
            for (Task task: finalTasksRemoveList) {
                if (task instanceof DoAfter) {
                    indexSet.add(((DoAfter) task).getCurrentNumber());
                }
                indexSet.add(index);
            }

            DoAfterList.removeAll(indexSet);
            //updating the values of DoAfterList to supposed values after the tasks are all removed
            for (int index: indexSet) {
                for (int i = 0; i < DoAfterList.getSize(); i++) {
                    if (index < DoAfterList.get(i)) {
                        DoAfterList.set(i, DoAfterList.get(i) - 1);
                    }
                }
            }

            taskList.removeAll(finalTasksRemoveList);

            //updating the values of the current position of each DoAfter task with respect to the taskList
            for (Task task : taskList.getTaskList()) {
                if (task instanceof DoAfter) {
                    int currentNumber = ((DoAfter) task).getCurrentNumber();
                    int count = 0;
                    for (int index: indexSet) {
                        if (index <= currentNumber) {
                            count += 1;
                        }
                    }
                    ((DoAfter) task).setCurrentNumber(currentNumber - count);
                }
            }

            //updating the values of the task numbers of previous tasks for DoAfter tasks
            for (Task task: taskList.getTaskList()) {
                if (task instanceof DoAfter) {
                    ((DoAfter) task).setPreviousTaskNumber(DoAfterList.get(counter));
                    counter++;
                }
            }

            ArrayList<Task> oldList = new ArrayList<>(taskList.getTaskList());
            try {
                //storage.updateFile(taskList);
            } catch (Exception e) {
                throw new DukeException("","io");
            }
            ArrayList<Task> newList = new ArrayList<>(taskList.getTaskList());
            return ui.formatDelete(oldList, newList, index);
        }
    }

    /**
     * Returns a boolean value representing whether the program will terminate or not, used in
     * duke.Duke to reassign a boolean variable checked at each iteration of a while loop.
     *
     * @return a boolean value that represents whether the program will terminate after the command
     */
    @Override
    public boolean isExit() {
        return false;
    }

    /**
     * Returns a list of tasks that are linked to the current task being removed.
     * @param removedIndex The integer representing the index of the current task being removed
     * @param taskList The general list of tasks
     * @return
     */
    private ArrayList<Task> getRemovedTasks(int removedIndex, TaskList taskList) {
        ArrayList<Integer> indexList = new ArrayList<>(); //index of tasks that is going to be removed
        ArrayList<Task> removeList = new ArrayList<>(); //list of tasks that is going to be removed
        ArrayList<DoAfter> tempList = new ArrayList<>(); //temporary lists of DoAfter tasks

        //populating the indexList with indexes of tasks that are going to be deleted
        for (int i = 0; i < DoAfterList.getSize(); i++) {
            if (DoAfterList.get(i) == removedIndex) {
                indexList.add(i + 1);
            }
        }
        //exit if there are no extra tasks to be deleted
        if (indexList.isEmpty()) {
            Task removedTask = taskList.getTaskList().get(removedIndex - 1);
            removeList.add(removedTask);
            return removeList;
        }
        //populating the tempList with all the DoAfter tasks
        for (int i = 0; i < taskList.getTaskList().size(); i++) {
            Task task = taskList.getTaskList().get(i);
            if (task instanceof DoAfter) {
                tempList.add((DoAfter) task);
            }
        }
        //populating the removeList with all tasks that are supposed to be deleted
        for (int index: indexList) {
            removeList.add(tempList.get(index - 1));
            int removedDoAfterIndex = tempList.get(index - 1).getCurrentNumber();
            removeList.addAll(getRemovedTasks(removedDoAfterIndex, taskList));
        }
        Task removedTask = taskList.getTaskList().get(removedIndex - 1);
        removeList.add(removedTask);
        return removeList;
    }
}
