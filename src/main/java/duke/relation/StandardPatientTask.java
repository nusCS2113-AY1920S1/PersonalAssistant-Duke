package duke.relation;

import java.time.LocalDateTime;
import duke.core.DateTimeParser;
import duke.core.DukeException;
import duke.relation.PatientTask;

public class StandardPatientTask extends PatientTask {

    private LocalDateTime deadline;
    private String deadlineRaw;

    public StandardPatientTask(int pid, int tid, String timeBeforeFormat, String type){
        super(pid, tid, type);
        this.deadlineRaw = timeBeforeFormat;
        try{
            this.deadline = DateTimeParser.convertToLocalDateTime(timeBeforeFormat);
        }
        catch (DukeException e) {
            System.out.println(e.getMessage());
        }
    }

    public StandardPatientTask(int pid, int tid, boolean isdone, boolean isrecurrsive, String timeBeforeFormat, String type){
        super(pid, tid, isdone, isrecurrsive,type);
        this.deadlineRaw = timeBeforeFormat;
        try{
            this.deadline = DateTimeParser.convertToLocalDateTime(timeBeforeFormat);
        }
        catch (DukeException e) {
            System.out.println(e.getMessage());
        }
    }

    public LocalDateTime getDeadline(){return  this.deadline;}
    public String getDeadlineRaw(){
        return this.deadlineRaw;
    }


    public void updateDeadline(String time){
        try{
            this.deadline = DateTimeParser.convertToLocalDateTime(time);
        }
        catch (DukeException e) {
            System.out.println(e.getMessage());
        }
    }

    public String toString(){
        return super.printStatus() + " " + DateTimeParser.convertToEnglishDateTimeBeforeParse(deadline);
    }

}
