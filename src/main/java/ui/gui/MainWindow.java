package ui.gui;

import duke.exception.DukeException;
import interpreter.Interpreter;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import ui.UiCode;
import utils.InfoCapsule;

import java.util.ArrayList;

public class MainWindow extends AnchorPane {

    @FXML
    private AnchorPane mainPane;
    @FXML
    private AnchorPane headerPane;
    @FXML
    private AnchorPane contentPane;
    @FXML
    private ImageView headerBackground;
    @FXML
    private TextField userInput;

    private Boolean exitRequest;
    private ArrayList<String> userInputHistory;
    private Stage mainStage;
    private DisplayType displayType;
    private CommandLineDisplay cliController;
    private HomeWindow homeController;
    private Interpreter interpreterLayer;

    void initialize(Stage stage, String taskPath, String walletPath) {
        this.exitRequest = false;
        this.mainStage = stage;
        this.interpreterLayer = new Interpreter(taskPath, walletPath);
        this.displayType = DisplayType.NONE;
        this.userInputHistory = new ArrayList<>();

        this.fetchStoredImages();
        this.showHomeDisplay();
        this.refresh();
    }

    @FXML
    private void handleUserInput() {
        String input = this.userInput.getText();
        this.userInputHistory.add(input);
        InfoCapsule infoCapsule = this.interpreterLayer.interpret(input);
        this.updateGui(infoCapsule);
        if (this.displayType == DisplayType.HOME) {
            this.updateHomeDisplay();
            this.refresh(); // Javafx won't update completely
        }
        this.userInput.clear();
        if (this.exitRequest) {
            Platform.exit();
        }
    }

    /**
     * Set the Graphical User Interface to the Home Display.
     */
    private void showHomeDisplay() {
        if (this.displayType == DisplayType.HOME) {
            this.updateHomeDisplay();
            return;
        }
        try {
            FXMLLoader loaderHomeDisplay = new FXMLLoader(MainGui.class
                    .getResource("/view/HomeWindow.fxml"));
            AnchorPane homeDisplayRoot = loaderHomeDisplay.load();
            this.homeController = loaderHomeDisplay.<HomeWindow>getController();
            this.homeController.initialize(this.userInputHistory, this.interpreterLayer);
            this.contentPane.getChildren().add(homeDisplayRoot);
            this.displayType = DisplayType.HOME;
        } catch (DukeException e) {
            e.printStackTrace();
            this.displayToast(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            // Catch Error
        }
        if (this.contentPane.getChildren().size() > 1) {
            this.contentPane.getChildren().remove(0);
        }
    }

    private void updateHomeDisplay() {
        try {
            this.homeController.updateBalanceChart();
        } catch (DukeException e) {
            this.displayToast(e.getMessage());
        }
        try {
            this.homeController.displayTasks();
        } catch (DukeException e) {
            this.displayToast(e.getMessage());
        }
        try {
            this.homeController.updateBreakdownData();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateGui(InfoCapsule infoCapsule) {
        UiCode uiCode = infoCapsule.getUiCode();
        try {
            switch (uiCode) {
            case CLI:
                this.showCliDisplay();
                this.printToDisplay(infoCapsule.getOutputStr());
                break;
            case TOAST:
                this.displayToast(infoCapsule.getOutputStr());
                break;
            case ERROR:
                infoCapsule.throwError();
                break;
            case EXIT:
                this.exitRequest = true;
                break;
            case DISPLAY_HOME:
                this.showHomeDisplay();
                break;
            case DISPLAY_CLI:
                this.showCliDisplay();
                break;
            case CLEAR_CLI:
                this.cliController.clearCliDisplay();
                break;
            case TESTER:
                this.enableTesterMode();
                this.displayToast(infoCapsule.getOutputStr());
                break;
            case UPDATE:
                break;
            default:
            }
        } catch (DukeException e) {
            this.displayToast(e.getMessage());
        }
    }

    void saveAllData() {
        this.interpreterLayer.requestSave();
    }

    void refresh() {
        this.updateHomeDisplay();
    }

    /**
     * Fetches Images stored in application for display in slots for features yet to be developed.
     */
    @FXML
    private void fetchStoredImages() {
        Image headerBackgroundPic = new Image(this.getClass().getResourceAsStream("/images/headerBackground.png"));
        this.headerBackground.setImage(headerBackgroundPic);
    }

    /**
     * Set the Graphical User Interface to the CLI Display.
     */
    private void showCliDisplay() {
        if (this.displayType == DisplayType.CLI) {
            return;
        }
        try {
            FXMLLoader loaderCliDisplay = new FXMLLoader(MainGui.class
                .getResource("/view/CommandLineDisplay.fxml"));
            AnchorPane cliDisplayRoot = loaderCliDisplay.load();
            this.cliController = loaderCliDisplay.<CommandLineDisplay>getController();
            this.cliController.setStyle();
            this.contentPane.getChildren().add(cliDisplayRoot);
            this.displayType = DisplayType.CLI;
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        if (this.contentPane.getChildren().size() > 1) {
            this.contentPane.getChildren().remove(0);
        }
    }

    private void displayToast(String string) {
        Toast.makeText(this.mainStage, string);
    }

    private void dukeSays(String string) {
        this.showCliDisplay();
        this.cliController.dukeSays(string);
    }

    private void printToDisplay(String string) {
        this.showCliDisplay();
        this.cliController.printToDisplay(string);
        this.printSeparator();
    }

    private void printSeparator() {
        this.showCliDisplay();
        this.cliController.printSeparator();
    }

    private void enableTesterMode() {
        InfoCapsule infoCapsule = this.interpreterLayer.requestTesterData();
        this.showCliDisplay();
        this.showHomeDisplay();
        this.displayToast(infoCapsule.getOutputStr());
    }

}
