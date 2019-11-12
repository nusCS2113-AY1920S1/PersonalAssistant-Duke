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
    public static final String DATETIMEERROR = ":-( Not able to parse the date for all patterns given, "
            + "please use this format:\n" + "add <name> <description> /at <room code> "
            + "/from <date start-time> /to <end-time>.\n"
            + "<date start-time>: <dd/mm/yyyy HHMM>, <end-time>: <HHMM>.";
    public static final String DATEERROR = "Not able to parse the date for all patterns given, "
            + "please use this format: DATE" + ", DATE format is dd/mm/yyyy";
    public static final String MONTHERROR = ":-( OOPS!!! Please enter the correct format!\n"
            + "listmonth <month>\n"
            + "<month>: <mm>";
    public static final String APPROVEERROR = ":-( OOPS!!! "
            + "Please enter the index of the item you want to approve"
            + " with the following format: approve INDEX";
    public static final String DELETEERROR = ":-( OOPS!!! "
            + "Please enter the index of the item you want to delete"
            + " with the following format: delete INDEX";
    public static final String EDITERROR = ":-( OOPS!!! "
            + "Please enter the index of the item you want to edit as well as the "
            + "updated description of your booking!";
    public static final String REJECTERROR = ":-( OOPS!!! "
            + "Please enter the correct format!\n"
            + "reject <index>";
    public static final String INDEXERROR2 = ":-( OOPS!!! Please enter an index in integer form!";
    public static final String MONTHERROR1 = ":-( OOPS!!! Please enter the month in integer form!";
    public static final String MONTHERROR2 = ":-( OOPS!!! Please enter a valid month!";
    public static final String EDITSUCCESS = "The description of this request has been changed!";
}
