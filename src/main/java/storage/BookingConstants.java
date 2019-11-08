package storage;

public class BookingConstants {
    public static final String FILENAME = "data/bookingslist.txt";
    public static final int USERNAME = 0;
    public static final int VENUE = 1;
    public static final int DESCRIPTION = 2;
    public static final int TIMESTART = 3;
    public static final int TIMEEND = 4;
    public static final int STATUS = 5;
    public static final int APPROVEDBY = 6;
    public static final String DATETIMEERROR = "Not able to parse the date for all patterns given, "
            + "please use this format: add NAME DESCRIPTION /at ROOM_CODE /from DATE TIMESTART /to TIMEEND"
            + ", DATE TIMESTART format is dd/mm/yyyy HHMM, TIMEEND is HHMM";
}
