package executor.command;

import executor.task.TaskList;
import interpreter.Parser;
import ui.Ui;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CommandSchedule extends Command {
    protected String userInput;


    //constructor
    public CommandSchedule(String userInput) {
        this.userInput = userInput;
        this.commandType = CommandType.VIEWSCHEDULE;
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
        Date date = new Date();
        //insert code to convert string to date type
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Date userDate = format.parse(dateInput);
        userDate.setTime(0);
        for (int index = 0; index < taskList.getSize(); ++index) {
            try {
                Date taskDate = taskList.getList().get(index).getDatetime();//creates of copy of datetime in the task
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
