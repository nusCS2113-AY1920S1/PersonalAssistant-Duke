package Tasks;

import UI.Ui;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Timebound extends Task {
    public LocalDate dateStart;
    public LocalDate dateEnd;

    public Timebound (String description, String period) {
        super(description);

        try {
            DateTimeFormatter fmtED = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String[] dateTime = period.split(" and ");

            this.dateStart = LocalDate.parse(dateTime[0], fmtED);
            this.dateEnd = LocalDate.parse(dateTime[1], fmtED);
        } catch (DateTimeParseException | ArrayIndexOutOfBoundsException e) {
            Ui.showEventDateFormatError();
        }
    }
    @Override
    public String toString() {

        return "P"+ " | " + super.getStatusIcon() + " | " + super.description + " | " + "between: " + dateStart + " and " + dateEnd;
    }

    @Override

    public String listFormat(){
        return "[P]" + "[" + super.getStatusIcon() + "] " + super.description + " (between: " + dateStart + " and " + dateEnd + ")";
    }
}
