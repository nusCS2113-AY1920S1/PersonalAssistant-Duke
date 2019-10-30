package duke.ui.window;

import duke.ui.UiElement;
import javafx.scene.layout.Region;

public class HelpWindow extends UiElement<Region> {
    private static final String FXML = "HelpWindow.fxml";


    public HelpWindow() {
        super(FXML, null);
    }
}
