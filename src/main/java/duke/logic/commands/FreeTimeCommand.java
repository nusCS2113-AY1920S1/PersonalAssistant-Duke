package duke.logic.commands;

import duke.logic.commands.results.CommandResultText;
import duke.commons.exceptions.DukeException;
import duke.commons.Messages;
import duke.model.Model;
import duke.model.lists.EventList;

import java.time.LocalDateTime;

/**
 * Retrieves the number of free hours.
 */
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
     * Executes this command and returns a text result.
     *
     * @param model The model object containing event list.
     */
    @Override
    public CommandResultText execute(Model model) throws DukeException {
        EventList events = model.getSortedList();
        LocalDateTime result = findTime(events);

        throw new DukeException(Messages.ERROR_FILE_NOT_FOUND + "Write the code!");
    }

    private LocalDateTime findTime(EventList events) {
        return LocalDateTime.now();
    }
}
