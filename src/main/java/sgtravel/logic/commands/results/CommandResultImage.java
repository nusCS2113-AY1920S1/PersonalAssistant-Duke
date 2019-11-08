package sgtravel.logic.commands.results;

import sgtravel.model.locations.BusStop;
import sgtravel.model.locations.TrainStation;
import sgtravel.model.locations.Venue;
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
        this.message = message;
        int index = 1;
        for (Venue venue : venues) {
            appendNodeInformation(index, venue);
            index++;
        }
        this.image = image;
    }

    /**
     * Appends the RouteNode information to the result.
     *
     * @param index The index of the route.
     * @param venue The Venue object.
     */
    private void appendNodeInformation(int index, Venue venue) {
        if (venue instanceof BusStop) {
            this.message += "(" + index + ") " + ((BusStop) venue).getBusCode() + " " + venue.getAddress() + "\n";
        } else if (venue instanceof TrainStation) {
            this.message += "(" + index + ") " + ((TrainStation) venue).getDescription() + " Station\n";
        } else {
            this.message += "(" + index + ") " + venue.getAddress() + "\n";
        }
    }

    /**
     * Gets the Image.
     *
     * @return image The Image to show.
     */
    @Override
    public Image getImage() {
        return image;
    }

    /**
     * Gets the message.
     *
     * @return message The message in this object.
     */
    @Override
    public String getMessage() {
        return message;
    }
}
