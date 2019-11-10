package entertainment.pro.logic.parsers.commands;

import entertainment.pro.commons.enums.CommandKeys;
import entertainment.pro.commons.exceptions.Exceptions;
import entertainment.pro.logic.parsers.CommandStructure;
import entertainment.pro.logic.parsers.CommandSuper;
import entertainment.pro.model.CinemaInfoObject;
import entertainment.pro.ui.Controller;
import entertainment.pro.ui.MovieHandler;

import java.util.ArrayList;

public class FindCommand extends CommandSuper {

    public static final int CONST = 5;

    /**
     * Constructor for each Command Super class.
     *
     * @param uicontroller UI controller for javafx
     */
    public FindCommand(Controller uicontroller) {
        super(CommandKeys.FIND, CommandStructure.cmdStructure.get(CommandKeys.FIND), uicontroller);
    }

    /**
     * Function to execute commands depending on the subroot command.
     */
    @Override
    public void executeCommands() {
        switch (this.getSubRootCommand()) {
        case CINEMA:
            try {
                findCinemas();
            } catch (Exceptions exceptions) {
                exceptions.printStackTrace();
            }
            break;
        default:
            break;
        }
    }

    /**
     * find cinemas near a particular location in the payload and formats them to be displayed on the UI.
     */
    public void findCinemas() throws Exceptions {
        String display = "Here is the list of cinemas near " + getPayload() + ": \n\n";
        ArrayList<CinemaInfoObject> cinemas =
                ((MovieHandler) this.getUiController()).getCinemaAPIRequester().searchNearestCinemas(getPayload());
        int length = CONST;
        if (cinemas.size() < CONST) {
            length = cinemas.size();
        }
        if (length > 0) {
            for (int i = 0; i < length; i++) {
                display += i + 1 + ". Name: " + cinemas.get(i).getName() + " [Rating: "
                        + cinemas.get(i).getRating() + "]\n";
                display += "    Address: " + cinemas.get(i).getAddress() + "\n";
                display += "\n";
            }
        } else {
            display = "no cinemas in this location, please try a different one";
        }
        ((MovieHandler) this.getUiController()).clearSearchTextField();
        ((MovieHandler) this.getUiController()).setGeneralFeedbackText(display);
    }
}
