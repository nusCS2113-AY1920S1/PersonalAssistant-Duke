package duke.GUI;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("View is now loaded!");
    }

    @FXML
    public void returnToMenu () {
        System.out.println("OK");
    }

    public void handleSubmit () {
        System.out.println("Submit!");
    }

}