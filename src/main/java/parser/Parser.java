package parser;

import exceptions.DukeException;
import storage.Storage;
import task.ToDo;
import task.Event;
import task.Deadline;
import task.Tasks;
import task.TaskList;
import ui.Ui;
import wrapper.TimeInterval;

import javax.swing.*;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * This class deals with making sense of the user command and doing the appropriate actions.
 */
public class Parser {

    /**
     * Takes in users' input and call the aprropriate functions to execute the right action.
     */
    public static int handleCommand(String firstWord, String s){
        int check = 0;
        if (s.equals("bye")) {
            Ui.showByeMessage();
            check = 1;
        }else if(s.contains("freetime")){
            try{
                getFreeSlot(s);
            }catch (DukeException e) {
                Ui.showError(e.getError());
            }

        }else if(s.contains("get conflicts")){

            TaskList.getConflicts();


        }else {
            try {
                switch (firstWord) {
                    case "list":
                        listCommand();
                        break;
                    case "done":
                        doneCommand(s);
                        break;
                    //================================================
                    case "delete":
                        deleteCommand(s);
                        break;
                    //================================================
                    case "find":
                        findCommand(s);
                        break;
                    case "todo":
                        todoCommand(s);
                        break;
                    //================================================
                    case "deadline":
                        deadlineCommand(s);
                        break;
                    //================================================
                    case "event":
                        eventCommand(s);
                        break;
                    default:
                        throw DukeException.UNKNOWN_COMMAND;

                }
            } catch (DukeException e) {
                Ui.showError(e.getError());
            }
        }
        return check;
    }

    private static void getFreeSlot(String s) throws DukeException {

        int num = -1;
        try {
            String[] tokens = s.split(" ");
            num = Integer.parseInt(tokens[1]);
        } catch (NumberFormatException e) {
            throw DukeException.TASK_DOES_NOT_EXIST;
        } catch (IndexOutOfBoundsException e) {
            throw DukeException.TASK_DOES_NOT_EXIST;
        }

        if(num > 0){

            TimeInterval freeslot = TaskList.getFreeSlot(num);

            Ui.showFreeTime(num , freeslot);
        }

    }

    /**
     * Prints the list of tasks in database by calling the showListTask function under Ui class.
     */
    private static void listCommand() {
        Ui.showListIntroMessage();
        List<Tasks> userToDoList = TaskList.getList();
        for (int i = 0; i < userToDoList.size(); i++) {
            String message = userToDoList.get(i).getDescription();
            int j = i + 1;
            Ui.showListTask(userToDoList.get(i).getType(), userToDoList.get(i).getStatusIcon(),
                message, j);
        }
        Ui.printLine();
    }

    /**
     * Mark a particular task as done.
     * Then, prints the confirmation message by calling showMarkAsDone function under Ui class.
     * Finally, updates the database.
     */
    private static void doneCommand(String s) throws DukeException {
        try {
            String[] tokens = s.split(" ");
            int num = Integer.parseInt(tokens[1]);
            TaskList.markTaskAsDone(num - 1);
            String type = TaskList.getType(num - 1);
            String status = TaskList.getStatus(num - 1);
            String message = TaskList.getMessage(num - 1);
            Ui.showMarkAsDone(type, status, message);
            Storage.saveTask(TaskList.getList());
        } catch (ArrayIndexOutOfBoundsException e) {
            throw DukeException.TASK_NO_MISSING;
        } catch (NumberFormatException e) {
            throw DukeException.TASK_DOES_NOT_EXIST;
        } catch (IndexOutOfBoundsException e) {
            throw DukeException.TASK_DOES_NOT_EXIST;
        }
    }

    /**
     * Creates a new To-Do task with users' input.
     * Then, prints a confirmation message by calling showToDoSuccess function under Ui class.
     * Finally, adds the task into database.
     */
    private static void todoCommand(String s) throws DukeException {
        int todolistNumber = TaskList.getTotalTasksNumber() + 1;
        try {
            String joinTokens = s.substring(s.indexOf(" ") + 1);
            if (joinTokens.equals(" ")) {
                throw DukeException.EMPTY_TASK_IN_TODO;
            }
            if (s.length() == 4) {
                throw DukeException.EMPTY_TASK_IN_TODO;
            }
            if (joinTokens.isEmpty()) {
                throw DukeException.EMPTY_TASK_IN_TODO;
            }
            Tasks newToDo = new ToDo(joinTokens, "T");
            TaskList.addTask(newToDo);
            Ui.showToDoSucess(TaskList.getType(todolistNumber - 1),
                TaskList.getStatus(todolistNumber - 1), TaskList.getMessage(todolistNumber - 1),
                TaskList.getTotalTasksNumber());
            Storage.saveTask(TaskList.getList());
        } catch (DukeException e) {
            Ui.showError(e.getError());
        }
    }

    /**
     * Prints tasks that contain the keyword entered by user.
     */
    private static void findCommand(String s) throws DukeException {
        try {
            String find = s.substring(5);
            int j = 1;
            if (find.equals(" ") || (find.equals(""))) {
                throw DukeException.TASK_NO_MISSING_FIND;
            } else {
                Ui.showFindIntroMessage();
                String findLowerCase = find.toLowerCase();
                for (int i = 0; i < TaskList.getTotalTasksNumber(); i += 1) {
                    String message = TaskList.getMessage(i).toLowerCase();
                    String type = TaskList.getType(i).toLowerCase();
                    String icon = TaskList.getStatus(i);
                    if ((message.contains(findLowerCase)) || (type.contains(findLowerCase))
                        || (icon.contains(findLowerCase))) {
                        Ui.showFindTasks(TaskList.getType(i), TaskList.getStatus(i),
                            TaskList.getMessage(i), j);
                        j += 1;
                    }
                }
                Ui.printLine();
            }
        } catch (StringIndexOutOfBoundsException e) {
            throw DukeException.TASK_NO_MISSING_FIND;
        }
    }

    /**
     * Deletes a particular task as entered by user.
     * Then, prints the confirmation message by calling showDeleteMessage function under Ui class.
     * Finally, updates the database.
     */
    private static void deleteCommand(String s) throws DukeException {
        try {
            String[] tokens = s.split(" ");
            int num = Integer.parseInt(tokens[1]);
            Ui.showDeleteMessage(TaskList.getType(num - 1),
                TaskList.getStatus(num - 1), TaskList.getMessage(num - 1),
                TaskList.getTotalTasksNumber() - 1);
            TaskList.removeTask(num - 1);
            Storage.saveTask(TaskList.getList());
        } catch (ArrayIndexOutOfBoundsException e) {
            throw DukeException.TASK_NO_MISSING_DELETE;
        } catch (NumberFormatException e) {
            throw DukeException.TASK_DOES_NOT_EXIST;
        } catch (IndexOutOfBoundsException e) {
            throw DukeException.TASK_DOES_NOT_EXIST;
        }
    }



    /**
     * Creates a new event task with users' input.
     * Then, prints a confirmation message by calling showEventMessage function under Ui class.
     * Finally, adds the task into database.
     */
    private static void eventCommand(String s) throws DukeException {
        try {
            String todoTask1 = s.substring(6, s.indexOf("/at"));
            String time1 = s.substring(s.indexOf("/at") + 4);
            String[] startendtime = time1.split("to");
//            Date timetemp1 = TimeParser.convertStringToDate(time1);
//            time1 = TimeParser.convertDateToLine(timetemp1);
            if (todoTask1.isEmpty()) {
                throw DukeException.EMPTY_TASK_IN_EVENT;
            }
            if (todoTask1.equals(" ")) {
                throw DukeException.EMPTY_TASK_IN_EVENT;
            }
            if (time1.isEmpty()) {
                throw DukeException.EMPTY_TIME_IN_EVENT;
            }
            if (time1.equals(" ")) {
                throw DukeException.EMPTY_TIME_IN_EVENT;
            }
            Event task1 = new Event(todoTask1, "E", startendtime[0] ,startendtime[1]);
            String newtodoTask1 = task1.toMessage();
            Tasks newToDo1 = new Event(newtodoTask1, "E", startendtime[0] ,startendtime[1]);
            int todolistNumber = TaskList.getTotalTasksNumber();
            TaskList.addTask(newToDo1);
            Ui.showEventMessage(TaskList.getStatus(todolistNumber), newtodoTask1, todolistNumber + 1);
            Storage.saveTask(TaskList.getList());
        } catch (StringIndexOutOfBoundsException e) {
            throw DukeException.INVALID_FORMAT_IN_EVENT;
        }
    }

    /**
     * Creates a new deadline task with users' input.
     * Then, prints a confirmation message by calling showDeadlineMessage function under Ui class.
     * Finally, adds the task into database.
     */
    private static void deadlineCommand(String s) throws DukeException {
        int todolistNumber = TaskList.getTotalTasksNumber();
        try {
            String todoTask = s.substring(9, s.indexOf("/by"));
            String time = s.substring(s.indexOf("/by") + 4);
//            TimeParser timeParser = new TimeParser();
//            time = timeParser.convertStringToDate(time);
            //==============================================
            if (todoTask.isEmpty()) {
                throw DukeException.EMPTY_TASK_IN_DEADLINE;
            }
            if (todoTask.equals(" ")) {
                throw DukeException.EMPTY_TASK_IN_DEADLINE;
            }
            if (time.isEmpty()) {
                throw DukeException.EMPTY_TIME_IN_DEADLINE;
            }
            if (time.equals(" ")) {
                throw DukeException.EMPTY_TIME_IN_DEADLINE;
            }
            //==============================================
            Deadline task = new Deadline(todoTask, "D", time);
            String newtodoTask = task.toMessage();
            Tasks newToDo2 = new Deadline(newtodoTask, "D", time);
            TaskList.addTask(newToDo2);
            Ui.showDeadlineMessage(TaskList.getStatus(todolistNumber), newtodoTask, todolistNumber + 1);
            Storage.saveTask(TaskList.getList());
        } catch (StringIndexOutOfBoundsException e) {
            throw DukeException.INVALID_FORMAT_IN_DEADLINE;
        }
    }


}

