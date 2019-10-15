package booking;

import user.User;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.io.*;
import java.time.temporal.ChronoField;

public class Booking {

    protected LocalDateTime dateTimeStart;
    protected LocalDateTime dateTimeEnd;
    protected String venue;
    protected String username;
    protected String description;

    /**
     * Facility.booking.Booking constructor to make booking, and also to read from file
     *
     * @param roomcode      the specific room code
     * @param description   what you are going to use the room for
     * @param dateTimeStart when you are booking the facility
     * @param dateTimeEnd   when your booked period ends
     */
    public Booking(String roomcode, String description, String dateTimeStart, String dateTimeEnd, User user) {
        this.venue = roomcode;
        DateTimeFormatter formatterStart = DateTimeFormatter.ofPattern("dd/MM/yyyy HHmm");
        DateTimeFormatter formatterEnd = DateTimeFormatter.ofPattern("dd/MM/yyyy HHmm");
        this.dateTimeStart = LocalDateTime.parse(dateTimeStart, formatterStart);
        this.dateTimeEnd = LocalDateTime.parse(dateTimeEnd, formatterEnd);
        this.description = description;
        this.username = user.getUsername();

    }

    public Booking (String roomcode, String username, String description, long atStart, long atEnd) {
        this.venue =  roomcode;
        this.description = description;
        Instant instantStart = Instant.ofEpochMilli(atStart);
        Instant instantEnd = Instant.ofEpochMilli(atEnd);
        this.dateTimeStart = instantStart.atZone(ZoneId.systemDefault()).toLocalDateTime();
        this.dateTimeEnd = instantEnd.atZone(ZoneId.systemDefault()).toLocalDateTime();

    }

    public Booking (String roomcode, String username, String description, String atStart, String atEnd) {
        this.venue = roomcode;
        this.description = description;
        Instant instantStart = Instant.ofEpochMilli(Long.parseLong(atStart));
        Instant instantEnd = Instant.ofEpochMilli(Long.parseLong(atEnd));
        this.dateTimeStart = instantStart.atZone(ZoneId.systemDefault()).toLocalDateTime();
        this.dateTimeEnd = instantEnd.atZone(ZoneId.systemDefault()).toLocalDateTime();
        this.username = username;
    }




    public String toString() {
        DateTimeFormatter formatterStart = DateTimeFormatter.ofPattern("dd/MM/yyyy HHmm");
        DateTimeFormatter formatterEnd = DateTimeFormatter.ofPattern("dd/MM/yyyy HHmm");
        return venue + " " + dateTimeStart.format(formatterStart) + " to " + dateTimeEnd.format(formatterEnd);
    }


    public String toWriteFile() {
        return this.username + " | " + this.venue + " | " + this.description + " | " + this.dateTimeStart.getLong(ChronoField.EPOCH_DAY) + " | "
                + this.dateTimeEnd.getLong(ChronoField.EPOCH_DAY) + "\n";
    }

    public LocalDateTime getDateTimeStart() {return this.dateTimeStart;}

    public LocalDateTime getDateTimeEnd() {return this.dateTimeEnd;}

}
