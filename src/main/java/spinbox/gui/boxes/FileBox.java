package spinbox.gui.boxes;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import spinbox.entities.items.File;
import spinbox.gui.MainWindow;

import java.io.IOException;

public class FileBox extends VBox {
    @FXML
    private Label fileDetails;

    private FileBox(File file) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("/view/itemBoxes/FileBox.fxml"));
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
        this.fileDetails.setStyle("-fx-font-weight: bold");
        this.fileDetails.setTextFill(Color.WHITE);


        this.fileDetails.setText(file.toString());
    }

    public static FileBox getFileBox(File file) {
        return new FileBox(file);
    }
}
