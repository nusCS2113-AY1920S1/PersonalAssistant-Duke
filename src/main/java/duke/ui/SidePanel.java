package duke.ui;

import duke.logic.commands.results.PanelResult;
import duke.ui.map.PointCard;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

/**
 * Represents the side panel of the main window.
 */
public class SidePanel extends UiPart<AnchorPane> {
    @FXML
    private StackPane taskContainer;
    @FXML
    private AnchorPane miniMap;
    @FXML
    private Label description;
    @FXML
    private Label startDate;
    @FXML
    private Label endDate;

    private static final String FXML = "SidePanel.fxml";

    private SidePanel(PanelResult result) {
        super(FXML);
        if (result.isReady()) {
            for (int i = 0; i < result.size(); ++i) {
                miniMap.getChildren().add(
                        PointCard.getCard(result.getVenue(i), result.getVenueColor(i)));
            }
            description.setText(result.getDescription());
            startDate.setText(result.getStartDate());
            endDate.setText(result.getEndDate());
            setHighlight(result);
        }
    }

    private void setHighlight(PanelResult result) {
        switch (result.getField()) {
        case 0:
            description.getStyleClass().add("highlight");
            break;
        case 1:
            startDate.getStyleClass().add("highlight");
            break;
        case 2:
            endDate.getStyleClass().add("highlight");
            break;
        default:
        }
    }

    public static AnchorPane getPanel(PanelResult result) {
        return new SidePanel(result).getRoot();
    }
}
