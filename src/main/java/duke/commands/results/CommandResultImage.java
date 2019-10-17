package duke.commands.results;

import javafx.scene.image.Image;

public class CommandResultImage extends CommandResult {
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

    public Image getImage() {
        return image;
    }
}
