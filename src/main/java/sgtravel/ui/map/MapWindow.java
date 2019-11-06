package sgtravel.ui.map;

import sgtravel.logic.commands.results.CommandResultMap;
import sgtravel.model.locations.RouteNode;
import sgtravel.model.locations.Venue;
import sgtravel.ui.UiPart;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a generic Map Window in the UI.
 */
public class MapWindow extends UiPart<Stage> {
    private static final String FXML = "MapWindow.fxml";
    private List<Venue> locations = new ArrayList<>();

    @FXML
    private AnchorPane map;

    /**
     * Creates a new MapWindow.
     *
     * @param root Stage to use as the root of the CalendarWindow.
     * @param routeNodeList The List of RouteNodes.
     */
    private MapWindow(Stage root, List<RouteNode> routeNodeList) {
        super(FXML, root);
        root.getScene().getStylesheets().addAll(this.getClass().getResource("/css/mapStyle.css").toExternalForm());
        generateNodes(routeNodeList);
    }

    /**
     * Creates a new MapWindow.
     *
     * @param commandResultMap The CommandResult that contains a Route.
     */
    public MapWindow(CommandResultMap commandResultMap) {
        this(new Stage(), commandResultMap.getRoute());
    }

    /**
     * Generates the RouteNodes in a Route for the MapWindow.
     *
     * @param routeNodeList The List of RouteNodes.
     */
    private void generateNodes(List<RouteNode> routeNodeList) {
        locations.addAll(routeNodeList);
        map.getChildren().clear();
        int index = 0;
        String id = "";
        for (Venue location : locations) {
            if (index == 0) {
                id = "RouteNodeStart";
            } else if (index == locations.size() - 1) {
                id = "RouteNodeEnd";
            } else {
                id = "RouteNodeIntermediate";
            }
            map.getChildren().add(LocationCard.getCard(location, id));
            index++;
        }
    }

    /**
     * Shows the Map window.
     * @throws IllegalStateException
     * <ul>
     *     <li>
     *         if this method is called on a thread other than the JavaFX Application Thread.
     *     </li>
     *     <li>
     *         if this method is called during animation or layout processing.
     *     </li>
     *     <li>
     *         if this method is called on the primary stage.
     *     </li>
     *     <li>
     *         if {@code dialogStage} is already showing.
     *     </li>
     * </ul>
     */
    public void show() {
        getRoot().show();
    }

    /**
     * Returns true if the Map window is currently being shown.
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }

    /**
     * Focuses on the Map window.
     */
    public void focus() {
        getRoot().requestFocus();
    }
}
