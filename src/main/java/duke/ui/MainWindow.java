package duke.ui;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import duke.logic.Duke;
import duke.order.Order;
import duke.task.TaskList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.util.List;

public class MainWindow extends AnchorPane {

    private Duke duke;
    private Ui ui;
    @FXML
    private AnchorPane orderPane;
    @FXML
    private HBox popUp;
    @FXML
    private Label popUpLabel;
    @FXML
    JFXButton popUpButton;
    @FXML
    private TextField userInput;
    @FXML
    private JFXListView<DialogBox> taskList;
    @FXML
    private VBox orderList;


    @FXML
    public void initialize() {
        Ui ui = new Ui(this);
        duke = new Duke(ui);
    }

    @FXML
    private void handleUserInput() {
        popUp.setVisible(false);
        String input = userInput.getText();
        duke.executeInput(input);
        userInput.clear();
    }

    @FXML
    private void handleOk() {
        popUp.setVisible(false);
    }

    void showMessage(String message) {
        popUpLabel.setText(message);
        popUpLabel.setTextFill(Color.valueOf("#000000"));
        popUpButton.getStyleClass().clear();
        popUpButton.getStyleClass().add("message-popup");
        popUp.getStyleClass().clear();
        popUp.getStyleClass().add("message-popup");
        popUp.setVisible(true);
    }

    void showErrorPopUp(String message) {
        popUpLabel.setText(message);
        popUpLabel.setTextFill(Color.valueOf("#ffffff"));
        popUpButton.getStyleClass().clear();
        popUpButton.getStyleClass().add("error-popup");
        popUp.getStyleClass().clear();
        popUp.getStyleClass().add("error-popup");
        popUp.setVisible(true);
    }

    void refreshTaskList(TaskList tasks, TaskList all) {
        taskList.getItems().clear();
        for (int i = 0; i < tasks.size(); i++) {
            taskList.getItems().add(new DialogBox(tasks.get(i), all));
        }
    }

    void refreshOrderList(List<Order> orders, List<Order> all) {
        orderList.getChildren().clear();
        for (Order order : orders) {
            orderList.getChildren().add(new OrderBox(order));
        }
    }

    void showOrderPane() {
        orderPane.setVisible(true);
    }

    void disableInput() {
        userInput.setDisable(true);
    }
}
