package duke.ui;

import duke.model.locations.Venue;
import javafx.fxml.FXML;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

public class EventPointCard extends UiPart<StackPane> {
    @FXML
    private Circle circle;

    private static final String FXML = "EventPointCard.fxml";
    private Venue v;

    public EventPointCard(Venue v) {
        super(FXML);
        this.v = v;
        setLocation();
    }

    public void setColor(Paint paint) {
        circle.setFill(paint);
    }

    private void setLocation() {
        double offsetY = 300 - ((v.getLatitude() - 1.218) * 300 / (1.486 - 1.218));
        double offsetX = (v.getLongitude() - 103.622) * 400  / (104.021 - 103.622);
        AnchorPane.setLeftAnchor(getRoot(), offsetX);
        AnchorPane.setTopAnchor(getRoot(), offsetY);
        Tooltip tooltip = new Tooltip(v.getAddress());
        Tooltip.install(getRoot(), tooltip);
    }
}
