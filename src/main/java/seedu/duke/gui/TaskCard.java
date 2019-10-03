package seedu.duke.gui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.duke.TaskList;
import seedu.duke.task.Task;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class TaskCard extends HBox {

    private static final String FXML = "TaskCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */
    public final TaskList tasks;

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

    public TaskCard(TaskList tasks, int displayedIndex) {
//        super(FXML);
        this.tasks = tasks;
        id.setText(displayedIndex + ". ");
//        taskName.setText(tasks.get(displayedIndex-1).Description);
        taskType.setText(tasks.get(displayedIndex-1).getTaskType().toString());
        if (tasks.get(displayedIndex-1).getTaskType().toString().equals("T")) {
            date.setText(null);
        } else {
            date.setText(tasks.get(displayedIndex-1).getTaskType().toString());
        }
//        task.getTags().stream()
//                .sorted(Comparator.comparing(tag -> tag.tagName))
//                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }

    public static TaskCard getTaskCard(TaskList tasks, int displayedIndex) {
        return new TaskCard(tasks, displayedIndex);
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