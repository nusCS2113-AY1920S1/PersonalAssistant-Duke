package duke.logic.command.bookingcommands;

import duke.logic.command.Command;
import duke.model.list.bookinglist.BookingList;
import duke.storage.BookingStorage;
import duke.model.task.bookingtasks.Booking;
import duke.ui.Ui;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static duke.common.BookingMessages.*;

public class ViewBookingScheduleCommand extends Command<BookingList, Ui, BookingStorage> {

    public ViewBookingScheduleCommand(String userInput) {
        this.userInput = userInput;
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
    public ArrayList<String> execute(BookingList bookingList, Ui ui, BookingStorage bookingStorage) throws ParseException {
        ArrayList<String> arrayList = new ArrayList<>();
        if (userInput.trim().equals(COMMAND_VIEW_BOOKING_SCHEDULE)) {
            arrayList.add(ERROR_MESSAGE_EMPTY_DATE);
        } else if (userInput.trim().charAt(19) == ' ') {
            String inputDate = userInput.substring(20).trim();
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
                    arrayList.add(MESSAGE_NO_BOOKING + outputDate + MESSAGE_PROMPT_ADDBOOKING);
                } else {
                    arrayList.add(MESSAGE_BOOKING_ON + outputDate);
                    for (int i = 0; i < scheduleList.size(); i++) {
                        arrayList.add("      " + (i + 1) + ". " + scheduleList.get(i));
                    }
                }
            } else {
                arrayList.add(ERROR_MESSAGE_INVALID_DATE);
            }
        } else {
            arrayList.add(ERROR_MESSAGE_INVALID_VIEWBOOKINGSCHEDULE_COMMAND);
        }
        return arrayList;
    }

    @Override
    public boolean isExit() {
        return false;
    }
}