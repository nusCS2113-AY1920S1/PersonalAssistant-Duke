import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Duke {
    private static TaskList taskList;

    private static Ui ui = new Ui();

    private static Storage storage;

    private static void eval(String line) throws DukeException {
        List<String> words = Arrays.asList(line.split(" "));
        Command c = null;

        String keyword = words.get(0);
        List<String> arguments = words.subList(1, words.size());

        // list all tasks
        if (keyword.equals("list") && words.size() == 1) {
            c = new ListCommand();

        // Find tasks
        } else if (keyword.equals("find")) {
            c = new FindCommand(arguments);

        // Delete tasks
        } else if (keyword.equals("delete")) {
            c = new DeleteCommand(arguments);

        // Mark tasks as done
        } else if (keyword.equals("done")) {
            c = new DoneCommand(arguments);

        // Add todo tasks
        } else if (keyword.equals("todo")) {
            c = new AddTodoCommand(arguments);

        // Add deadline tasks
        } else if (keyword.equals("deadline")) {
            c = new AddDeadlineCommand(arguments);

        // Add event task
        } else if (keyword.equals("event")) {
            c = new AddEventCommand(arguments);

        } else {
            throw new DukeException("Please enter a valid command.");
        }

        if (c != null) {
            c.execute(taskList, ui, storage);
        }
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

        storage = new Storage("tasks.dat");
        try {
            taskList = storage.load();
        } catch (DukeException e) {
            taskList = new TaskList();
            ui.showError(e.getMessage());
        }

        repl();

        try {
            storage.save(taskList);
        } catch (DukeException e) {
            ui.showError(e.getMessage());
        }

        ui.showBye();
    }
}
