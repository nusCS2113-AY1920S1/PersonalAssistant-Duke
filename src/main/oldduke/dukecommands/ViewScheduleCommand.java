<<<<<<< HEAD:src/main/oldduke/dukecommands/ViewScheduleCommand.java
package duke.command.dukecommands;

import duke.command.Command;
import duke.storage.Storage;
import duke.list.tasklist.TaskList;
import duke.task.duketasks.Task;
=======
package duke.command.bookingcommands;

import duke.command.CommandBooking;
import duke.exception.DukeException;
import duke.list.BookingList;
import duke.storage.BookingStorage;
import duke.task.bookingtasks.Booking;
import duke.ui.Ui;
>>>>>>> branch-booking-list:src/main/java/duke/command/bookingcommands/ViewBookingScheduleCommand.java

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static duke.common.Messages.ERROR_MESSAGE_GENERAL;
import static duke.common.Messages.ERROR_MESSAGE_VIEWSCHEDULE;

public class ViewBookingScheduleCommand extends CommandBooking {

<<<<<<< HEAD:src/main/oldduke/dukecommands/ViewScheduleCommand.java
    public ViewScheduleCommand(String userInput) {
        this.userInput = userInput;
    }


    public void execute(TaskList taskList, Ui ui, Storage storage) throws DukeException, ParseException {
        if (userInput.trim().equals(COMMAND_VIEWSCHEDULE)) {
            throw new DukeException(ERROR_MESSAGE_GENERAL + ERROR_MESSAGE_VIEWSCHEDULE);
        }
        String inputDate = userInput.substring(13).trim();
=======
    public ViewBookingScheduleCommand(String userInputCommand) {
        this.userInputCommand = userInputCommand;
    }

    @Override
    public void execute(BookingList bookingList, Ui ui, BookingStorage bookingStorage) throws DukeException, ParseException {
        if (userInputCommand.trim().equals("viewbookingschedule")) {
            throw new DukeException(ERROR_MESSAGE_GENERAL + ERROR_MESSAGE_VIEWSCHEDULE);
        }
        String inputDate = userInputCommand.substring(20).trim();
>>>>>>> branch-booking-list:src/main/java/duke/command/bookingcommands/ViewBookingScheduleCommand.java
        Date currDate = new SimpleDateFormat("dd/MM/yyyy").parse(inputDate);
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(currDate);

        int currDay = calendar.get(Calendar.DAY_OF_MONTH);
        int currMonth = calendar.get(Calendar.MONTH);
        int currYear = calendar.get(Calendar.YEAR);

        ArrayList<Booking> scheduleList = new ArrayList<>();
        for (Booking booking : bookingList.getBookingList()) {
    //        if (task.getTaskType() == Task.TaskType.DEADLINE) {
                calendar.setTime(booking.getDateTime());
                int taskDay = calendar.get(Calendar.DAY_OF_MONTH);
                int taskMonth = calendar.get(Calendar.MONTH);
                int taskYear = calendar.get(Calendar.YEAR);
                if (taskYear == currYear && taskMonth == currMonth && taskDay == currDay) {
                    scheduleList.add(booking);
                }
   //         }
        }

        String outputDate = new SimpleDateFormat("dd MMMM yyyy").format(currDate);
        if (scheduleList.isEmpty()) {
            System.out.println("      No booking on " + outputDate);
        } else {
            System.out.println("      Here are your bookings for " + outputDate);
            for (int i = 0; i < scheduleList.size(); i++) {
                System.out.println("      " + (i + 1) + ". " + scheduleList.get(i));
            }
        }
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
