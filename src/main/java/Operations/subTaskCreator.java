package Operations;

import CustomExceptions.RoomShareException;
import Enums.ExceptionType;
import Model_Classes.Assignment;

public class subTaskCreator {
    public subTaskCreator(int index, String subTasks) throws RoomShareException {
        if (TaskList.currentList().get(index) instanceof Assignment) {
            ((Assignment) TaskList.currentList().get(index)).setSubTasks(subTasks);
        } else {
            throw new RoomShareException(ExceptionType.subTask);
        }
    }
}
