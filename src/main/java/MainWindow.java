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
    public TextField inputField;
    @FXML
    public Label lastCommandLabel;
    @FXML
    public void onEnter() {
        String userInput = inputField.getText();
        String response = duke.getResponse(userInput);
        lastCommandLabel.setText(response);
        setExpensesList();
        inputField.clear();
    }


    public void setDuke(Duke d) {
        this.duke = d;
    }

    private void setExpensesList(){
        expenseList.getItems().clear();
        for(Expense expense : duke.getExpenseList().getExpenseList()){
            expenseList.getItems().add(expense.toString());
        }
    }
}

