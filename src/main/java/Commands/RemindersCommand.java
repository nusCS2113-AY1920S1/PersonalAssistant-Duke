package Commands;

import ControlPanel.Storage;
import ControlPanel.Ui;
import Tasks.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RemindersCommand extends Command {

    protected Date nowDate;
    private SimpleDateFormat simpleDateFormat;
    private String keyword;

    public RemindersCommand(String string){
        this.keyword = string;
        this.nowDate = new Date();
        simpleDateFormat  = new SimpleDateFormat("d/M/yyyy HHmm");
    }

    @Override
    public boolean isExit() {
        return false;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws ParseException {
        Date currDate = nowDate;
        String[] currDay = simpleDateFormat.format(currDate).split(" ");
        Date startDay = simpleDateFormat.parse(currDay[0] + " 0000");
        Date endDay = simpleDateFormat.parse(currDay[0] + " 2359");

        switch (keyword){
            case "upcoming":{
                System.out.println(" Got it. Your Upcoming Reminders: \n");
                int counter = 1;
                for(Task t : tasks.getCheckList()){
                    Boolean isAfter = false;
                    if(t instanceof Deadline){
                        Date dueDate = ((Deadline) t).getDateBy();
                        isAfter = dueDate.after(endDay);

                    }else if (t instanceof Events){
                        Date dueDate = ((Events) t).getStartDateAt();
                        isAfter = dueDate.after(endDay);
                    }else if(t instanceof ToDos){
                        isAfter = true;
                    }else if(t instanceof Periods){
                        Date dueDate = ((Periods) t).getDateFrom();
                        isAfter = dueDate.after(endDay);
                    }

                    if(isAfter && !t.getStatus()){
                        System.out.println(" " + counter++ + "." + t.toString() + "\n");
                    }
                }
                break;
            }

            case "past":{
                System.out.println(" Got it. Your Past Reminders: \n");
                int counter = 1;
                for(Task t : tasks.getCheckList()){
                    Boolean isBefore = false;
                    if(t instanceof Deadline){
                        Date dueDate = ((Deadline) t).getDateBy();
                        isBefore = dueDate.before(startDay);

                    }else if (t instanceof Events){
                        Date dueDate = ((Events) t).getEndDateAt();
                        isBefore = dueDate.before(startDay);
                    }else if(t instanceof Periods){
                        Date dueDate = ((Periods) t).getDateTo();
                        isBefore = dueDate.before(startDay);
                    }

                    if(isBefore && !t.getStatus()){
                        System.out.println(" " + counter++ + "." + t.toString() + "\n");
                    }
                }
                break;
            }
            case "today":{
                System.out.println(" Got it. Today's Reminders: \n");
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
                break;
            }
        }

    }
}
