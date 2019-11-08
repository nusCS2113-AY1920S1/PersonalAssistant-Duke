package spinbox.gui.boxes;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import spinbox.entities.items.GradedComponent;
import spinbox.gui.MainWindow;

import java.io.IOException;

public class GradedComponentBox extends VBox {
    @FXML
    private Label gradedComponentDetails;

    private GradedComponentBox(GradedComponent gradedComponent, int index) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource(
                    "/view/itemBoxes/GradedComponentBox.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.setStyle("-fx-border-color: #FFF");
        this.setPadding(new Insets(10, 10, 10, 10));
        this.setSpacing(10.0);
        setMargin(this, new Insets(10, 10, 10, 10));
        this.gradedComponentDetails.setStyle("-fx-font-weight: bold");
        this.gradedComponentDetails.setTextFill(Color.WHITE);
        this.gradedComponentDetails.setText(index + ". " + gradedComponent.toString());
    }

    public static GradedComponentBox getGradedComponentsBox(GradedComponent gradedComponent, int index) {
        return new GradedComponentBox(gradedComponent, index);
    }
}
