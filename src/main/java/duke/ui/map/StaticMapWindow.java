package duke.ui.map;

import duke.ui.UiPart;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class StaticMapWindow extends UiPart<Stage> {
    @FXML
    private AnchorPane map;

    private static final String FXML = "StaticMapWindow.fxml";

    /**
     * Creates a new MapWindow.
     *
     * @param root Stage to use as the root of the CalendarWindow.
     */
    private StaticMapWindow(Stage root, Image image) {
        super(FXML, root);
        root.getScene().getStylesheets().addAll(
                this.getClass().getResource("/css/staticMapStyle.css").toExternalForm());
        ImageView iv1 = new ImageView();
        iv1.setImage(image);
        map.getChildren().add(iv1);
    }

    /**
     * Creates a new MapWindow.
     */
    public StaticMapWindow(Image image) {
        this(new Stage(), image);
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
