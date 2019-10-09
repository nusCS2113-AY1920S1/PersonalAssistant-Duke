package user;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.io.*;

public class Booking extends User {

    protected LocalDateTime dateTimeStart;

    protected LocalDateTime dateTimeEnd;

    protected String venue;

    protected String description;

    /**
     * Facility.user.Booking constructor to make booking, and also to read from file
     *
     * @param roomcode      the specific room code
     * @param dateTimeStart when you are booking the facility
     * @param dateTimeEnd   when your booked period ends
     * @param description   what you are going to use the room for
     */
    public Booking(String roomcode, String username, String description, String dateTimeStart, String dateTimeEnd) {
        super(username);
        this.venue = roomcode;
        //this.pax = people;
        DateTimeFormatter formatterStart = DateTimeFormatter.ofPattern("dd/mm/yyyy HH:mm");
        DateTimeFormatter formatterEnd = DateTimeFormatter.ofPattern("HH:mm");
        //this.dateTime = LocalDateTime.parse(timing, formatter); //add start time and end time
        this.dateTimeStart = LocalDateTime.parse(dateTimeStart, formatterStart);
        this.dateTimeEnd = LocalDateTime.parse(dateTimeEnd, formatterEnd);
        this.description = description;

    }



    @Override
    public String toString() {
        DateTimeFormatter formatterStart = DateTimeFormatter.ofPattern("dd/mm/yyyy HH:mm");
        DateTimeFormatter formatterEnd = DateTimeFormatter.ofPattern("HH:mm");
        return venue + " " + dateTimeStart.format(formatterStart) + " " + dateTimeEnd.format(formatterEnd);
    }



    public String toWriteFile() {
        //int boolToInt = isDone ? 1 : 0;
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        String formattedDateTimeStart = dateTimeStart.format(formatter);
        String formattedDateTimeEnd = dateTimeEnd.format(formatter);
        return this.venue + " | " + "user" + " | " + this.description + "\n" + formattedDateTimeStart + " | "
                + formattedDateTimeEnd + "\n";
    }

    public LocalDateTime getDateTimeStart() {return this.dateTimeStart;}

    public LocalDateTime getDateTimeEnd() {return this.dateTimeEnd;}

}
