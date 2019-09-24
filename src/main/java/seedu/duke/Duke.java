package seedu.duke;

import seedu.duke.command.Command;
import seedu.duke.command.ExitCommand;

import java.util.Scanner;

/**
 * The main class of the program, which provides the entry point.
 */
public class Duke {
    private static TaskList taskList;
    private static EmailList emailList;
    private static UI ui;

    /**
     * The main function of the program, which is the entry point.
     *
     * @param args the arguments from the console when running
     */
    public static void main(String[] args) {
        ui = new UI();
        run();
    }

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

    private static void run() {
        taskList = Storage.readTasks();
        emailList = EmailStorage.readEmails();
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        Command command = Parser.parseCommand(input);
        while (!(command instanceof ExitCommand)) {
            command.execute();
            input = scanner.nextLine();
            command = Parser.parseCommand(input);
        }
        Storage.saveTasks(taskList);
        EmailStorage.saveEmails(emailList);
        ui.showMessage("Bye. Hope to see you again!");
    }

}
