package tasks;

import javafx.util.Pair;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MultipleEvent extends Task {

    protected ArrayList<Pair<Date, Date>> dates;
    protected boolean beenChosen;
    private SimpleDateFormat simpleDateFormat;

    /**
     * The constructor to initialize a MultipleEvent object.
     * @param description the description of the multiple event.
     * @param dates the list of start and end date of these events.
     */
    public MultipleEvent(String description, ArrayList<Pair<Date, Date>> dates) {
        super(description);
        this.dates = dates;
        this.beenChosen = false;
        super.type = "M";

        simpleDateFormat  = new SimpleDateFormat("d/M/yyyy HHmm");
    }

    public MultipleEvent(String description, ArrayList<Pair<Date, Date>> dates, String chosenStatus) {
        super(description);
        this.dates = dates;
        //this.beenChosen = false;
        super.type = "M";

        simpleDateFormat  = new SimpleDateFormat("d/M/yyyy HHmm");

        if (chosenStatus.equals("1")) {
            this.beenChosen = true;
        } else {
            this.beenChosen = false;
        }
    }

    @Override
    public String toString() {
        String possibleDates = "";
        for (Pair dates : dates) {
            possibleDates += simpleDateFormat.format(dates.getKey()) + " to "
                    + simpleDateFormat.format(dates.getValue()) + " or ";
        }
        possibleDates = possibleDates.substring(0, possibleDates.length() - 3);
        return "[E]" + super.toString() + "(at: " +  possibleDates + ")";
    }

    public Pair<Date, Date> getDate(int index) {
        return dates.get(index);
    }

    public ArrayList<Pair<Date,Date>> getDates() {
        return dates;
    }

    public boolean getChosenStatus() {
        return beenChosen;
    }

    public void setChosenStatusTrue(){
        this.beenChosen = true;
    }

    @Override
    public void chooseDate(int index) {
        Pair<Date, Date> temp =  getDate(index);
        dates.clear();
        dates.add(temp);
        beenChosen = true;
    }

    public Date getStartDateAt() {
        return dates.get(0).getKey();
    }

    public Date getEndDateAt() {
        return dates.get(0).getValue();
    }
}
