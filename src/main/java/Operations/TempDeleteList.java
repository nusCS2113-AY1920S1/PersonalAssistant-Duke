package Operations;

import CustomExceptions.RoomShareException;
import Enums.ExceptionType;
import Model_Classes.Assignment;
import Model_Classes.Task;

import java.util.ArrayList;

public class TempDeleteList {
    private ArrayList<Task> tempDelete;

    /**
     * Constructor for the TempDeleteList Class.
     * Takes in an ArrayList of Task objects as a parameter
     * @param tempDelete ArrayList of Task objects to be operated on
     */
    public TempDeleteList(ArrayList<Task> tempDelete) {
        this.tempDelete = tempDelete;
    }

    /**
     * Adds a Task to the temporary deleted list.
     * @param task Task that was deleted from the main list and
     *             has to be added into the temp delete list
     */
    public void add(Task task) {
        tempDelete.add(task);
    }

    /**
     * Restores a Task from the temp delete list into the main list.
     * ALso removes the Task from the temp delete list
     * if index is not valid, will show the temp delete list to help the
     * user see the deleted items
     * @param index index of the task in the temp delete list that is being restored
     * @param taskList the main list to add the restored task back into
     * @throws RoomShareException if the index entered is not valid
     */
    public void restore(int index, TaskList taskList) throws RoomShareException {
        if (index < 0 || index > tempDelete.size() - 1) {
            System.out.println("This are your tasks in the temp delete list");
            list();
            throw new RoomShareException(ExceptionType.outOfBounds);
        } else {
            taskList.add(tempDelete.get(index));
            this.tempDelete.remove(index);
        }
    }

    /**
     * lists the tasks in the temp delete list.
     * @throws RoomShareException when the list is empty
     */
    public void list() throws RoomShareException {
        if (tempDelete.size() == 0) {
            throw new RoomShareException(ExceptionType.emptyList);
        } else {
            int listCount = 1;
            for (Task output : tempDelete) {
                System.out.println("\t" + listCount + ". " + output.toString());
                if (output instanceof Assignment && (((Assignment) output).getSubTasks() != null)) {
                    ArrayList<String> subTasks = ((Assignment) output).getSubTasks();
                    for (String subtask : subTasks) {
                        System.out.println("\t" + "\t" + "-" + subtask);
                    }
                }
                listCount += 1;
            }
        }
    }
}
