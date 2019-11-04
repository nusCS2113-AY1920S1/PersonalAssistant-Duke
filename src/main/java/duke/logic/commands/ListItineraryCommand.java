package duke.logic.commands;

import duke.logic.commands.results.CommandResultText;
import duke.model.Model;
import duke.model.planning.Itinerary;

import java.util.HashMap;

/**
 * Lists the stored itineraries names an serial number.
 */
public class ListItineraryCommand extends Command {

    /**
     * Executes this command on the given task list and user interface.
     *
     * @param model The model object containing information about the user.
     */
    @Override
    public CommandResultText execute(Model model) {
        StringBuilder stringBuilder = new StringBuilder();
        HashMap<String, Itinerary> itineraryHashMap = model.getItineraryTable();
        int i = 1;
        for (String name : itineraryHashMap.keySet()) {
            stringBuilder.append(i++).append(". ").append(name).append("\n");
        }
        return new CommandResultText("Your Saved Itineraries are :" + "\n" + stringBuilder.toString());
    }
}
