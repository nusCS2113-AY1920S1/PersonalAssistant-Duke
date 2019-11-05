package duke.logic.commands.results;

import duke.model.locations.BusStop;
import duke.model.locations.TrainStation;
import duke.model.locations.Venue;
import javafx.scene.image.Image;

import java.util.ArrayList;

/**
 * Defines the command result of any command containing an image.
 */
public class CommandResultImage extends CommandResult implements Imageable {
    private String message;
    private Image image = null;

    /**
     * Constructs a basic CommandResultImage object.
     *
     * @param message Message for ui to display.
     */
    public CommandResultImage(String message, Image image) {
        this.message = message;
        if (image != null) {
            this.image = image;
        }
    }

    /**
     * Alternative constructor that helps to create text for an ArrayList of Venue.
     *
     * @param message The message to show at the top.
     * @param venues The ArrayList of Venue.
     */
    public CommandResultImage(Image image, String message, ArrayList<Venue> venues) {
        String result = message;
        int index = 1;
        for (Venue venue : venues) {
            if (venue instanceof BusStop) {
                result += "(" + index + ") " + ((BusStop) venue).getBusCode() + " " + venue.getAddress() + "\n";
            } else if (venue instanceof TrainStation) {
                result += "(" + index + ") " + ((TrainStation) venue).getDescription() + " Station\n";
            } else {
                result += "(" + index + ") " + venue.getAddress() + "\n";
            }
            index++;
        }
        this.message = result;
        this.image = image;
    }

    @Override
    public Image getImage() {
        return image;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
