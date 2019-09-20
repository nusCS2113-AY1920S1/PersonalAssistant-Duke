package wallet.model.task;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Tentative extends Task {

    private ArrayList<Date> possibleDates;

    /**
     * Constructs a new Tentative object.
     *
     * @param description The description of the task.
     */
    public Tentative(String description, ArrayList<Date> possibleDates) {
        super(description);
        this.possibleDates = possibleDates;
    }

    @Override
    public String toString() {
        String outputFormat = "[*E]" + super.toString() + " (at: " + "<unscheduled>" + ")";
        return outputFormat;
    }

    public ArrayList<Date> getPossibleDates() {
        return possibleDates;
    }

    @Override
    public String writeToFile() {

        String concatArray = "";

        for (Date d : possibleDates) {

            String formatDate = new SimpleDateFormat("dd MMM yyyy h:mma").format(d);
            concatArray = String.join("|", formatDate, concatArray);

        }

        return "*E," + super.writeToFile() + "," + concatArray;
    }
}
