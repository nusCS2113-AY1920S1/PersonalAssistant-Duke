package Operations;

import CustomExceptions.RoomShareException;
import Enums.ExceptionType;
import Model_Classes.Assignment;
import Model_Classes.Task;

import java.util.ArrayList;

public class OverdueList {
    private static ArrayList<Task> overdue;

    /**
     * A constructor for the overdueList class.
     * Takes in an ArrayList of Task objects as a parameter.
     * @param Overdue ArrayList of Task object to be operated on.
     */
    public OverdueList(ArrayList<Task> Overdue) {
        OverdueList.overdue = Overdue;
    }

    /**
     * Adds a Task to the Overdued task list.
     * @param task Task that was overdue and added into the
     *             Overdued task list.
     */
    public void add(Task task) {
        overdue.add(task);
    }

    /**
     * Reschedules an overdue task that was in the overdued list to be placed back into
     * the original task list for the user.
     *
     * @param idx index of the task in the Overdued task list that is being rescheduled.
     * @throws RoomShareException if the index entered is not valid
     */
    public void reschedule(int[] idx, TaskList taskList) throws RoomShareException {
        int[] index = idx.clone();
        if (index.length == 1) {
            boolean isNegativeIndex = index[0] < 0;
            boolean isExceededIndex = index[0] >= overdue.size();
            if (isNegativeIndex || isExceededIndex) {
                System.out.println("This are your tasks in your Overdue list");
                list();
                throw new RoomShareException(ExceptionType.outOfBounds);
            } else {
                taskList.add(overdue.get(index[0]));
                overdue.get(index[0]).setOverdue(false);
            }
        } else {
            boolean isNegativeFirstIndex = index[0] < 0;
            boolean isExceededFirstIndex = index[0] >= overdue.size();
            boolean isNegativeSecondIndex = index[1] < 0;
            boolean isExceededSecondIndex = index[1] >= overdue.size();
            if (isNegativeFirstIndex|| isExceededFirstIndex
                    || isNegativeSecondIndex || isExceededSecondIndex) {
                throw new RoomShareException(ExceptionType.outOfBounds);
            }
            for (int i = index[0]; i <= index[1]; i++){
                taskList.add(overdue.get(i));
                overdue.get(i).setOverdue(false);
            }
        }
        for (int i = 0; i < index.length; i++){
            overdue.removeIf(n -> !n.getOverdue());
        }
    }

    /**
     * lists the tasks that are current in the overdued task list.
     * @throws RoomShareException when the list is empty
     */
    public void list() throws RoomShareException {
        if (overdue.size() == 0) {
            throw new RoomShareException(ExceptionType.emptyList);
        } else {
            int listCount = 1;
            for (Task output : overdue) {
                System.out.println("\t" + listCount + ". " + output.toString());
                showSubtasks(output);
                listCount += 1;
            }
        }
    }

    /**
     * Retrieve a task from the overdued task list.
     * @param index the index of the task.
     * @return the task at the specified index in the task list.
     * @throws RoomShareException when the index specified is out of bounds.
     */
    public Task get(int index) throws RoomShareException {
        try {
            return overdue.get(index);
        } catch (IndexOutOfBoundsException e) {
            throw new RoomShareException(ExceptionType.outOfBounds);
        }
    }

    /**
     * removes overdue items from the list.
     * supports ranged based deletion
     * @param index array of indices of tasks to be removed
     * @param deletedList list for temporary storage to dump the overdue items into
     * @throws RoomShareException when the indices specified are out of bounds
     */
    public void remove(int[] index, TempDeleteList deletedList) throws RoomShareException {
        int[] idx = index.clone();
        if (idx.length == 1) {
            boolean isNegativeIndex = index[0] < 0;
            boolean isExceededIndex = index[0] >= overdue.size();
            if (isNegativeIndex || isExceededIndex) {
                throw new RoomShareException(ExceptionType.outOfBounds);
            }
            deletedList.add(overdue.get(idx[0]));
            overdue.remove(idx[0]);
        } else {
            boolean isNegativeFirstIndex = index[0] < 0;
            boolean isExceededFirstIndex = index[0] >= overdue.size();
            boolean isNegativeSecondIndex = index[1] < 0;
            boolean isExceededSecondIndex = index[1] >= overdue.size();
            if (isNegativeFirstIndex || isExceededFirstIndex
                    || isNegativeSecondIndex || isExceededSecondIndex) {
                throw new RoomShareException(ExceptionType.outOfBounds);
            }
            for (int i = idx[0]; idx[1] >= idx[0]; idx[1]--) {
                deletedList.add(overdue.get(i));
                overdue.remove(i);
            }
        }
    }

    /**
     * gets the current overdue list.
     * @return ArrayList of tasks representing the overdue list
     */
    public static ArrayList<Task> getOverdueList() {
        return overdue;
    }

    private void showSubtasks(Task task) {
        if (task instanceof Assignment && !(((Assignment) task).getSubTasks() == null)) {
            ArrayList<String> subTasks = ((Assignment) task).getSubTasks();
            for (String subtask : subTasks) {
                System.out.println("\t" + "\t" + "-" + subtask);
            }
        }
    }
}
