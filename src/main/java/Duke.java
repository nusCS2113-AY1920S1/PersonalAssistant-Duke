import javafx.application.Application;
import exceptions.DukeException;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import views.CLIView;

public class Duke extends Application {
    /**
     * Main class.
     *
     * @param args Refers to CLI arguments
     */

    private ScrollPane scrollPane;
    private VBox dialogContainer;
    private TextField userInput;
    private Button sendButton;
    private Scene scene;

    public static void main(String[] args) {
        CLIView cliView = new CLIView();
        cliView.start();
    }

    @Override
    public void start(Stage stage) {

        //The container for content of chat to scroll
        this.scrollPane = new ScrollPane();
        this.dialogContainer = new VBox();
        this.scrollPane.setContent(dialogContainer);

        this.userInput = new TextField();
        this.sendButton = new Button("Send");

        AnchorPane mainLayout = new AnchorPane();
        mainLayout.getChildren().addAll(scrollPane, userInput, sendButton);
        this.scene = new Scene(mainLayout);

        //Formatting the layout
        stage.setTitle("ArchDuke");
        stage.setResizable(false);
        stage.setMinHeight(600.0);
        stage.setMinWidth(400.0);
        mainLayout.setPrefSize(400.0,600.0);

        scrollPane.setPrefSize(385, 535);
        //scrollPane.setHbarPolicy(ScrollPane.);



        stage.setScene(this.scene); // Setting the stage to show our screen
        stage.show(); // Render the stage.
    }

}
