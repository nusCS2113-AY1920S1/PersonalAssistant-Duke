import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Duke {
    private static List<Task> taskList = new ArrayList<>();

    public static void main(String[] args) {
        /*
        String logo = " ____        _        \n"
                + "|  _ \\ _   _| | _____ \n"
                + "| | | | | | | |/ / _ \\\n"
                + "| |_| | |_| |   <  __/\n"
                + "|____/ \\__,_|_|\\_\\___|\n";
        System.out.println("Hello from\n" + logo);
         */
        Ui.print_line();
        taskList = new FileManager().LoadFile();
        System.out.println("Hello! I'm Duke\nWhat can I do for you?");
        Ui.print_line();
        run();
    }

    public static void run() {
        Boolean isExit = false;
        while (!isExit)
            try {
                Parser line = new Parser();
                String command = line.getCommand();
                switch (command) {
                    case "done":
                        int listNum = line.getIndex();
                        if (listNum >= 0 && listNum < taskList.size()) {
                            taskList.get(listNum).markDone();
                            Task currTask = taskList.get(listNum);
                            Ui.printMarkDone(currTask);
                            new FileManager().saveFile(taskList);
                        } else {
                            throw new DukeException("", DukeException.ExceptionType.OUT_OF_RANGE);
                        }
                        break;
                    case "delete":
                        listNum = line.getIndex();
                        if (listNum >= 0 && listNum < taskList.size()) {
                            Task currTask = taskList.get(listNum);
                            Ui.printDeleted(currTask, taskList);
                            taskList.remove(listNum);
                            new FileManager().saveFile(taskList);
                        } else {
                            throw new DukeException("", DukeException.ExceptionType.OUT_OF_RANGE);
                        }
                        break;
                    case "bye":
                        Ui.printBye();
                        new FileManager().saveFile(taskList);
                        isExit = true;
                        System.exit(0);
                        break;
                    case "list":
                        Ui.printList(taskList);
                        break;
                    case "find":
                        String argument = line.getArgument().toLowerCase();
                        if (argument.isEmpty())
                            throw new DukeException("", DukeException.ExceptionType.INVALID_ARGUMENT);
                        else {
                            List<Task> foundList = taskList.stream()
                                    .filter(x -> x.description.toLowerCase().contains(argument)).collect(Collectors.toList());
                            Ui.printList(foundList);
                        }
                        break;
                    case "todo":
                        String description = line.buildTodo();
                        if (description.isEmpty()) {
                            throw new DukeException("", DukeException.ExceptionType.INVALID_TODO);
                        }
                        Todo todo = new Todo(description);
                        Ui.printTodo(todo, taskList);
                        taskList.add(todo);
                        new FileManager().saveFile(taskList);
                        break;
                    case "deadline":
                        line.buildDeadline();
                        description = line.description;
                        String by = line.additional;
                        if (description.isEmpty()) {
                            throw new DukeException("", DukeException.ExceptionType.INVALID_DEADLINE);
                        }
                        if (by.isEmpty()) {
                            throw new DukeException("", DukeException.ExceptionType.DEADLINE_TIME);
                        }
                        LocalDateTime localDateTime = new DukeDateTime().getLocalDateTime(by);
                        Deadline deadline = new Deadline(description, localDateTime);
                        taskList.add(deadline);
                        Ui.printDeadline(deadline, taskList);
                        new FileManager().saveFile(taskList);
                        break;
                    case "event":
                        line.buildEvent();
                        description = line.description;
                        String at = line.additional;
                        if (description.isEmpty()) {
                            throw new DukeException("", DukeException.ExceptionType.INVALID_EVENT);
                        }

                        if (at.isEmpty()) {
                            throw new DukeException("", DukeException.ExceptionType.EVENT_TIME);
                        }
                        Event event = new Event(description, at);
                        Ui.printEvent(event, taskList);
                        taskList.add(event);
                        new FileManager().saveFile(taskList);
                        break;
                    default:
                        throw new DukeException("", DukeException.ExceptionType.INVALID_COMMAND);
                }
            } catch (DukeException e) {
                e.PrintExceptionMessage();
            }
    }

}

