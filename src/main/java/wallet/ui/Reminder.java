package wallet.ui;

import wallet.record.Record;
import wallet.record.RecordList;
import wallet.task.TaskList;

import java.util.ArrayList;

public class Reminder {

    private RecordList recordList;
    private Ui ui;
    private int number;
    private boolean autoRemind;
    private int timeInSeconds;


    public Reminder(RecordList recordList){
        ui = new Ui();
        this.recordList = recordList;
        number = 1;
        autoRemind = true;
        timeInSeconds = 1800; //set default time interval of auto remind to be 30 minutes
    }


    /**
     * autoReminds the user of undone tasks
     * this method will run as a background process
     * users can also set the reminder interval timings
     * and also turn it off and on as they wish
     */
    public void autoRemindStart(){
        MyThread t = new MyThread(true, recordList);
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
