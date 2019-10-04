import DukeObjects.Expense;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;


public class MainWindow extends BorderPane{
    private Duke duke;
    @FXML
    public ListView<String> expenseList;
    @FXML
    public BorderPane main;
    @FXML
    public TextField input;
    @FXML
    public Label output;
    @FXML
    public void onEnter(javafx.event.ActionEvent actionEvent) {
        String userInput = input.getText();
        String response = duke.getResponse(userInput);
        output.setText(response);
        setExpensesList();
        input.clear();
    }


    public void setDuke(Duke duke) {
        this.duke = duke;
    }

    private void setExpensesList(){
        expenseList.getItems().clear();
        for(Expense expense : duke.getExpenseList().getExpenseList()){
            expenseList.getItems().add(expense.toString());
        }
    }
}

