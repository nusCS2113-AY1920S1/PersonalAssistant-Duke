package Commands;

import ControlPanel.Storage;
import ControlPanel.Ui;
import Tasks.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ViewScheduleCommand extends Command {

    private String inputString;
    private SimpleDateFormat simpleDateFormat;

    public ViewScheduleCommand(String string){
        this.inputString = string;
        simpleDateFormat  = new SimpleDateFormat("d/M/yyyy HHmm");
    }

    @Override
    public boolean isExit() {
        return false;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws ParseException {
        String[] currDay = inputString.split("/on ");

        Date startDay = simpleDateFormat.parse(currDay[currDay.length-1] + " 0000");
        Date endDay = simpleDateFormat.parse(currDay[currDay.length-1] + " 2359");
        //System.out.println(startDay);
        //System.out.println(endDay);
        System.out.println(" Got it. Your schedule for " + currDay[currDay.length-1] + ": \n");
        int counter = 1;
        for(Task t : tasks.getCheckList()){
            Boolean isToday = false;
            if(t instanceof Deadline){
                Date dueDate = ((Deadline) t).getDateBy();
                isToday = (!dueDate.before(startDay) && !dueDate.after(endDay));

            }else if (t instanceof Events){
                Date startDate = ((Events) t).getStartDateAt();
                Date endDate = ((Events) t).getEndDateAt();
                isToday = (startDate.after(startDay) && startDate.before(endDay)) ||
                        (endDate.after(startDay) && endDate.before(endDay)) ||
                        (startDay.after(startDate) && endDay.before(endDate));
            }else if(t instanceof Periods){
                Date startDate = ((Periods) t).getDateFrom();
                Date endDate = ((Periods) t).getDateTo();
                isToday = (startDate.after(startDay) && startDate.before(endDay)) ||
                        (endDate.after(startDay) && endDate.before(endDay)) ||
                        (startDay.after(startDate) && endDay.before(endDate));
            }

            if(isToday && !t.getStatus()){
                System.out.println(" " + counter++ + "." + t.toString() + "\n");
            }
        }
    }
}
