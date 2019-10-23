package duke.command.bookingcommands;

import duke.command.CommandBooking;
import duke.exception.DukeException;
import duke.list.bookinglist.BookingList;
import duke.storage.BookingStorage;
import duke.task.bookingtasks.Booking;
import duke.ui.Ui;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class ViewBookingScheduleCommand extends CommandBooking {

    public ViewBookingScheduleCommand(String userInputCommand) {
        this.userInputCommand = userInputCommand;
    }

    private static boolean isDateParsable(String bookingDate) {
        try {
            new SimpleDateFormat("dd/MM/yyyy").parse(bookingDate);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    private static boolean isCurrentTime (Date currDate, Date bookingDate) {
        Calendar calendar = GregorianCalendar.getInstance();

        calendar.setTime(currDate);
        int currDay = calendar.get(Calendar.DAY_OF_MONTH);
        int currMonth = calendar.get(Calendar.MONTH);
        int currYear = calendar.get(Calendar.YEAR);

        calendar.setTime(bookingDate);
        int taskDay = calendar.get(Calendar.DAY_OF_MONTH);
        int taskMonth = calendar.get(Calendar.MONTH);
        int taskYear = calendar.get(Calendar.YEAR);

        if(taskYear == currYear && taskMonth == currMonth && taskDay == currDay) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public ArrayList<String> execute(BookingList bookingList, Ui ui, BookingStorage bookingStorage) throws DukeException, ParseException {
        ArrayList<String> arrayList = new ArrayList<>();
        if (userInputCommand.trim().equals("viewbookingschedule")) {
            arrayList.add("Booking date cannot be empty!\n" +
                    "       Please enter in the following format:\n" +
                    "       viewbookingschedule <date: dd/MM/yyyy>");
        } else if (userInputCommand.trim().charAt(19) == ' ') {
            String inputDate = userInputCommand.substring(20).trim();
            if (isDateParsable(inputDate)) {
                Date currDate = new SimpleDateFormat("dd/MM/yyyy").parse(inputDate);
                
                ArrayList<Booking> scheduleList = new ArrayList<>();
                for (Booking booking : bookingList.getBookingList()) {
                    Date bookingDate = booking.getDateTime();
                    if (isCurrentTime(currDate, bookingDate)) {
                        scheduleList.add(booking);
                    }
                }
                String outputDate = new SimpleDateFormat("dd MMMM yyyy").format(currDate);
                if (scheduleList.isEmpty()) {
                    arrayList.add("      No booking on " + outputDate);
                } else {
                    arrayList.add("      Here are your bookings for " + outputDate);
                    for (int i = 0; i < scheduleList.size(); i++) {
                        arrayList.add("      " + (i + 1) + ". " + scheduleList.get(i));
                    }
                }
            } else {
                arrayList.add("Invalid booking date entered.\n Please enter again in the format: dd/MM/yyyy");
            }
        } else {
            arrayList.add("Incorrect view booking schedule command.\n " +
                    "       Please enter in the following format:\n" +
                    "       viewbookingschedule <date: dd/MM/yyyy>");
        }
        return arrayList;
    }

    @Override
    public boolean isExit() {
        return false;
    }
}