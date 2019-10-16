package duke.ui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class OrderItemBox extends AnchorPane {
    @FXML
    private Label itemName;
    @FXML
    private Label itemQuantity;

    public OrderItemBox(String name, double quantity) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("/view/OrderItemBox.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        itemName.setText(name);
        itemQuantity.setText(Double.toString(quantity));
    }
}
