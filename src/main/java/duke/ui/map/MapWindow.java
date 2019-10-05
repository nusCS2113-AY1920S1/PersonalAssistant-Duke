package duke.ui.map;

import duke.data.Location;
import duke.ui.UiPart;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class MapWindow extends UiPart<Stage> {
    @FXML
    private AnchorPane map;

    private static final String FXML = "MapWindow.fxml";
    ObservableList<Location> locations = FXCollections.observableArrayList();

    private void generateNodes() {
        locations.add(new Location("sentosa", 1.2454983, 103.8437327, 0, 0));
        locations.add(new Location("mandai", 1.421336, 103.802622, 0, 0));
        locations.add(new Location("lck", 1.431321, 103.718253, 0, 0));
        locations.add(new Location("Jurong island", 1.254945, 103.678820, 0, 0));
        locations.add(new Location("Changi Airport", 1.346703, 103.986755, 0, 0));
        locations.add(new Location("Bukit Timah", 1.327360, 103.794509, 0, 0));
    }

    private void attachListener() {
        locations.addListener((ListChangeListener<Location>) c -> {
            map.getChildren().clear();
            for (Location location : locations) {
                map.getChildren().add(LocationCard.getCard(location));
            }
        });
    }

    /**
     * Creates a new MapWindow.
     *
     * @param root Stage to use as the root of the CalendarWindow.
     */
    private MapWindow(Stage root/*, Route routes*/) {
        super(FXML, root);
        root.getScene().getStylesheets().addAll(this.getClass().getResource("/css/mapStyle.css").toExternalForm());
        attachListener();
        generateNodes();
    }

    /**
     * Creates a new MapWindow.
     */
    public MapWindow(/*Route routes*/) {
        this(new Stage()/*, routes*/);
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
