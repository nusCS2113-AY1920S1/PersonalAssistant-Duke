package Operations;

import CustomExceptions.RoomShareException;
import Enums.ExceptionType;
import Model_Classes.Assignment;
import java.util.ArrayList;
import java.util.Arrays;

public class subTaskCreator {
    /**
     * creates sub tasks for Assignments. Appends the information to the Assignment class.
     * checks for duplicate subTasks
     * throws RoomShareException if there are mistakes in the formatting of sub tasks
     * @param index index of the task to add sub tasks to
     * @param subTasks list of sub tasks to be added to the task
     * @throws RoomShareException when the sub tasks are added to non assignment classes
     */
    public subTaskCreator(int index, String subTasks) throws RoomShareException {
        boolean error = false;
        if (TaskList.get(index) instanceof Assignment) {
            ArrayList<String> temp =  new ArrayList<>(Arrays.asList(subTasks.trim().split(",")));

            ArrayList<String> subtasks = new ArrayList<>();

            for (int i = 0; i < temp.size(); i++) {
                temp.set(i, temp.get(i).trim());
                if (hasSpecialCharacters(temp.get(i))) {
                    throw new RoomShareException(ExceptionType.invalidInputString);
                }
                boolean duplicate = false;
                if (!subtasks.isEmpty()) {
                    for (String subtask : subtasks) {
                        if (temp.get(i).equals(subtask)) {
                            duplicate = true;
                            error = true;
                            break;
                        }
                    }
                }
                if (!duplicate) {
                    subtasks.add(temp.get(i));
                }
            }
            ((Assignment) TaskList.currentList().get(index)).addSubTasks(subtasks);
            if (error) {
                throw new RoomShareException(ExceptionType.duplicateSubtask);
            }
        } else {
            throw new RoomShareException(ExceptionType.subTaskError);
        }
    }

    private boolean hasSpecialCharacters(String input) {
        boolean isInvalid = false;
        if (input.contains("#")) {
            isInvalid = true;
        } else if (input.contains("@")) {
            isInvalid = true;
        } else if (input.contains("!")) {
            isInvalid = true;
        } else if (input.contains("*")) {
            isInvalid = true;
        } else if (input.contains("^")) {
            isInvalid = true;
        } else if (input.contains("%")) {
            isInvalid = true;
        } else if (input.contains("&")) {
            isInvalid = true;
        } else if (input.contains("(")) {
            isInvalid = true;
        }
        return isInvalid;
    }
}
