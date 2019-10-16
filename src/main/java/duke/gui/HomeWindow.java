package duke.gui;

import com.jfoenix.controls.JFXMasonryPane;
import com.jfoenix.controls.JFXScrollPane;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Region;

import java.util.Random;

/**
 * UI window for the Home context.
 */
public class HomeWindow extends UiComponent<Region> {
    private static final String FXML = "HomeTab.fxml";

    @FXML
    JFXMasonryPane patientsListView;
    @FXML
    ScrollPane scrollPane;

    /**
     * Construct HomeWindow object.
     */
    public HomeWindow() {
        super(FXML);

        JFXScrollPane.smoothScrolling(scrollPane);

        for (int i = 0; i < 200; ++i) {
            Label lbl = new Label();
            lbl.setPrefSize(150, 150);
            lbl.setStyle("-fx-background-color:rgb(" + new Random().nextInt(255) + ", "
                    + new Random().nextInt(255) + ", " + new Random().nextInt(255) + ")");
            patientsListView.getChildren().add(lbl);
        }
    }
}
