package duke.ui;

import duke.model.order.Order;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.List;

public class OrderPage extends AnchorPane {
    @FXML
    private VBox orderList;

    public OrderPage() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("/view/OrderPage.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void refreshOrderList(List<Order> orders, List<Order> all) {
        orderList.getChildren().clear();
        int index = 1;
        for (Order order : orders) {
            orderList.getChildren().add(new OrderCard(order, index));
            index++;
        }
    }

}
