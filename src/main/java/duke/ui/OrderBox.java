package duke.ui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;

import java.io.IOException;

public class OrderBox extends AnchorPane {
    @FXML
    private AnchorPane innerPane;
    @FXML
    private FlowPane itemFlow;

    public OrderBox() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("/view/OrderBox.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        itemFlow.getChildren().add(new OrderItemBox());
    }
}
