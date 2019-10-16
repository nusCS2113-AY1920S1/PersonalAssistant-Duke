package duke.gui;

import duke.Duke;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public final class Controller implements Initializable {

    /**
     * Width of scene.
     */
    private final int width = 1280;
    /**
     * Height of scene.
     */
    private final int height = 720;
    public Button btnDay;
    public Button btnHome;
    public Button btnSchedule;
    public Button btnStudents;
    public Button btnTraining;

    @Override
    public void initialize(final URL location,
                           final ResourceBundle resources) {
        System.out.println("View is now loaded!");
    }

    /**
     * Method will submit when the submit button is pressed.
     */
    @FXML
    public void handleSubmit() {
        System.out.println("Submit!");
    }

    /**
     * When this method is called, the menu scene will be called.
     *
     * @param event Will get the active instance of the button.
     */
    public void viewMenu(final ActionEvent event) {
        URL url = Duke.class.getClassLoader()
            .getResource("view/menu.fxml");
        System.out.println(url);
        assert url != null;
        changeScene(url, event);
    }


    /**
     * When this method is called, the student scene will be called.
     *
     * @param event Will get the active instance of the button.
     */
    public void viewStudents(final ActionEvent event) {
        URL url = Duke.class.getClassLoader()
            .getResource("view/student.fxml");
        System.out.println(url);
        assert url != null;
        changeScene(url, event);
    }

    /**
     * When this method is called, the training scene will be called.
     *
     * @param event Will get the active instance of the button.
     */
    public void viewTraining(final ActionEvent event) {
        URL url = Duke.class.getClassLoader()
            .getResource("view/training.fxml");
        System.out.println(url);
        assert url != null;
        changeScene(url, event);
    }

    /**
     * When this method is called, the schedule scene will be called.
     *
     * @param event Will get the active instance of the button.
     */
    public void viewSchedule(final ActionEvent event) {
        URL url = Duke.class.getClassLoader()
            .getResource("view/schedule.fxml");
        System.out.println(url);
        assert url != null;
        changeScene(url, event);
    }

    /**
     * When this method is called, the day scene will be called.
     *
     * @param event Will get the active instance of the button.
     */
    public void viewDay(final ActionEvent event) {
        URL url = Duke.class.getClassLoader()
            .getResource("view/daily.fxml");
        System.out.println(url);
        assert url != null;
        changeScene(url, event);
    }



    /**
     * Method will change the scene of the current stage.
     *
     * @param url   Directory of the fxml file to be loaded
     * @param event The event handled by the button
     */
    public void changeScene(final URL url, final ActionEvent event) {
        try {

            Parent root = FXMLLoader.load(url);

            //get current stage information
            Stage stage = (Stage) ((Node) event.getSource())
                .getScene().getWindow();

            //Change scene
            stage.setScene(new Scene(root, width, height));
            stage.setTitle("Sports Manager");
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            System.err.println("Unable to open " + url);
        }
    }


}
