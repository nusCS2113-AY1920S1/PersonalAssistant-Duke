//import Duke.Commands.Command;
//import Duke.Exception.DukeException;
//import Duke.Parser.Parser;
//import javafx.application.Application;
//import javafx.scene.Scene;
//import javafx.scene.control.Button;
//import javafx.scene.control.Label;
//import javafx.scene.control.ScrollPane;
//import javafx.scene.control.TextField;
//import javafx.scene.layout.AnchorPane;
//import javafx.scene.layout.Region;
//import javafx.scene.layout.VBox;
//import javafx.stage.Stage;
//
//import java.io.File;
//
///**
// * A launcher class to workaround classpath issues.
// */
//public class Launcher extends Application {
//    private ScrollPane scrollPane;
//    private VBox dialogContainer;
//    private TextField userInput;
//    private Button sendButton;
//    private Scene scene;
//
//    public static void main(String[] args) {
//        launch(args);
//    }
//
//    @Override
//    public void start(Stage stage) {
//        File currentDir = new File(System.getProperty("user.dir"));
//        File filePath = new File(currentDir.toString() + "\\src\\main\\data\\duke.txt");
//        Duke duke = new Duke(filePath);
//
//        scrollPane = new ScrollPane();
//        dialogContainer = new VBox();
//        scrollPane.setContent(dialogContainer);
//
//        userInput = new TextField();
//        sendButton = new Button("Send");
//
//        AnchorPane mainLayout = new AnchorPane();
//        mainLayout.getChildren().addAll(scrollPane, userInput, sendButton);
//
//        scene = new Scene(mainLayout);
//
//        stage.setTitle("Duke");
//        stage.setResizable(false);
//        stage.setMinHeight(600.0);
//        stage.setMinWidth(400.0);
//
//        mainLayout.setPrefSize(400.0, 600.0);
//
//        scrollPane.setPrefSize(385, 535);
//        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
//        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
//
//        scrollPane.setVvalue(1.0);
//        scrollPane.setFitToWidth(true);
//
//        // You will need to import `javafx.scene.layout.Region` for this.
//        dialogContainer.setPrefHeight(Region.USE_COMPUTED_SIZE);
//
//        userInput.setPrefWidth(325.0);
//
//        sendButton.setPrefWidth(55.0);
//
//        AnchorPane.setTopAnchor(scrollPane, 1.0);
//
//        AnchorPane.setBottomAnchor(sendButton, 1.0);
//        AnchorPane.setRightAnchor(sendButton, 1.0);
//
//        AnchorPane.setLeftAnchor(userInput , 1.0);
//        AnchorPane.setBottomAnchor(userInput, 1.0);
//
//        stage.setScene(scene); // Setting the stage to show our screen
//        stage.show(); // Render the stage.
//
//        sendButton.setOnMouseClicked((event) -> {
//            handleUserInput(duke);
//        });
//
//        userInput.setOnAction((event) -> {
//            handleUserInput(duke);
//        });
//
//        dialogContainer.heightProperty().addListener((observable) -> scrollPane.setVvalue(1.0));
//    }
//
//    private void handleUserInput(Duke duke){
//        try {
//            Command c = Parser.parse(userInput.getText());
//            c.execute(duke.tasks, duke.ui, duke.storage);
//        } catch (DukeException e) {
//            duke.ui.setMessage(e.getMessage());
//        } catch (NumberFormatException e) {
//            duke.ui.setMessage("     Invalid Command\n");
//        }
//        Label userText = new Label(userInput.getText());
//        Label dukeText = new Label(duke.ui.showLine());
//        dialogContainer.getChildren().addAll(userText, dukeText);
//        userInput.clear();
//    }
//
//    private String getResponse(String input) {
//        return "Duke heard: " + input;
//    }
//}