package duke.ui.map;

import duke.model.locations.Venue;
import duke.ui.UiPart;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

public class LocationCard extends UiPart<StackPane> {
    private static final String FXML = "LocationCard.fxml";

    private LocationCard(Venue location) {
        super(FXML);
        double offsetY = 600 - ((location.getLatitude() - 1.218) * 600 / (1.486 - 1.218));
        double offsetX = (location.getLongitude() - 103.622) * 800  / (104.021 - 103.622);
        AnchorPane.setLeftAnchor(getRoot(), offsetX);
        AnchorPane.setTopAnchor(getRoot(), offsetY);
        Tooltip tooltip = new Tooltip(location.getAddress());
        Tooltip.install(getRoot(), tooltip);
    }

    public static StackPane getCard(Venue location) {
        return new LocationCard(location).getRoot();
    }
}
