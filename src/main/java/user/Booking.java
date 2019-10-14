package user;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.io.*;
import java.time.temporal.ChronoField;

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
    public Booking(String username, String userType, String roomcode, String description, String dateTimeStart, String dateTimeEnd) {
        super(username, userType);
        this.venue = roomcode;
        //this.pax = people;
        DateTimeFormatter formatterStart = DateTimeFormatter.ofPattern("dd/mm/yyyy HH:mm");
        DateTimeFormatter formatterEnd = DateTimeFormatter.ofPattern("HH:mm");
        //this.dateTime = LocalDateTime.parse(timing, formatter); //add start time and end time
        this.dateTimeStart = LocalDateTime.parse(dateTimeStart, formatterStart);
        this.dateTimeEnd = LocalDateTime.parse(dateTimeEnd, formatterEnd);
        this.description = description;

    }

    public Booking (String roomcode, String username, String description, long atStart, long atEnd) {
        super(username);
        this.venue =  roomcode;
        this.description = description;
        Instant instantStart = Instant.ofEpochMilli(atStart);
        Instant instantEnd = Instant.ofEpochMilli(atEnd);
        this.dateTimeStart = instantStart.atZone(ZoneId.systemDefault()).toLocalDateTime();
        this.dateTimeEnd = instantEnd.atZone(ZoneId.systemDefault()).toLocalDateTime();

    }

    public Booking (String roomcode, String username, String description, String atStart, String atEnd) {
        super(username);
        this.venue = roomcode;
        this.description = description;
        Instant instantStart = Instant.ofEpochMilli(Long.parseLong(atStart));
        Instant instantEnd = Instant.ofEpochMilli(Long.parseLong(atEnd));
        this.dateTimeStart = instantStart.atZone(ZoneId.systemDefault()).toLocalDateTime();
        this.dateTimeEnd = instantEnd.atZone(ZoneId.systemDefault()).toLocalDateTime();
    }




    @Override
    public String toString() {
        DateTimeFormatter formatterStart = DateTimeFormatter.ofPattern("dd/mm/yyyy HHmm");
        DateTimeFormatter formatterEnd = DateTimeFormatter.ofPattern("HHmm");
        return venue + " " + dateTimeStart.format(formatterStart) + " " + dateTimeEnd.format(formatterEnd);
    }


    @Override
    public String toWriteFile() {
        return this.username + " | " + this.venue + " | " + this.description + " | " + "\n" + this.dateTimeStart.getLong(ChronoField.EPOCH_DAY) + " | "
                + this.dateTimeEnd.getLong(ChronoField.CLOCK_HOUR_OF_DAY) + "\n";
    }

    public LocalDateTime getDateTimeStart() {return this.dateTimeStart;}

    public LocalDateTime getDateTimeEnd() {return this.dateTimeEnd;}

}
