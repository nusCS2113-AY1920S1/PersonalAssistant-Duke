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

    private static boolean isValidDate(String bookingDate) {
        String[] dateInput = bookingDate.split("/");

        int day = Integer.parseInt(dateInput[0]);
        int month = Integer.parseInt(dateInput[1]);
        int year = Integer.parseInt(dateInput[2]);

        if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {
            if (day > 0 && day <= 31 && year >= 2000) {
                return true;
            }
        }
        if (month == 4 || month == 6 || month == 9 || month == 11) {
            if (day > 0 && day <= 30 && year >= 2000) {
                return true;
            }
        }
        if (month == 2) {
            if (year >= 2000 && (year - 2000) % 4 == 0) { //is a leap year
                if (day > 0 && day <= 29) {
                    return true;
                }
                return false;
            } else if (year >= 2000 && (year - 2000) % 4 != 0) { //is a common year
                if (day > 0 && day <= 28) {
                    return true;
                }
                return false;
            }
        }
        return false;
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
                if (isValidDate(inputDate)) {
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
                    arrayList.add(ERROR_MESSAGE_OVERFLOW_DATE);
                }
            }else {
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