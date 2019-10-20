package duke.ui.map;

import duke.logic.commands.results.CommandResultMap;
import duke.model.locations.BusStop;
import duke.model.locations.Venue;
import duke.ui.UiPart;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.util.List;

public class MapWindow extends UiPart<Stage> {
    @FXML
    private AnchorPane map;

    private static final String FXML = "MapWindow.fxml";
    private ObservableList<Venue> locations = FXCollections.observableArrayList();

    private void generateNodes(List<Venue> routes) {
        locations.addAll(routes);
    }

    private void attachListener() {
        locations.addListener((ListChangeListener<Venue>) c -> {
            map.getChildren().clear();
            for (Venue location : locations) {
                map.getChildren().add(LocationCard.getCard(location));
            }
        });
    }

    /**
     * Creates a new MapWindow.
     *
     * @param root Stage to use as the root of the CalendarWindow.
     */
    private MapWindow(Stage root, List<Venue> routes) {
        super(FXML, root);
        root.getScene().getStylesheets().addAll(this.getClass().getResource("/css/mapStyle.css").toExternalForm());
        attachListener();
        generateNodes(routes);
    }

    /**
     * Creates a new MapWindow.
     */
    public MapWindow(CommandResultMap commandResult) {
        this(new Stage(), commandResult.getRoute());
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
