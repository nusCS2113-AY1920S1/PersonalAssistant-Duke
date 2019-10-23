package duke.command;

import duke.exception.DukeException;
import duke.storage.Storage;
import duke.task.Task;
import duke.tasklist.TaskList;
import duke.ui.TaskListPrinter;
import duke.ui.Ui;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;

public class RandomCommand extends ListCommand {

    public RandomCommand(Optional<String> filter) {
        super(filter);
    }

    public RandomCommand(String modeInformation, Optional<String> filter) {
        super(modeInformation, filter);
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws IOException, ParseException, DukeException {
        Random randomGenerator = new Random();
        int randomIndex = randomGenerator.nextInt(tasks.size());
        ArrayList<Task> randomTaskTemp = new ArrayList<Task>();
        randomTaskTemp.add(tasks.get(randomIndex));
        TaskList randomTask = new TaskList(randomTaskTemp);
        TaskListPrinter.print(ui, randomTask);
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
