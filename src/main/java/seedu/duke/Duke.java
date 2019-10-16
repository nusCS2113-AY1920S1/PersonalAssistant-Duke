package seedu.duke;

import seedu.duke.common.network.Http;
import seedu.duke.common.command.Command;
import seedu.duke.email.EmailContentParser;
import seedu.duke.email.EmailList;
import seedu.duke.email.EmailStorage;
import seedu.duke.task.TaskList;
import seedu.duke.task.TaskStorage;

import static seedu.duke.email.EmailContentParser.testFuzzySearchInString;


/**
 * The main class of the program, which provides the entry point.
 */
public class Duke {
    private static TaskList taskList = new TaskList();
    private static EmailList emailList = new EmailList();
    private static UI ui;
    private static CommandParser commandParser;

    public static TaskList getTaskList() {
        return taskList;
    }

    public static EmailList getEmailList() {
        return emailList;
    }

    public static void setEmailList(EmailList emailList) {
        Duke.emailList = emailList;
    }

    public static UI getUI() {
        return ui;
    }

    /**
     * Main function of the GUI program.
     */
    public Duke() {
        ui = new UI();
        testFuzzySearchInString();
        commandParser = new CommandParser();
        ui.setDebug(true);
        taskList = TaskStorage.readTasks();
        emailList = EmailStorage.readEmails();
        EmailContentParser.initKeywordList();
        Http.startAuthProcess();
    }

    /**
     * Links up command output with GUI display.
     *
     * @param input user input
     * @return the response from the parsed and executed command
     */
    public String respond(String input) {
        try {
            ui.setInput(input);
            Command command = commandParser.parseCommand(input);
            ui.setCommand(command.toString());
            command.execute();
            return command.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }
}
