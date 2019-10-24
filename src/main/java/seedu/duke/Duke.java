package seedu.duke;

import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.util.Duration;
import seedu.duke.common.model.Model;
import seedu.duke.common.network.Http;
import seedu.duke.email.EmailContentParseHelper;
import seedu.duke.email.EmailList;
import seedu.duke.email.EmailStorage;
import seedu.duke.task.TaskList;
import seedu.duke.task.TaskStorage;
import seedu.duke.ui.UI;


/**
 * The main class of the program, which provides the entry point.
 */
public class Duke {
    private static UI ui = new UI();
    private static Model model = new Model();
    private static Duke duke;

    public static Duke getInstance() {
        if (duke == null) {
            duke = new Duke();
        }
        return duke;
    }

    /**
     * Main function of the GUI program.
     */
    public void run() {
        initModel();
        Http.startAuthProcess();
    }

    public static UI getUI() {
        return ui;
    }

    public static Model getModel() {
        return model;
    }

    /**
     * Exits the entire program.
     */
    public static void exit() {
        TaskStorage.saveTasks(model.getTaskList());
        EmailStorage.saveEmails(model.getEmailList());
        PauseTransition delay = new PauseTransition(Duration.seconds(1));
        delay.setOnFinished(event -> Platform.exit());
        delay.play();
    }

    private void initModel() {
        TaskList taskList = TaskStorage.readTaskFromFile();
        EmailList emailList = EmailStorage.readEmailFromFile("");
        EmailContentParseHelper.initKeywordList();

        model.setTaskList(taskList);
        model.setEmailList(emailList);
    }
}
