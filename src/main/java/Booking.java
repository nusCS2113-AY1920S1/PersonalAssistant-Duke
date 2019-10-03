import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Booking {

    protected LocalDateTime dateTime;

    protected String venue;

    protected int pax;

    public Booking (String description, String timing, String people) {
        this.venue = description;
        this.pax = Integer.parseInt(people);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/mm/yyyy HH:mm");
        this.dateTime = LocalDateTime.parse(timing, formatter);
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/mm/yyyy HH:mm");
        return venue + " " + dateTime.format(formatter) + " " + pax;
    }
}
