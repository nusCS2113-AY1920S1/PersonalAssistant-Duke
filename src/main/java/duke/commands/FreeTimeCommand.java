package duke.commands;

import duke.commons.exceptions.DukeException;
import duke.commons.Messages;
import duke.storage.Storage;
import duke.data.tasks.Task;
import duke.data.tasks.TaskWithDates;

import javafx.collections.transformation.SortedList;

import java.time.LocalDateTime;

public class FreeTimeCommand extends Command {
    private int duration;

    /**
     * Creates a new FreeTimeCommand.
     *
     * @param duration The number of hours of free time.
     */
    public FreeTimeCommand(int duration) {
        this.duration = duration + 1;
    }

    /**
     * Executes this command on the given task list and user interface.
     *
     * @param storage The storage object containing task list.
     */
    @Override
    public CommandResult execute(Storage storage) throws DukeException {
        TaskWithDates primalTask = new TaskWithDates("earliest", LocalDateTime.now().plusMinutes(1));
        TaskWithDates worldEndTask = new TaskWithDates("latest", LocalDateTime.MAX);
        storage.getTasks().add(primalTask);
        storage.getTasks().add(worldEndTask);
        SortedList<Task> tasks = storage.getTasks().getChronoList();
        for (int i = 1; i < tasks.size(); ++i) {
            LocalDateTime prev = ((TaskWithDates) tasks.get(i - 1)).getStartDate();
            LocalDateTime now = ((TaskWithDates) tasks.get(i)).getStartDate();
            if (LocalDateTime.now().compareTo(prev) < 0 && prev.plusHours(duration).compareTo(now) <= 0) {
                storage.getTasks().remove(primalTask);
                storage.getTasks().remove(worldEndTask);
                return new CommandResult(prev.toString());
            }
        }
        //change to time not found later, but this line of code should nvr be executed
        throw new DukeException(Messages.FILE_NOT_FOUND);
    }
}
