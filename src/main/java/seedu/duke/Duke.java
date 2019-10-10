package seedu.duke;

import seedu.duke.common.network.Http;
import seedu.duke.common.command.Command;
import seedu.duke.email.EmailList;
import seedu.duke.email.EmailStorage;
import seedu.duke.task.TaskList;
import seedu.duke.task.TaskStorage;


/**
 * The main class of the program, which provides the entry point.
 */
public class Duke {
    private static TaskList taskList = new TaskList();
    private static EmailList emailList = new EmailList();
    private static UI ui;
    private static CommandParser commandParser;

    ///**
    // * The main function of the cli program, which is the entry point.
    // *
    // * @param args the arguments from the console when running
    // */
    //public static void main(String[] args) throws Parser.UserInputException {
    //    ui = new UI();
    //    parser = new Parser();
    //    ui.setDebug(true);
    //    Http.startAuthProcess();
    //    run();
    //}

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

    //private static void run() throws Parser.UserInputException {
    //    taskList = Storage.readTasks();
    //    emailList = EmailStorage.readEmails();
    //    Scanner scanner = new Scanner(System.in);
    //    String input = scanner.nextLine();
    //    Command command = parser.parseCommand(input);
    //    while (!(command instanceof ExitCommand)) {
    //        command.execute();
    //        input = scanner.nextLine();
    //        command = parser.parseCommand(input);
    //    }
    //    Storage.saveTasks(taskList);
    //    EmailStorage.saveEmails(emailList);
    //    ui.showMessage("Bye. Hope to see you again!");
    //}

    /**
     * Main function of the GUI program.
     */
    public Duke() {
        ui = new UI();
        commandParser = new CommandParser();
        ui.setDebug(true);
        taskList = TaskStorage.readTasks();
        emailList = EmailStorage.readEmails();
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
            return e.getMessage();
        }
    }
}
