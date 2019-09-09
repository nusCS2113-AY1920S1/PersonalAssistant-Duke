package parser;

import exceptions.DukeException;
import storage.Storage;
import task.*;
import ui.Ui;

import javax.print.DocFlavor;
import java.util.List;

public class Parser {

    public static int handleCommand(String firstWord, String s) {
        int check = 0;
        if (s.equals("bye")) {
            Ui.showByeMessage();
            check = 1;
        } else {
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

    private static void todoCommand(String s) throws DukeException {
        int todolist_number = TaskList.getTotalTasksNumber() + 1;
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
            Ui.showToDoSucess(TaskList.getType(todolist_number - 1),
                TaskList.getStatus(todolist_number - 1), TaskList.getMessage(todolist_number - 1),
                TaskList.getTotalTasksNumber());
            Storage.saveTask(TaskList.getList());
        } catch (DukeException e) {
            Ui.showError(e.getError());
        }
    }

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
                    if ((message.contains(findLowerCase)) || (type.contains(findLowerCase)) ||
                        (icon.contains(findLowerCase))) {
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

    private static void eventCommand(String s) throws DukeException {
        try {
            int todolist_number = TaskList.getTotalTasksNumber();
            String todoTask1 = s.substring(6, s.indexOf("/at"));
            String time1 = s.substring(s.indexOf("/at") + 4);
            TimeParser timeParser = new TimeParser();
            time1 = timeParser.convertStringToDate(time1);
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
            Event task1 = new Event(todoTask1, "E", time1);
            String newtodoTask1 = task1.toMessage();
            Tasks newToDo1 = new Event(newtodoTask1, "E", time1);
            TaskList.addTask(newToDo1);
            Ui.showEventMessage(TaskList.getStatus(todolist_number), newtodoTask1, todolist_number + 1);
            Storage.saveTask(TaskList.getList());
        } catch (StringIndexOutOfBoundsException e) {
            throw DukeException.INVALID_FORMAT_IN_EVENT;
        }
    }


    private static void deadlineCommand(String s) throws DukeException {
        int todolist_number = TaskList.getTotalTasksNumber();
        try {
            String todoTask = s.substring(9, s.indexOf("/by"));
            String time = s.substring(s.indexOf("/by") + 4);
            TimeParser timeParser = new TimeParser();
            time = timeParser.convertStringToDate(time);
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
            Ui.showDeadlineMessage(TaskList.getStatus(todolist_number), newtodoTask, todolist_number + 1);
            Storage.saveTask(TaskList.getList());
        } catch (StringIndexOutOfBoundsException e) {
            throw DukeException.INVALID_FORMAT_IN_DEADLINE;
        }
    }


}

