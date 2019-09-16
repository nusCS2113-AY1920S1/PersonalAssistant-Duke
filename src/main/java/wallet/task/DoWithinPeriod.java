package wallet.task;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DoWithinPeriod extends Task{

    /**
     * The date of the event.
     */
    private Date dateStart;
    private Date dateEnd;

    public DoWithinPeriod(String description, Date dateStart, Date dateEnd) {
        super(description);
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
    }

    @Override
    public String toString() {
        return "[DW]" + super.toString() + " (from: " + new SimpleDateFormat("dd MMM yyyy h:mma").format(dateStart)
                                         + " to: " + new SimpleDateFormat("dd MMM yyyy h:mma").format(dateEnd)+ ")";
    }

    /**
     * Outputs the string with the correct format for writing to output file
     * @return The string formatted for writing to output file
     */
    @Override
    public String writeToFile(){
        return "DW," + super.writeToFile() + "," + new SimpleDateFormat("dd MMM yyyy h:mma").format(dateStart) + "," + new SimpleDateFormat("dd MMM yyyy h:mma").format(dateEnd);
    }
}
