package seedu.duke;

import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.util.Duration;
import seedu.duke.common.model.Model;
import seedu.duke.common.network.Http;
import seedu.duke.email.EmailContentParser;
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
    public static UI getUI() {
        return ui;
    }
    private static Model model = new Model();
    public static Model getModel() {
        return model;
    }

    /**
     * Main function of the GUI program.
     */
    public Duke() {
        initModel();
        Http.startAuthProcess();
    }

    private void initModel() {
        TaskList taskList = TaskStorage.readTaskFromFile();
        EmailList emailList = EmailStorage.readEmailFromFile();
        EmailContentParser.initKeywordList();

        model.setTaskList(taskList);
        model.setEmailList(emailList);
    }

    public static void exit() {
        TaskStorage.saveTasks(model.getTaskList());
        EmailStorage.saveEmails(model.getEmailList());
        PauseTransition delay = new PauseTransition(Duration.seconds(1));
        delay.setOnFinished(event -> Platform.exit());
        delay.play();
    }
}
