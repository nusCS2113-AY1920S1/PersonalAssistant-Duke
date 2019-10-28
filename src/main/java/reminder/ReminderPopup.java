package reminder;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import scene.NewScene;

//@@author tessa-z
public class ReminderPopup extends NewScene {

    protected Scene reminderScene;
    protected Stage reminderPopup;

    public ReminderPopup() {
        makeReminderPopup();
        reminderPopup.show();
    }



    public void makeReminderPopup() {
        reminderScene = setReminderScene();
        reminderPopup = setReminderWindow();
    }

    public Scene setReminderScene() {
        Label secondLabel = new Label("Remember to study these words:");

        StackPane secondaryLayout = new StackPane();
        secondaryLayout.getChildren().add(secondLabel);

        return new Scene(secondaryLayout, 230, 100);
    }

    public Stage setReminderWindow() {
        reminderPopup = new Stage();
        reminderPopup.setTitle("Study Reminder");
        reminderPopup.setScene(reminderScene);

        return reminderPopup;
    }

}
