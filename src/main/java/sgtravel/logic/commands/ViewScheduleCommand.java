package sgtravel.logic.commands;

import sgtravel.commons.Messages;
import sgtravel.logic.commands.results.CommandResultCalender;
import sgtravel.model.Model;

/**
 * Command to allow users to view their calender.
 */
public class ViewScheduleCommand extends Command {
    /**
     * Executes this command and returns a calendar result.
     *
     * @param model The model object containing event list.
     */
    @Override
    public CommandResultCalender execute(Model model) {
        CommandResultCalender commandResult = new CommandResultCalender(Messages.VIEW_SCHEDULE_SUCCESS);
        commandResult.setEvents(model.getEvents());
        return commandResult;
    }
}
