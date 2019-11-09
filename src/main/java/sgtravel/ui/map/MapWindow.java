package sgtravel.ui.map;

import javafx.geometry.Rectangle2D;
import javafx.scene.layout.StackPane;
import javafx.stage.Screen;
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
    private static final double MAP_WINDOW_SCALING = 0.667;
    private static final double MAP_IMAGE_ASPECT_RATIO = 1.47154;
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
        setSize();
    }

    /**
     * Creates a new MapWindow.
     *
     * @param commandResultMap The CommandResult that contains a Route.
     */
    public MapWindow(CommandResultMap commandResultMap) {
        this(new Stage(), commandResultMap.getRoutes());
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
        for (Venue location : locations) {
            StackPane locationCard = LocationCard.getCard(location, getLocationCardId(index));
            map.getChildren().add(locationCard);
            index++;
        }
    }

    /**
     * Gets the ID for a LocationCard, based on the index.
     *
     * @param index The index of the Location.
     * @return The ID to set.
     */
    private String getLocationCardId(int index) {
        if (index == 0) {
            return "RouteNodeStart";
        } else if (index == locations.size() - 1) {
            return "RouteNodeEnd";
        } else {
            return "RouteNodeIntermediate";
        }
    }

    /**
     * Shows the Map window.
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

    /**
     * Sets the size of the MapWindow.
     */
    private void setSize() {
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        double windowHeight = primaryScreenBounds.getHeight() * MAP_WINDOW_SCALING;
        map.setMaxHeight(windowHeight);
        map.setMaxWidth(windowHeight * MAP_IMAGE_ASPECT_RATIO);
    }
}
