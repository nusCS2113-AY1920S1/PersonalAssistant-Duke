package Tasks;

import javafx.util.Pair;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MultipleEvent extends Task {

    protected ArrayList<Pair<Date, Date>> dates;
    protected boolean beenChosen;
    private SimpleDateFormat simpleDateFormat;

    public MultipleEvent(String description, ArrayList<Pair<Date, Date>> dates) {
        super(description);
        this.dates = dates;
        this.beenChosen = false;
        super.type = "M";

        simpleDateFormat  = new SimpleDateFormat("d/M/yyyy HHmm");
    }

    @Override
    public String toString() {
        String possibleDates = "";
        for (Pair dates : dates) {
            possibleDates += simpleDateFormat.format(dates.getKey()) + " to " + simpleDateFormat.format(dates.getValue()) + " or ";
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

    @Override
    public void chooseDate(int index) {
        Pair<Date, Date> temp =  getDate(index);
        dates.clear();
        dates.add(temp);
        beenChosen = true;
    }
}
