package duke.logic.commands;

import duke.logic.commands.results.CommandResultCalender;
import duke.commons.exceptions.DukeException;
import duke.model.Model;

public class ViewScheduleCommand extends Command {
    private static final String MESSAGE_SHOW_CALENDAR = "Calendar is launching...";

    /**
     * Executes this command on the given task list and user interface.
     *
     * @param model The model object containing information about the user.
     */
    @Override
    public CommandResultCalender execute(Model model) throws DukeException {
        CommandResultCalender commandResult = new CommandResultCalender(MESSAGE_SHOW_CALENDAR);
        commandResult.setEvents(model.getEventList());
        return commandResult;
    }
}
