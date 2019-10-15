package duke.GUI;

import duke.Duke;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
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

    @Override
    public void initialize(final URL location,
                           final ResourceBundle resources) {
        System.out.println("View is now loaded!");
    }

    /**
     * Method will return to menu when button is pressed.
     *
     * @param event Will get the active instance of the button.
     */
    @FXML
    public void viewMenu(ActionEvent event) {
        try {
            URL url = Duke.class.getClassLoader()
                .getResource("view/menu.fxml");
            System.out.println(url);
            assert url != null;
            Parent root = FXMLLoader.load(url);

            //get current stage information
            Stage stage = (Stage) ((Node) event
                .getSource()).getScene().getWindow();

            //Change scene
            stage.setScene(new Scene(root, width, height));
            stage.setTitle("Sports Manager");
            stage.setResizable(false);
            stage.show();
            System.out.println("View menu");
        } catch (IOException e) {
            System.err.println("Unable to open menu.fxml");
        }
    }

    /**
     * Method will submit when the submit button is pressed.
     */
    public void handleSubmit() {
        System.out.println("Submit!");
    }

    /**
     * When this method is called, the training scene will be called.
     *
     * @param event Will get the active instance of the button.
     */
    public void viewTraining(final ActionEvent event) {
        try {
            URL url = Duke.class.getClassLoader()
                .getResource("view/training.fxml");
            System.out.println(url);
            assert url != null;
            Parent root = FXMLLoader.load(url);

            //get current stage information
            Stage stage = (Stage) ((Node) event
                .getSource()).getScene().getWindow();

            //Change scene
            stage.setScene(new Scene(root, width, height));
            stage.setTitle("Sports Manager");
            stage.setResizable(false);
            stage.show();
            System.out.println("View Training");
        } catch (IOException e) {
            System.err.println("Unable to open training.fxml");
        }
    }

    /**
     * When this method is called, the schedule scene will be called.
     *
     * @param event Will get the active instance of the button.
     */
    public void viewSchedule(final ActionEvent event) {
        try {
            URL url = Duke.class.getClassLoader()
                .getResource("view/schedule.fxml");
            System.out.println(url);
            assert url != null;
            Parent root = FXMLLoader.load(url);

            //get current stage information
            Stage stage = (Stage) ((Node) event.getSource())
                .getScene().getWindow();

            //Change scene
            stage.setScene(new Scene(root, width, height));
            stage.setTitle("Sports Manager");
            stage.setResizable(false);
            stage.show();
            System.out.println("View Schedule");
        } catch (IOException e) {
            System.err.println("Unable to open schedule.fxml");
        }
    }

    /**
     * When this method is called, the student scene will be called.
     *
     * @param event Will get the active instance of the button.
     */
    public void viewStudents(final ActionEvent event) {
        try {
            URL url = Duke.class.getClassLoader()
                .getResource("view/student.fxml");
            System.out.println(url);
            assert url != null;
            Parent root = FXMLLoader.load(url);

            //get current stage information
            Stage stage = (Stage) ((Node) event.getSource())
                .getScene().getWindow();

            //Change scene
            stage.setScene(new Scene(root, width, height));
            stage.setTitle("Sports Manager");
            stage.setResizable(false);
            stage.show();
            System.out.println("View Schedule");
        } catch (IOException e) {
            System.err.println("Unable to open student.fxml");
        }
    }

}
