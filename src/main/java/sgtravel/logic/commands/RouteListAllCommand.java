package sgtravel.logic.commands;

import sgtravel.logic.commands.results.CommandResultText;
import sgtravel.model.Model;

/**
 * Lists all Routes in RouteList.
 */
public class RouteListAllCommand extends Command {
    /**
     * Executes this command on the given Route List and user interface.
     *
     * @param model The model object containing information about the user.
     * @return The CommandResultText.
     */
    @Override
    public CommandResultText execute(Model model) {
        return new CommandResultText(model.getRoutes());
    }
}
