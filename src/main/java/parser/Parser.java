package parser;

import exceptions.DukeException;
import storage.Storage;
import duke.task.DoAfter;
import duke.task.TaskList;
import duke.task.Deadline;
import duke.task.Tasks;
import duke.task.Event;
import duke.task.ToDo;
import duke.task.Recurring;
import duke.task.FixedDuration;
import duke.task.Period;
import duke.ui.Ui;
import wrapper.TimeInterval;

import java.util.Scanner;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * This class deals with making sense of the user command and doing the appropriate actions.
 */
public class Parser {

    /**
     * Takes in users' input and call the aprropriate functions to execute the right action.
     */
    public static int handleCommand(String firstWord, String s) {
        int check = 0;
        if (s.equals("bye")) {
            Ui.showByeMessage();
            check = 1;
        } else if (s.contains("freetime")) {
            try {
                getFreeSlot(s);
            } catch (DukeException e) {
                Ui.showError(e.getError());
            }

        } else if (s.contains("get conflicts")) {

            TaskList.getConflicts();


        } else if (s.contains("change date")) {
            try {
                changeDate(s);
            } catch (DukeException e) {
                Ui.showError(e.getError());
            }
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
                case "do":
                    doAfterCommand(s);
                    break;
                case "fix":
                    fixCommand(s);
                    break;
                case "recur":
                    recurCommand(s);
                    break;
                case "reminder":
                    reminderCommand(s);
                    break;
                case "fixed":
                    fixedCommand(s);
                    break;
                case "schedule":
                    scheduleCommand(s);
                    break;
                case "period":
                    periodCommand(s);
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

    /**
     * This function deals when user wants to fix a tentative duke.task.
     * @param s the whole user command.
     * @throws DukeException when user did not enter duke.task number or select wrong duke.task.
     * */
    private static void fixCommand(String s) throws DukeException {
        try {
            String[] tokens = s.split(" ");
            int num = Integer.parseInt(tokens[1]);
            String message = TaskList.getMessage(num - 1).substring(0,
                TaskList.getMessage(num - 1).indexOf("(at:"));
            String date = TaskList.getMessage(num - 1).substring(
                TaskList.getMessage(num - 1).indexOf("(at:") + 5);
            String[] time = date.split(" to ");
            time[1] = time[1].substring(0, time[1].length() - 1);
            //System.out.println(message + date);
            String type = TaskList.getType(num - 1);
            if (!(type.equals("?][E"))) {
                throw DukeException.TASK_IS_NOT_TENTATIVE;
            }
            int upper = num - 1;
            int lower = num - 1;
            int start = 0;
            int end = TaskList.getTotalTasksNumber() - 1;
            while ((end >= 0) || (start <= TaskList.getTotalTasksNumber() - 1)) {
                String checkMessage = new String();
                String checkMessage2 = new String();
                if (upper - 1 >= 0) {
                    checkMessage = TaskList.getMessage(upper - 1).substring(0,
                        TaskList.getMessage(upper - 1).indexOf("(at:"));
                }
                if (lower + 1 <= TaskList.getTotalTasksNumber() - 1) {
                    checkMessage2 = TaskList.getMessage(lower + 1).substring(0,
                        TaskList.getMessage(lower + 1).indexOf("(at:"));
                }
                //System.out.println(upper + " " + lower);
                if ((upper - 1 >= 0) && (checkMessage.equals(message))
                    && (TaskList.getType(upper - 1).equals("?][E"))) {
                    upper -= 1;
                }
                if ((lower + 1 <= TaskList.getTotalTasksNumber() - 1)
                    && (checkMessage2.equals(message))
                    && (TaskList.getType(lower + 1).equals("?][E"))) {
                    //String[] checkMessage2 = TaskList.getMessage(lower).split("at:");
                    lower += 1;
                }
                start += 1;
                end -= 1;
            }
            //System.out.println(upper + " " + lower);
            while (lower >= upper) {
                TaskList.removeTask(upper);
                lower -= 1;
            }

            Event task1 = new Event(message, "E", time[0], time[1]);
            String newtodoTask1 = task1.toMessage();
            Tasks newToDo1 = new Event(newtodoTask1, "E", time[0], time[1]);
            int todolistNumber = TaskList.getTotalTasksNumber();
            TaskList.addTask(newToDo1);
            Ui.showEventMessage(TaskList.getType(todolistNumber), TaskList.getStatus(todolistNumber),
                newtodoTask1, todolistNumber + 1);
            Storage.saveTask(TaskList.getList());

        } catch (ArrayIndexOutOfBoundsException e) {
            throw DukeException.TASK_NO_MISSING;
        } catch (NumberFormatException e) {
            throw DukeException.TASK_DOES_NOT_EXIST;
        } catch (IndexOutOfBoundsException e) {
            throw DukeException.TASK_DOES_NOT_EXIST;
        }
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

        if (num > 0) {

            TimeInterval freeslot = TaskList.getFreeSlot(num);

            Ui.showFreeTime(num, freeslot);
        }

    }

    private static void changeDate(String s) throws DukeException {

        int num = -1;
        try {
            String[] tokens = s.split(" ");
            num = Integer.parseInt(tokens[2]);

            Tasks temp = TaskList.getList().get(num - 1);

            Scanner scan = new Scanner(System.in);

            if (temp.getType().equals("E")) {
                Ui.showMsg("Enter new start and end date");
                String inputStr = scan.nextLine().trim();
                String[] startendtime = inputStr.split("to");
                ((Event) temp).setTime(startendtime[0], startendtime[1]);
                Ui.updateTime(temp);


            } else if (temp.getType().equals("D")) {
                Ui.showMsg("Enter new deadline");
                String inputStr = scan.nextLine().trim();
                ((Deadline) temp).setTime(inputStr);
                Ui.updateTime(temp);

            } else {
                Ui.showError("You cannot change the date of the duke.task!");
            }

            Storage.saveTask(TaskList.getList());


        } catch (NumberFormatException e) {
            throw DukeException.TASK_DOES_NOT_EXIST;
        } catch (IndexOutOfBoundsException e) {
            throw DukeException.TASK_DOES_NOT_EXIST;
        }
    }

    /**
     * Creates a new doAfter duke.task with users' input.
     * Then, prints a confirmation message by calling showdoAfterMessage function under Ui class.
     * Finally, adds the duke.task into database.
     */
    private static void doAfterCommand(String s) throws DukeException {
        try {
            String doAfterTask = s.substring(3, s.indexOf("/after"));
            String time1 = s.substring(s.indexOf("/after") + 7);
            if ((doAfterTask.isEmpty()) || (doAfterTask.equals(" "))) {
                throw DukeException.EMPTY_TASK_IN_DOAFTER;
            }
            if ((time1.isEmpty()) || (time1.equals(" "))) {
                throw DukeException.EMPTY_TIME_IN_DOAFTER;
            }
            DoAfter task1 = new DoAfter(doAfterTask, "A", time1);
            String newtodoTask1 = task1.toMessage();
            Tasks newToDo1 = new DoAfter(newtodoTask1, "A", time1);
            int todolistNumber = TaskList.getTotalTasksNumber();
            TaskList.addTask(newToDo1);
            Ui.showDoAfterMessage(TaskList.getStatus(todolistNumber), newtodoTask1, todolistNumber + 1);
            Storage.saveTask(TaskList.getList());
        } catch (StringIndexOutOfBoundsException e) {
            throw DukeException.INVALID_FORMAT_IN_DOAFTER;
        }
    }

    /**
     * Prints the list of tasks in database by calling the showListTask function under Ui class.
     */
    public static void listCommand() {
        Ui.showListIntroMessage();
        List<Tasks> userToDoList = TaskList.getList();
        for (int i = 0; i < userToDoList.size(); i++) {
            String message;
            switch (userToDoList.get(i).getType()) {
                case "E":
                    message = ((Event) userToDoList.get(i)).getDescription();
                    break;
                case "D":
                    message = ((Deadline) userToDoList.get(i)).toMessage();
                    break;
                case "T":
                    message = ((ToDo) userToDoList.get(i)).toMessage();
                    break;
                case "R":
                    message = ((Recurring)userToDoList.get(i)).toMessage();
                    break;
                case "F":
                    message = ((FixedDuration)userToDoList.get(i)).toMessage();
                    break;
                case "P":
                    message = ((Period)userToDoList.get(i)).toMessage();
                    break;
                default:
                    message = (userToDoList.get(i)).getDescription();
                    break;
            }
            int j = i + 1;
            Ui.showListTask(userToDoList.get(i).getType(), userToDoList.get(i).getStatusIcon(),
                message, j);
        }
        Ui.printLine();
    }

    /**
     * Mark a particular duke.task as done.
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
     * Creates a new To-Do duke.task with users' input.
     * Then, prints a confirmation message by calling showToDoSuccess function under Ui class.
     * Finally, adds the duke.task into database.
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
     * Creates a new Recurring Task with users input.
     */
    private static void recurCommand(String command) throws DukeException {
        int todolistNumber = TaskList.getTotalTasksNumber() + 1;
        try {
            String[] token = command.substring("recur".length()).strip().split("/frequency");
            if (token.length != 2 || token[1] == null) {
                throw DukeException.EMPTY_TIME_IN_RECUR;
            }
            if (token[0].strip().isEmpty()) {
                throw DukeException.EMPTY_TASK_IN_RECUR;
            }
            TaskList.addTask(new Recurring(token[0].strip(), "R", token[1].strip()));
            Ui.showToDoSucess(TaskList.getType(todolistNumber - 1),
                TaskList.getStatus(todolistNumber - 1), TaskList.getMessage(todolistNumber - 1),
                TaskList.getTotalTasksNumber());
            Storage.saveTask(TaskList.getList());
        } catch  (DukeException e) {
            throw e;
        }
    }

    /**
     * Prints tasks that have to be done within a certain period of time
     */
    private static void periodCommand(String command) throws DukeException {
        int todolistNumber = TaskList.getTotalTasksNumber() + 1;
        try {
            String[] token = command.substring("period".length()).strip().split("/duration");
            String[] dates = token[1].strip().split("to");
            if (token.length != 2 || token[1] == null) {
                throw DukeException.EMPTY_TIME_IN_PERIOD;
            }
            if (token[0].strip().isEmpty()) {
                throw DukeException.EMPTY_TASK_IN_PERIOD;
            }
            TaskList.addTask(new Period(token[0].strip(), "P", dates[0].strip(), dates[1].strip()));
            Ui.showToDoSucess(TaskList.getType(todolistNumber - 1),
                    TaskList.getStatus(todolistNumber - 1), TaskList.getMessage(todolistNumber - 1),
                    TaskList.getTotalTasksNumber());
            Storage.saveTask(TaskList.getList());
        } catch  (DukeException e) {
            throw e;
        }
    }

    /**
     * Prints tasks that have a fixed duration
     */
    private static void fixedCommand(String command) throws DukeException {
        int todolistNumber = TaskList.getTotalTasksNumber() + 1;
        try {
            String[] token = command.substring("fixed".length()).strip().split("/duration");
            if (token.length != 2 || token[1] == null) {
                throw DukeException.EMPTY_TIME_IN_FIXED;
            }
            if (token[0].strip().isEmpty()) {
                throw DukeException.EMPTY_TASK_IN_FIXED;
            }
            TaskList.addTask(new FixedDuration(token[0].strip(), "F", token[1].strip()));
            Ui.showToDoSucess(TaskList.getType(todolistNumber - 1),
                    TaskList.getStatus(todolistNumber - 1), TaskList.getMessage(todolistNumber - 1),
                    TaskList.getTotalTasksNumber());
            Storage.saveTask(TaskList.getList());
        } catch  (DukeException e) {
            throw e;
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
     * Deletes a particular duke.task as entered by user.
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
     * Creates a new event duke.task with users' input.
     * Then, prints a confirmation message by calling showEventMessage function under Ui class.
     * Finally, adds the duke.task into database.
     */
    private static void eventCommand(String s) throws DukeException {
        try {
            String todoTask1 = s.substring(6, s.indexOf("/at"));
            String time1 = s.substring(s.indexOf("/at") + 4);
            //Date timetemp1 = TimeParser.convertStringToDate(time1);
            //time1 = TimeParser.convertDateToLine(timetemp1);
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
            if (time1.equals("tentative")) {
                eventTentativeCommand(todoTask1);
            } else {
                String[] startendtime = time1.split("to");
                Event task1 = new Event(todoTask1, "E", startendtime[0], startendtime[1]);
                String newtodoTask1 = task1.toMessage();
                Tasks newToDo1 = new Event(newtodoTask1, "E", startendtime[0], startendtime[1]);
                int todolistNumber = TaskList.getTotalTasksNumber();
                TaskList.addTask(newToDo1);
                Ui.showEventMessage(TaskList.getType(todolistNumber), TaskList.getStatus(todolistNumber),
                    newtodoTask1, todolistNumber + 1);
                Storage.saveTask(TaskList.getList());
            }
        } catch (StringIndexOutOfBoundsException e) {
            throw DukeException.INVALID_FORMAT_IN_EVENT;
        } catch (ArrayIndexOutOfBoundsException e1) {
            throw DukeException.INVALID_FORMAT_IN_EVENT;
        }
    }

    /**
     * Creates a new deadline duke.task with users' input.
     * Then, prints a confirmation message by calling showDeadlineMessage function under Ui class.
     * Finally, adds the duke.task into database.
     */
    private static void deadlineCommand(String s) throws DukeException {
        int todolistNumber = TaskList.getTotalTasksNumber();
        try {
            String todoTask = s.substring(9, s.indexOf("/by"));
            String time = s.substring(s.indexOf("/by") + 4);
            //TimeParser timeParser = new TimeParser();
            // time = timeParser.convertStringToDate(time);
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
            //Tasks newToDo2 = new Deadline(newtodoTask, "D", time);
            TaskList.addTask(task);
            Ui.showDeadlineMessage(TaskList.getStatus(todolistNumber), newtodoTask, todolistNumber + 1);
            Storage.saveTask(TaskList.getList());
        } catch (StringIndexOutOfBoundsException e) {
            throw DukeException.INVALID_FORMAT_IN_DEADLINE;
        }
    }

    /**
     * This function deals when user wants to store tentative tasks.
     * @param todoTask1 description of the duke.task.
     * @throws DukeException when user did not/wrongly input description of duke.task and/or date.
     */
    private static void eventTentativeCommand(String todoTask1) throws DukeException {
        Ui.queryForDates();
        try {
            Scanner scan = new Scanner(System.in);
            String time1 = scan.nextLine().trim();
            if (time1.isEmpty()) {
                throw DukeException.EMPTY_TASK_IN_EVENT_TENTATIVE;
            }
            if (time1.equals(" ")) {
                throw DukeException.EMPTY_TASK_IN_EVENT_TENTATIVE;
            }
            String[] diffDates = time1.split("or");
            for (int i = 0; i < diffDates.length; i += 1) {
                String[] startendtime = diffDates[i].split("to");
                Event task1 = new Event(todoTask1, "?][E", startendtime[0], startendtime[1]);
                String newtodoTask1 = task1.toMessage();
                Tasks newToDo1 = new Event(newtodoTask1, "?][E", startendtime[0], startendtime[1]);
                int todolistNumber = TaskList.getTotalTasksNumber();
                TaskList.addTask(newToDo1);
                if (i == 0) {
                    Ui.showEventTentativeOpeningMessage();
                }
                Ui.showEventTentativeMessage(TaskList.getType(todolistNumber), TaskList.getStatus(todolistNumber),
                    newtodoTask1);
            }
            Ui.showEventTentativeCloseMessage(TaskList.getTotalTasksNumber(),
                TaskList.getTentativeNumber());
            Storage.saveTask(TaskList.getList());

        } catch (NullPointerException e) {
            throw DukeException.INPUT_NOT_FOUND;
        } catch (StringIndexOutOfBoundsException e) {
            throw DukeException.EMPTY_TASK_IN_EVENT_TENTATIVE;
        } catch (ArrayIndexOutOfBoundsException e1) {
            throw DukeException.EMPTY_TASK_IN_EVENT_TENTATIVE;
        }
    }

    /**
     * Find all the deadlines/events on the user specified date
     * Then print them out for user
     * Command: schedule <date>
     */
    private static void scheduleCommand(String s) throws DukeException {
        String[] tokens = s.split(Pattern.quote(" "));
        DukeException.checkSchedule(tokens);
        Ui.showScheduleIntroMessage(tokens[1]);
        tokens[1] = tokens[1] + " 00000";
        int count = 1;
        for (Map.Entry<Date, Tasks> log : TaskList.getTreeMap().entrySet()) {
            if (TimeParser.getDateOnly(log.getKey()).equals(TimeParser.convertStringToDate(tokens[1]))) {
                Ui.printScheduleTask(log);
                count++;
            }
        }
        Ui.showScheduleFinalMessage(count);
        Ui.printLine();
    }

    /**
     * Check for any incomplete events/deadlines since the user specified date
     * The number of reminders is also specified by the user
     * The reminders is then printed out
<<<<<<< HEAD
     * Command: reminder <no. of reminders> <date>
     */
    private static void reminderCommand(String s) throws DukeException {
        String[] tokens = s.split(Pattern.quote(" "));
        DukeException.checkReminder(tokens);
        Ui.showReminderIntroMessage(Integer.valueOf(tokens[1]), tokens[2]);
        int count = 1;
        Date startDate = TimeParser.convertToDate(tokens[2]);
        Date previousDate = null;
        for (Map.Entry<Date, Tasks> log : TaskList.getTreeMap().entrySet()) {
            Date logDate = TimeParser.getDateOnly(log.getKey());
            if (count > Integer.valueOf(tokens[1])) {
                break;
            }
            if (logDate.equals(startDate) || logDate.after(startDate)){
                if (!log.getValue().isDone()){
                    if (!logDate.equals(previousDate)){
                        Ui.printScheduleDate(TimeParser.getStringDate(logDate));
                    }
                    Ui.printScheduleTask(log);
                    count++;
                    previousDate = logDate;
                }
            }
        }
        Ui.showEmptyReminderMessage(count);
        Ui.printLine();
    }
}

