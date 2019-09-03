import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

import exceptions.DukeException;
import storage.Storage;
import task.*;


public class Duke {

    private static List<Tasks> userToDoList;
    protected static String logo =
        " ____        _        \n"
            + "|  _ \\ _   _| | _____ \n"
            + "| | | | | | | |/ / _ \\\n"
            + "| |_| | |_| |   <  __/\n"
            + "|____/ \\__,_|_|\\_\\___|\n";
    protected static String line = "   ____________________________________________________________";
    protected static String space = "    ";
    static int todolist_number = 1;
    private static Storage storage;


    public static void main(String[] args) throws DukeException {
        Duke myDuke = new Duke();
        myDuke.run();
    }

    /**
     * Runs the duke program where it first extracts tasks from duke.txt.
     * After which, it takes in input from users and run the appropriate commands.
     */
    public void run() throws DukeException {

        storage = new Storage("data/duke.txt");
        try {
            userToDoList = storage.getTasksFromDatabase();
        } catch (NoSuchElementException e) {
            userToDoList = new ArrayList<>();
            // no tasks in database
        }
        //=================================
        System.out.println(space + "Hello from\n" + logo + "\n" + space + "Hello I am " + "Duke.");
        System.out.println(space + "What can I do for you?");
        System.out.println(line);
        //=================================
        while (true) {
            Scanner in = new Scanner(System.in);
            String s = in.nextLine();
            String[] arr = s.split(" ", 2);
            String firstWord = arr[0];
            if (s.equals("bye")) {
                System.out.println(line + "\n" + space + "Bye. Hope to see you again soon!");
                System.out.println(line);
                break;
            }
            try {
                switch (firstWord) {
                    case "list":
                        System.out.println(line + "\n" + space + "Here are the tasks in your list:");
                        for (int i = 0; i < userToDoList.size(); i++) {
                            String message = userToDoList.get(i).getDescription();
                            int j = i + 1;
                            System.out.println(space + j + ".[" + userToDoList.get(i).getType()
                                + "][" + userToDoList.get(i).getStatusIcon()
                                + "] " + message);
                        }
                        System.out.println(line);
                        break;
                    //================================================
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
                System.out.println(line + "\n" + space + e.getError() + "\n" + line);
            }
        }
    }

    private void findCommand(String s) throws DukeException {
        try {
            String find = s.substring(5);
            int j = 1;
            if (find.equals(" ") || (find.equals(""))) {
                throw DukeException.TASK_NO_MISSING_FIND;
            } else {
                System.out.println(line + "\n" + space + "Here are the matching tasks in your list:");
                for (int i = 0; i < userToDoList.size(); i += 1) {
                    String desLowerCase = userToDoList.get(i).getDescription().toLowerCase();
                    String findLowerCase = find.toLowerCase();
                    if (desLowerCase.contains(findLowerCase)) {
                        System.out.println(space + j + ".["
                            + userToDoList.get(i).getType() + "][" + userToDoList.get(i).getStatusIcon()
                            + "] " + userToDoList.get(i).getDescription());
                        j += 1;
                    }
                }
                System.out.println(line);
            }
        } catch (StringIndexOutOfBoundsException e) {
            throw DukeException.TASK_NO_MISSING_FIND;
        }
    }

    private void deleteCommand(String s) throws DukeException {
        try {
            String[] tokens = s.split(" ");
            int num = Integer.parseInt(tokens[1]);

            System.out.println(line + "\n" + space + "Noted. I've removed this task:"
                + "\n" + space + " [" + userToDoList.get(num - 1).getType()
                + "][" + userToDoList.get(num - 1).getStatusIcon()
                + "] " + userToDoList.get(num - 1).getDescription());
            userToDoList.remove(num - 1);
            storage.saveTask(userToDoList);
            System.out.println(space + "Now you have " + userToDoList.size() + " tasks in the list.");
            System.out.println(line);
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
            todolist_number = userToDoList.size() + 1;
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
            userToDoList.add(newToDo1);

            System.out.println(line + "\n" + space + "Got it. I've added this task:" + "\n" + space + " [E]["
                + userToDoList.get(todolist_number - 1).getStatusIcon()
                + "] " + todoTask1 + "(at: " + time1 + ")");


            if (todolist_number > 1) {
                System.out.println(space + "Now you have " + todolist_number + " tasks in the list.");
            } else {
                System.out.println(space + "Now you have " + todolist_number + " task in the list.");
            }
            System.out.println(line);
            storage.saveTask(userToDoList);
        } catch (StringIndexOutOfBoundsException e) {
            throw DukeException.INVALID_FORMAT_IN_EVENT;
        }
    }


    private static void deadlineCommand(String s) throws DukeException {
        todolist_number = userToDoList.size() + 1;
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
            userToDoList.add(newToDo2);
            System.out.println(line + "\n" + space + "Got it. I've added this task:" + "\n" + space + " [D]["
                + userToDoList.get(todolist_number - 1).getStatusIcon()
                + "] " + todoTask + "(by: " + time + ")");
            if (todolist_number > 1) {
                System.out.println(space + "Now you have " + todolist_number + " tasks in the list.");
            } else {
                System.out.println(space + "Now you have " + todolist_number + " task in the list.");
            }
            System.out.println(line);
            todolist_number += 1;
            storage.saveTask(userToDoList);

        } catch (StringIndexOutOfBoundsException e) {
            throw DukeException.INVALID_FORMAT_IN_DEADLINE;
        }
    }

    private static void todoCommand(String s) throws DukeException {
        todolist_number = userToDoList.size() + 1;

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
            userToDoList.add(newToDo);
            System.out.println(line + "\n" + space + "Got it. I've added this task:"
                + "\n" + space + " [" + userToDoList.get(todolist_number - 1).getType()
                + "][" + userToDoList.get(todolist_number - 1).getStatusIcon()
                + "] " + joinTokens);

            if (todolist_number > 1) {
                System.out.println(space
                    + "Now you have " + todolist_number + " tasks in the list.");
            } else {
                System.out.println(space + "Now you have " + todolist_number + " task in the list.");
            }
            todolist_number += 1;
            System.out.println(line);
            storage.saveTask(userToDoList);


        } catch (DukeException e) {
            System.out.println(line + "\n" + space + e.getError() + "\n" + line);
        }
    }

    private static void doneCommand(String s) throws DukeException {
        try {
            String[] tokens = s.split(" ");
            int num = Integer.parseInt(tokens[1]);
            userToDoList.get(num - 1).setDone(true);
            System.out.println(line + "\n" + space + "Nice! I've marked this task as done:");
            System.out.println(space + " [" + userToDoList.get(num - 1).getType()
                + "][" + userToDoList.get(num - 1).getStatusIcon()
                + "] " + userToDoList.get(num - 1).getDescription());
            System.out.println(line);
            storage.saveTask(userToDoList);
        } catch (ArrayIndexOutOfBoundsException e) {
            throw DukeException.TASK_NO_MISSING;
        } catch (NumberFormatException e) {
            throw DukeException.TASK_DOES_NOT_EXIST;
        } catch (IndexOutOfBoundsException e) {
            throw DukeException.TASK_DOES_NOT_EXIST;
        }
    }
}

