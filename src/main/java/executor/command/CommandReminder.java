package executor.command;

import executor.task.Task;
import executor.task.TaskList;
import ui.Ui;
import ui.Wallet;

import java.time.LocalDate;
import java.util.Date;

public class CommandReminder extends Command {
    //private String[] reminders;
    private LocalDate currentDate;

    /**
     * Constructor for CommandReminder subCommand Class.
     * @param userInput The user input from the CLI
     */
    public CommandReminder(String userInput) {
        this.userInput = userInput;
        this.commandType = CommandType.REMINDER;
        this.description = "Loops through list and checks if current date matches date linked with task and prints it";
        this.currentDate = LocalDate.now();
    }

    @Override
    public void execute(Wallet wallet) {

    }

    @Override
    public void execute(TaskList taskList) {
        try {
            for (Task task : taskList.getList()) {
                LocalDate dateOfTask = task.getDate();
                if (dateOfTask != null && dateOfTask.equals(this.currentDate)) {
                        Ui.dukeSays(task.genTaskDesc());
                        Ui.printSeparator();
                    }
                }
            } catch (Exception e) {
            System.out.println("sorry");
        }
    }
}
