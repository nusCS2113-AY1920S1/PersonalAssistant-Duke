import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Booking /*extends Facility*/ {


    protected LocalDateTime dateTime;

    protected String venue;

    protected int pax;

    /**
     * Facility.Booking constructor to make booking
     * @param description the specific room code
     * @param timing when you are booking the facility
     * @param people how many people you are accommodating
     */
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
