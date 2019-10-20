package duke.ui;

import duke.model.events.BindableEvent;
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
    private BindableEvent event;

    public EventPointCard(BindableEvent event) {
        super(FXML);
        this.event = event;
        setLocation();
    }

    public void setColor(Paint paint) {
        circle.setFill(paint);
    }

    private void setLocation() {
        double offsetY = 300 - ((event.getLatitude() - 1.218) * 300 / (1.486 - 1.218));
        double offsetX = (event.getLongitude() - 103.622) * 400  / (104.021 - 103.622);
        AnchorPane.setLeftAnchor(getRoot(), offsetX);
        AnchorPane.setTopAnchor(getRoot(), offsetY);
        Tooltip tooltip = new Tooltip(event.getAddress());
        Tooltip.install(getRoot(), tooltip);
    }
}
