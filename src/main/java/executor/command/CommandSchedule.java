package executor.command;

import executor.task.TaskList;
import interpreter.Parser;
import ui.Ui;
import ui.Wallet;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class CommandSchedule extends Command {
    protected String userInput;


    //constructor
    /**
     * Constructor for CommandSchedule subCommand Class.
     * @param userInput The user input from the CLI
     */
    public CommandSchedule(String userInput) {
        this.userInput = userInput;
        this.commandType = CommandType.VIEWSCHEDULE;
        this.description = "Prints the schedule for the input date";
    }

    @Override
    public void execute(Wallet wallet) {

    }

    @Override
    public void execute(TaskList taskList) {
        String dateInput = Parser.removeStr(this.commandType.toString(), this.userInput);
        Ui.dukeSays("Here is your schedule for the following date '"
                + dateInput
                + "'."
        );
        try {
            printSchedule(dateInput, taskList);
        } catch (Exception e) {
            System.out.println("Read invalid input");
        }
    }

    private void printSchedule(String dateInput, TaskList taskList) throws ParseException {
        LocalDate date = LocalDate.now();
        //insert code to convert string to date type
        String dateStr_3="28/09/16";
        DateTimeFormatter formatter_3 = DateTimeFormatter.ofPattern("dd/MM/yy");
        LocalDate userDate = LocalDate.parse(dateInput,formatter_3);
        for (int index = 0; index < taskList.getSize(); ++index) {
            try {
                LocalDate taskDate = taskList.getList().get(index).getDate();//creates of copy of datetime in the task
                if (taskDate == null) {
                    return;
                }
                taskDate.setTime(0);//this sets the time to zero
                if (taskDate.equals(userDate)) {
                    taskList.printTaskByIndex(index);
                }
            } catch (Exception e) {
                System.out.println("invalid");
            }
        }

    }
}
