package duke.task;

import duke.command.AddCommand;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Stores description and date/time automatically for recursive events
 * based on frequency and number of times the event is held.
 */
public class Repeat extends Task {
    protected Date at;
    protected SimpleDateFormat datetimeFormat = new SimpleDateFormat("dd/MM/yyyy HHmm");
    protected String taskDesc;
    protected String dateDesc;
    protected String repeatDesc;
    protected int timesToRepeat;
    /**
     * Creates an event with the specified description, date/time, frequency and number of times it repeats.
     *
     * @param taskDesc The description of the task.
     * @param dateDesc The date/time of the task.
     * @param listRepeatDates The different dates for the same event.
     * @param timesToRepeat The number of times it needs to repeat.
     * @throws ParseException If there is an error converting the date/time.
     */
    public Repeat(String taskDesc, String dateDesc, String[] listRepeatDates, int timesToRepeat) throws ParseException {
        super(taskDesc);
        this.taskDesc = taskDesc;

        Date dateTime = null;
        for(int i=0; i<timesToRepeat; i++){
            try {
                dateTime = datetimeFormat.parse(listRepeatDates[i]);
                this.at = dateTime;
            } catch (ParseException e) {
                System.out.println("Error reading date/time, please use this format \"d/MM/yyyy HHmm\"");
                throw e;
            }
            Task taskObj = new Event(this.taskDesc, listRepeatDates[i]);
            new AddCommand(taskObj);
        }
    }

    @Override
    public String toFile() {
        String datetimeStr = datetimeFormat.format(at);
        return "E|" + super.toFile() + "|" + datetimeStr;
    }
}