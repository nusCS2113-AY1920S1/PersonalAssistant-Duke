package duke.logic.commands;

import duke.logic.commands.results.CommandResultCalender;
import duke.model.Model;

/**
 * Command to allow users to view their calender.
 */
public class ViewScheduleCommand extends Command {
    private static final String MESSAGE_SHOW_CALENDAR = "Calendar is launching...";

    /**
     * Executes this command and returns a calendar result.
     *
     * @param model The model object containing event list.
     */
    @Override
    public CommandResultCalender execute(Model model) {
        CommandResultCalender commandResult = new CommandResultCalender(MESSAGE_SHOW_CALENDAR);
        commandResult.setEvents(model.getEvents());
        return commandResult;
    }
}
