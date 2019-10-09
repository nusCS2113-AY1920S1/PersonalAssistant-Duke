package seedu.duke.gui;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;

import seedu.duke.task.entity.Task;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class TaskCard extends HBox {


    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX. As a
     * consequence, UI elements' variable names cannot be set to such keywords or an exception will be thrown
     * by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook
     *         level 4</a>
     */
    public final Task task;
    public final int index;

    @FXML
    private HBox cardPane;
    @FXML
    private Label taskName;
    @FXML
    private Label taskType;
    @FXML
    private Label date;
    @FXML
    private Label doAfter;
    @FXML
    private Label id;
    @FXML
    private FlowPane tags;

    public TaskCard(Task task, int displayedIndex) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("/view/TaskCard.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.task = task;
        this.index = displayedIndex;

        id.setText(index + ". ");
        taskName.setText(task.getName());
        String type = task.getTaskType().toString();
        taskType.setText(type);
//        switch (type) {
//            case "D":
//                date.setText("Deadline date");
//                break;
//            case "E":
//                date.setText("Event date");
//                break;
//            default:
//                date.setText(null);
//        }
//        task.getTags().stream()
//                .sorted(Comparator.comparing(tag -> tag.tagName))
//                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }

//    @Override
//    public boolean equals(Object other) {
//        // short circuit if same object
//        if (other == this) {
//            return true;
//        }
//        // instanceof handles nulls
//        if (!(other instanceof TaskCard)) {
//            return false;
//        }
//
//        // state check
//        TaskCard card = (TaskCard) other;
//        return id.getText().equals(card.id.getText())
//                && task.equals(card.task);
//    }
}