package duke.relation;

import java.time.Duration;
import java.time.LocalDateTime;
import duke.core.DateTimeParser;
import duke.core.DukeException;
import duke.relation.PatientTask;

public class EventPatientTask extends PatientTask {

    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String startTimeRaw;
    private String endTimeRaw;
    private long duration;

    public EventPatientTask(int pid, int tid, String stime, String eTime, String type){
        super(pid, tid, type);
        this.startTimeRaw = stime;
        this.endTimeRaw = eTime;
        try{
            this.startTime = DateTimeParser.convertToLocalDateTime(stime);
            this.endTime = DateTimeParser.convertToLocalDateTime(eTime);
        }
        catch (DukeException e) {
            System.out.println(e.getMessage());
        }

        duration = Duration.between(startTime, endTime).toMillis();
    }

    public EventPatientTask(int pid, int tid, boolean isdone, boolean isrecurrsive, String stime, String eTime, String type){
        super(pid, tid, isdone, isrecurrsive,type);
        this.startTimeRaw = stime;
        this.endTimeRaw = eTime;
        try{
            this.startTime = DateTimeParser.convertToLocalDateTime(stime);
            this.endTime = DateTimeParser.convertToLocalDateTime(eTime);
        }
        catch (DukeException e) {
            System.out.println(e.getMessage());
        }

        duration = Duration.between(startTime, endTime).toMillis();
    }

    public String getStartTimeRaw(){
        return startTimeRaw;
    }

    public String getEndTimeRaw(){
        return endTimeRaw;
    }

    public LocalDateTime getStartTime(){return startTime;}

    public LocalDateTime getEndTime() {return endTime;}

    public void updateStartTime(String time){
        try{
            this.startTime = DateTimeParser.convertToLocalDateTime(time);
        }
        catch (DukeException e) {
            System.out.println(e.getMessage());
        }
    }

    public void updateEndTime(String time){
        try{
            this.endTime = DateTimeParser.convertToLocalDateTime(time);
        }
        catch (DukeException e) {
            System.out.println(e.getMessage());
        }
    }

    public long retrieveDuration()
    {
        return this.duration;
    }

    public String toString(){
        return super.printStatus() + " From " + DateTimeParser.convertToEnglishDateTimeBeforeParse(startTime) + "To" + DateTimeParser.convertToEnglishDateTimeBeforeParse(endTime);
    }

}