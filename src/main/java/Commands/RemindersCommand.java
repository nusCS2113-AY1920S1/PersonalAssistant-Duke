package Commands;

import ControlPanel.Storage;
import ControlPanel.Ui;
import Tasks.Deadline;
import Tasks.Events;
import Tasks.Task;
import Tasks.TaskList;

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
                int counter = 1;
                for(Task t : tasks.getCheckList()){
                    Boolean isAfter = false;
                    if(t instanceof Deadline){
                        Date dueDate = ((Deadline) t).getDateBy();
                        isAfter = dueDate.after(endDay);

                    }else if (t instanceof Events){
                        Date dueDate = ((Events) t).getDateAt();
                        isAfter = dueDate.after(endDay);
                    }

                    if(isAfter){
                        System.out.println(" " + counter++ + "." + t.toString() + "\n");
                    }
                }
                break;
            }

            case "past":{
                int counter = 1;
                for(Task t : tasks.getCheckList()){
                    Boolean isBefore = false;
                    if(t instanceof Deadline){
                        Date dueDate = ((Deadline) t).getDateBy();
                        isBefore = dueDate.before(startDay);

                    }else if (t instanceof Events){
                        Date dueDate = ((Events) t).getDateAt();
                        isBefore = dueDate.before(startDay);
                    }

                    if(isBefore){
                        System.out.println(" " + counter++ + "." + t.toString() + "\n");
                    }
                }
                break;
            }
            case "today":{
                int counter = 1;
                for(Task t : tasks.getCheckList()){
                    Boolean isToday = false;
                    if(t instanceof Deadline){
                        Date dueDate = ((Deadline) t).getDateBy();
                        isToday = (!dueDate.before(startDay) && !dueDate.after(endDay));

                    }else if (t instanceof Events){
                        Date dueDate = ((Events) t).getDateAt();
                        isToday = (!dueDate.before(startDay) && !dueDate.after(endDay));
                    }

                    if(isToday){
                        System.out.println(" " + counter++ + "." + t.toString() + "\n");
                    }
                }
                break;
            }
        }

    }
}
