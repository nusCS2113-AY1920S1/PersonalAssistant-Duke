package scene;

import dictionary.Bank;
import command.Command;
import exception.WordUpException;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import parser.Parser;
import ui.Ui;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import parser.Parser;
import storage.Storage;
import ui.Ui;

import java.util.ArrayList;
import java.util.List;

public abstract class NewScene {
    protected ScrollPane scrollPane;
    protected VBox dialogContainer;
    protected TextField userInput;
    protected Button sendButton;
    protected Scene scene;
    protected AnchorPane layout;
    protected Image user = new Image(this.getClass().getResourceAsStream("/images/girl.png"));
    protected Image duke = new Image(this.getClass().getResourceAsStream("/images/robot.png"));
    protected Ui ui;
    protected Bank bank;
    protected Storage storage;
    protected String greet;
    protected Stage window;
    protected List<String> inputHistory = new ArrayList<>();
    protected int inputIndex = 0;

    public NewScene() {
    }


    /**
     * Creates a new scene.
     * @param ui to interact with user
     * @param bank to store all words data
     * @param storage extracted file to store all data
     * @param greet greeting from bot to user when user moves to a new scene
     * @param window main window containing the scene
     */
    public NewScene(Ui ui, Bank bank, Storage storage, String greet, Stage window) {
        this.window = window;
        this.greet = greet;
        this.ui = ui;
        this.bank = bank;
        this.storage = storage;

        dialogContainer = new VBox();

        scrollPane = new ScrollPane();
        scrollPane.setContent(dialogContainer);

        userInput = new TextField();
        sendButton = new Button("Send");

        layout = new AnchorPane();
        layout.getChildren().addAll(scrollPane, userInput, sendButton);

        scene = new Scene(layout);

        layout.setPrefSize(400.0, 600.0);

        scrollPane.setPrefSize(400, 570);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);

        scrollPane.setVvalue(1.0);
        scrollPane.setFitToWidth(true);

        // You will need to import `javafx.scene.layout.Region` for this.
        dialogContainer.setPrefHeight(Region.USE_COMPUTED_SIZE);

        userInput.setPrefWidth(340.0);

        sendButton.setPrefWidth(50.0);

        AnchorPane.setTopAnchor(scrollPane, 1.0);

        AnchorPane.setBottomAnchor(sendButton, 1.0);
        AnchorPane.setRightAnchor(sendButton, 1.0);

        AnchorPane.setLeftAnchor(userInput, 1.0);
        AnchorPane.setBottomAnchor(userInput, 1.0);
        Label userText = new Label(greet);
        dialogContainer.getChildren().addAll(
                Box.getDukeDialog(userText, new ImageView(duke))
        );

        dialogContainer.heightProperty().addListener((observable) -> scrollPane.setVvalue(1.0));
    }

    private Label getDialogLabel(String text) {
        // You will need to import `javafx.scene.control.Label`.
        Label textToAdd = new Label(text);
        textToAdd.setWrapText(true);
        return textToAdd;
    }

    protected void setupHandleInput() {
        sendButton.setOnMouseClicked((event) -> {
            try {

                this.inputHistory.add(userInput.getText());
                this.inputIndex = this.inputHistory.size();
                handleUserInput();
            } catch (WordUpException e) {
                resolveException(e);
            }
        });

        userInput.setOnAction((event) -> {
            try {
                this.inputHistory.add(userInput.getText());
                this.inputIndex = this.inputHistory.size();
                handleUserInput();
            } catch (WordUpException e) {
                resolveException(e);
            }
        });

        userInput.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.UP) {
                if (inputIndex > 0) {
                    inputIndex -= 1;
                }
                if (inputHistory.size() != 0) {
                    userInput.setText(inputHistory.get(inputIndex));
                    userInput.positionCaret(inputHistory.get(inputIndex).length());
                }
            } else if (event.getCode() == KeyCode.DOWN) {
                if (inputIndex < inputHistory.size()) {
                    inputIndex += 1;
                }
                if (inputIndex == inputHistory.size()) {
                    userInput.setText("");
                } else {
                    userInput.setText(inputHistory.get(inputIndex));
                    userInput.positionCaret(inputHistory.get(inputIndex).length());
                }

            }
        });
    }

    protected void handleUserInput() throws WordUpException {
        Label userText = new Label(userInput.getText());
        Label dukeText = new Label(getResponse(userInput.getText()));
        dialogContainer.getChildren().addAll(
                Box.getUserDialog(userText, new ImageView(user)),
                Box.getDukeDialog(dukeText, new ImageView(duke))
        );
        userInput.clear();
    }

    protected void resolveException(WordUpException e) {
        return;
    }

    protected String getResponse(String userInput) throws WordUpException {
        Command c = Parser.parse(userInput);
        return c.execute(ui, bank, storage);
    }

    protected Scene getScene() {
        return scene;
    }
}
