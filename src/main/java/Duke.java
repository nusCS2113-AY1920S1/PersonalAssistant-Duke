import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Duke {
    private static List<Task> tasks = new ArrayList<>();

    private static Storage storage = new Storage("tasks.dat");

    private static Ui ui = new Ui();

    private static void eval(String line) throws DukeException {
        List<String> words = Arrays.asList(line.split(" "));

        // list all tasks
        if (words.get(0).equals("list") && words.size() == 1) {
            ui.showTaskList(tasks);

        // Find tasks
        } else if (words.get(0).equals("find")) {
            if (words.size() > 1) {
                String searchTerm = String.join(" ", words.subList(1, words.size()));
                List<Task> filteredTasks =
                        tasks.stream()
                                .filter(task -> task.containsKeyword(searchTerm))
                                .collect(Collectors.toList());
                if (filteredTasks.size() > 0) {
                    ui.showSearchResult(filteredTasks);
                } else {
                    throw new DukeException("There are no matching tasks.");
                }
            } else {
                throw new DukeException("Please enter at least a keyword to search.");
            }

        // Delete tasks
        } else if (words.get(0).equals("delete")) {
            try {
                int taskNo = Integer.parseInt(words.get(1));
                Task toRemove = tasks.get(taskNo - 1);
                tasks.remove(toRemove);
                ui.showDeletedTask(tasks, toRemove);
            } catch (NumberFormatException e) {
                throw new DukeException("Please supply a number. Eg: done 2");
            } catch (IndexOutOfBoundsException e) {
                throw new DukeException("Please supply a valid number.");
            }

            // Mark tasks as done
        } else if (words.get(0).equals("done")) {
            try {
                int taskNo = Integer.parseInt(words.get(1));
                Task task = tasks.get(taskNo - 1);
                task.markAsDone();
                ui.showDoneTask(task);
            } catch (NumberFormatException e) {
                throw new DukeException("Please supply a number. Eg: done 2");
            } catch (IndexOutOfBoundsException e) {
                throw new DukeException("Please supply a valid number.");
            }

        // Add todo tasks
        } else if (words.get(0).equals("todo")) {
            Task task = new Todo(words.subList(1, words.size()));
            tasks.add(task);
            ui.showTaskAdded(tasks, task);

        // Add deadline tasks
        } else if (words.get(0).equals("deadline")) {
            Task task = new Deadline(words.subList(1, words.size()));
            tasks.add(task);
            ui.showTaskAdded(tasks, task);

        // Add event task
        } else if (words.get(0).equals("event")) {
            Task task = new Event(words.subList(1, words.size()));
            tasks.add(task);
            ui.showTaskAdded(tasks, task);

        } else {
            throw new DukeException("Please enter a valid command.");
        }

        storage.save(tasks);
    }

    private static void repl() {
        Scanner sc = new Scanner(System.in);
        while (true) {
            String input = sc.nextLine();

            if (input.equals("bye")) {
                return;
            } else {
                ui.beginBlock();
                try {
                    eval(input);
                } catch (DukeException e) {
                    ui.showError(e.getMessage());
                }
                ui.endBlock();
            }
        }
    }

    public static void main(String[] args) {
        ui.showWelcome();

        tasks = storage.load();
        repl();

        ui.showBye();
    }
}
