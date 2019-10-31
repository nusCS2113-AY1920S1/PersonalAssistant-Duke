package duke.logic.command;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;

import duke.exception.DukeException;
import duke.storage.Storage;
import duke.task.Task;
import duke.tasklist.TaskList;
import duke.ui.TaskListPrinter;
import duke.ui.Ui;

/**
 * Class which executes the command of displaying a random Task to user
 */
public class RandomCommand extends ListCommand {
    /**
     * Constructor for RandomCommand
     *
     * @param filter filter for each task
     */
    public RandomCommand(Optional<String> filter) {
        super(filter);
    }

    /**
     * Constructor for RandomCommand
     *
     * @param modeInformation information of the sorted order
     * @param filter          filter for each task
     */
    public RandomCommand(String modeInformation, Optional<String> filter) {
        super(modeInformation, filter);
    }

    /**
     * Shows random task to user
     *
     * @param tasks
     * @param ui      Ui handling user interactions
     * @param storage Storage handling saving and loading of TaskList
     * @throws DukeException  if specified sort order given is invalid
     * @throws IOException    NA
     * @throws ParseException NA
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws IOException, ParseException, DukeException {
        Random randomGenerator = new Random();
        int randomIndex = randomGenerator.nextInt(tasks.size());
        ArrayList<Task> randomTaskTemp = new ArrayList<Task>();
        randomTaskTemp.add(tasks.get(randomIndex));
        TaskList randomTask = new TaskList(randomTaskTemp);
        TaskListPrinter.print(ui, randomTask);
    }
}
