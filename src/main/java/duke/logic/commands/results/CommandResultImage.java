package duke.logic.commands.results;

import javafx.scene.image.Image;

/**
 * Defines the command result of any command containing an image.
 */
public class CommandResultImage extends CommandResult implements Imageable {
    private String message;
    private Image image;

    /**
     * Constructs a basic CommandResultImage object.
     *
     * @param message Message for ui to display.
     */
    public CommandResultImage(String message, Image image) {
        this.message = message;
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
