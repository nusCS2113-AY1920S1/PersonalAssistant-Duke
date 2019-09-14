package wallet.ui;

import wallet.task.Task;
import wallet.task.TaskList;

import java.util.ArrayList;

public class Reminder {

    private TaskList taskList;
    private Ui ui;
    private int number;
    private boolean autoRemind;
    private int timeInSeconds;

    public Reminder(TaskList taskList){
        ui = new Ui();
        this.taskList = taskList;
        number = 1;
        autoRemind = true;
        timeInSeconds = 1800; //set default time interval of auto remind to be 30 minutes
    }

    public void reminderList(){
        ui.printLine();
        System.out.println("Reminder to do your undone tasks!");
        for(int i = 0; i < taskList.getTaskListSize(); i++){
            if(taskList.getTask(i).getStatus() == false) {
                System.out.println(number + ". " + taskList.getTask(i).toString());
                number++;
            }
        }
        ui.printLine();
        number = 1; //resets the index
    }

    /**
     * autoReminds the user of undone tasks
     * this method will run as a background process
     * users can also set the reminder interval timings
     * and also turn it off and on as they wish
     */
    public void autoReminder(){
        new Thread(new Runnable(){
            @Override
            public void run() {
                while (autoRemind == true) {
                    try {
                        Thread.sleep(timeInSeconds*1000);
                        reminderList();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    public void setAutoRemind(boolean autoRemind) {
        this.autoRemind = autoRemind;
    }
    public boolean getAutoRemind(){
        return this.autoRemind;
    }

    public void setTimeInSeconds(int timeInSeconds) {
        this.timeInSeconds = timeInSeconds;
    }
    public int getTimeInSeconds(){
        return this.timeInSeconds;
    }
}
