package Operations;

import CustomExceptions.RoomShareException;
import Enums.ExceptionType;
import Model_Classes.Assignment;
import Model_Classes.Task;
import Enums.TaskType;


import java.util.ArrayList;

public class OverdueList {
    private ArrayList<Task> Overdue;

    /**
     * A constructor for the overdueList class.
     * Takes in an ArrayList of Task objects as a parameter.
     * @param Overdue ArrayList of Task object to be operated on.
     */
    public OverdueList(ArrayList<Task> Overdue) {
        this.Overdue = Overdue;
    }

    /**
     * Adds a Task to the Overdued task list.
     * @param task Task that was overdue and added into the
     *             Overdued task list.
     */
    public void add(Task task) {
        Overdue.add(task);
    }

    /**
     * Reschedules an overdue task that was in the overdued list to be placed back into
     * the original task list for the user.
     *
     * @param index index of the task in the Overdued task list that is being rescheduled.
     * @throws RoomShareException if the index entered is not valid
     */
    public void reschedule(int index, TaskList taskList) throws RoomShareException {
        if (index < 0 || index > Overdue.size() - 1) {
            System.out.println("This are your tasks in your Overdue list");
            list();
            throw new RoomShareException(ExceptionType.outOfBounds);
        } else {
            taskList.add(Overdue.get(index));
            Overdue.get(index).setOverdue(false);
            this.Overdue.remove(index);
        }
    }

    /**
     * lists the tasks that are current in the overdued task list.
     * @throws RoomShareException when the list is empty
     */
    public void list() throws RoomShareException {
        if (Overdue.size() == 0) {
            throw new RoomShareException(ExceptionType.emptyList);
        } else {
            int listCount = 1;
            for (Task output : Overdue) {
                System.out.println("\t" + listCount + ". " + output.toString());
                if( output instanceof Assignment && !(((Assignment) output).getSubTasks() == null) ) {
                    ArrayList<String> subTasks = ((Assignment) output).getSubTasks();
                    for(String subtask : subTasks) {
                        System.out.println("\t" + "\t" + "-" + subtask);
                    }
                }
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
    public Task get(int index) throws RoomShareException{
        try {
            return Overdue.get(index);
        } catch (IndexOutOfBoundsException e) {
            throw new RoomShareException(ExceptionType.outOfBounds);
        }
    }

    public void remove(int[] index, TempDeleteList deletedList) throws RoomShareException {
        int[] idx = index.clone();
        if (idx.length == 1) {
            if (idx[0] < 0 || idx[0] >= Overdue.size()) {
                throw new RoomShareException(ExceptionType.outOfBounds);
            }
            deletedList.add(Overdue.get(idx[0]));
            Overdue.remove(idx[0]);
        }
        else {
            if (idx[0] < 0 || idx[0] >= Overdue.size() || idx[1] < 0 || idx[1] >= Overdue.size()) {
                throw new RoomShareException(ExceptionType.outOfBounds);
            }
            for (int i = idx[0]; idx[1] >= idx[0]; idx[1]--) {
                deletedList.add(Overdue.get(i));
                Overdue.remove(i);
            }
        }
    }
}
