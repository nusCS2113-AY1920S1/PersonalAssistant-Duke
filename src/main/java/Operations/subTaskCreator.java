package Operations;

import CustomExceptions.RoomShareException;
import Enums.ExceptionType;
import Model_Classes.Assignment;

public class subTaskCreator {
    /**
     * creates sub tasks for Assignments. Appends the information to the Assignment class
     * throws RoomShareException if there are mistakes in the formatting of sub tasks
     * @param index index of the task to add sub tasks to
     * @param subTasks list of sub tasks to be added to the task
     * @throws RoomShareException when the sub tasks are added to non assignment classes
     */
    public subTaskCreator(int index, String subTasks) throws RoomShareException {
        if (TaskList.get(index) instanceof Assignment) {
            ((Assignment) TaskList.currentList().get(index)).setSubTasks(subTasks);
        } else {
            throw new RoomShareException(ExceptionType.subTaskError);
        }
    }
}
