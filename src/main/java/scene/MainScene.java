package scene;

import command.SetReminderCommand;
import dictionary.Bank;
import command.Command;
import command.QuizCommand;
import exception.ChangeSceneException;
import exception.ReminderSetupException;
import exception.ReminderWordListEmptyException;
import exception.WordUpException;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import parser.Parser;
import storage.Storage;
import ui.Ui;

public class MainScene extends NewScene {

    protected int reminderSetUpState = 1;
    protected boolean isSettingUpReminder = false;

    public MainScene(Ui ui, Bank bank, Storage storage, Stage window) {
        super(ui, bank, storage, ui.greet(), window);
        setupHandleInput();
    }

    @Override
    public Scene getScene() {
        return scene;
    }

    @Override
    public void resolveException(WordUpException e) {
        if (e instanceof ChangeSceneException) {
            window.setScene(new QuizScene(ui, bank, storage, window).getScene());
        } else if (e instanceof ReminderSetupException) {
            isSettingUpReminder = false;
            reminderSetUpState = 1;
            displaySystemResponse(e.showError());
        }
    }

    /**
     * Displays a message from WordUp to the user on JavaFX.
     * @param s the system message to be displayed
     */
    public void displaySystemResponse(String s) {
        Label response = new Label(s);

        dialogContainer.getChildren().addAll(
                Box.getDukeDialog(response, new ImageView(duke))
        );
    }

    @Override
    protected void handleUserInput() throws WordUpException {
        Label userText = new Label(userInput.getText());
        Label dukeText = new Label(getResponse(userInput.getText()));

        dialogContainer.getChildren().addAll(
                Box.getUserDialog(userText, new ImageView(user)),
                Box.getDukeDialog(dukeText, new ImageView(duke))
        );
        userInput.clear();
    }

    @Override
    public String getResponse(String userInput) throws ReminderSetupException, ChangeSceneException {
        Command c;
        if (isSettingUpReminder) {
            if (reminderSetUpState == 1) {
                reminderSetUpState = 2;                                 //execute step 2
                if (userInput.trim().equals("")) {
                    isSettingUpReminder = false;
                    throw new ReminderWordListEmptyException();
                } else {
                    c = new SetReminderCommand(reminderSetUpState, userInput);
                }
            } else if (reminderSetUpState == 2) {                       //when user is entering a list of words
                if (userInput.trim().equals("")) {                      //user enters a blank line; end of list input
                    reminderSetUpState = 3;                             //assert state 3
                    c = new SetReminderCommand(reminderSetUpState);
                } else {
                    c = new SetReminderCommand(userInput);
                }
            } else if (reminderSetUpState == 3) {
                reminderSetUpState = 4;                                 //assert state 4
                c = new SetReminderCommand(reminderSetUpState, userInput);
                isSettingUpReminder = false;
                reminderSetUpState = 1;
            } else {
                throw new ReminderSetupException();
            }
        } else {
            c = Parser.parse(userInput);
            if (c instanceof SetReminderCommand) {
                isSettingUpReminder = true;
            } else if (c instanceof QuizCommand) {
                throw new ChangeSceneException();
            }
        }
        return c.execute(ui, bank, storage);
    }
}
