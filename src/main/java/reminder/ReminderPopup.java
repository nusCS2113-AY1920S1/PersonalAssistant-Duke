package reminder;

import dictionary.Word;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import scene.NewScene;
import storage.Storage;

import java.util.ArrayList;

//@@author tessa-z
public class ReminderPopup extends NewScene {

    protected Scene reminderScene;
    protected Stage reminderPopup;
    protected ArrayList<String> wordArrayList;

    /**
     * Creates a reminder popup that sets up the scene and window properties, and deletes the reminder from text file.
     * @param wordList the ArrayList of words containing words tagged to the reminder
     * @param remindInfo the String matching the text stored in reminder.txt for deletion by storage
     */
    public ReminderPopup(ArrayList<String> wordList, String remindInfo) {
        storage = new Storage();
        storage.updateFile(remindInfo, "", "reminder");
        wordArrayList = wordList;
        makeReminderPopup();
        reminderPopup.show();
    }

    public void makeReminderPopup() {
        reminderScene = setReminderScene();
        reminderPopup = setReminderWindow();
    }

    /**
     * Creates a reminder scene.
     * @return reminder scene with initialised values
     */
    public Scene setReminderScene() {
        String displayText = "Remember to study these words:\n";
        for (String word: wordArrayList) {
            displayText += word + "\n";
        }
        Label secondLabel = new Label(displayText);

        StackPane secondaryLayout = new StackPane();
        secondaryLayout.getChildren().add(secondLabel);

        return new Scene(secondaryLayout, 230, 100);
    }

    /**
     * Creates a reminder stage.
     * @return reminder stage with initialised values
     */
    public Stage setReminderWindow() {
        reminderPopup = new Stage();
        reminderPopup.setTitle("Study Reminder");
        reminderPopup.setScene(reminderScene);

        return reminderPopup;
    }

}
