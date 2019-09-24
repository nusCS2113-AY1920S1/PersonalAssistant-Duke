package optix.commands;

import optix.Ui;
import optix.core.Show;
import optix.core.Storage;
import optix.util.ShowMap;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class PostponeCommand extends Command {
    private String showName;
    private LocalDate oldDate;
    private LocalDate newDate;

    public PostponeCommand(String showName, String oldDate, String newDate) {
        // need to check if both dates are valid if not throw exception
        // need to check if the event was completed in the past. Past event shouldn't be postponed.
        this.showName = showName;
        this.oldDate = toLocalDate(oldDate);
        this.newDate = toLocalDate(newDate);
    }

    @Override
    public void execute(ShowMap shows, Ui ui, Storage storage) {
        String message;
        LocalDate today = storage.getToday();

        if (oldDate.compareTo(today) > 0) {
            message = "☹ OOPS!!! The show is already over.\n";
        } else if (newDate.compareTo(today) > 0) {
            message = "☹ OOPS!!! It is not possible to postpone to the past.\n";
        } else {
            if (!shows.containsKey(oldDate)) {
                message = "☹ OOPS!!! There isn't any show on " + oldDate + "\n";
            } else if (shows.containsKey(newDate)) {
                message = "☹ OOPS!!! There exist a show for " + newDate + "\n";
            } else if (!shows.get(oldDate).hasSameName(showName)) {
                message = "☹ OOPS!!! Did you get the wrong date or wrong show. \n"
                        + "Try again!\n";
            } else {
                Show postponedShow = shows.removeShow(oldDate);
                shows.put(newDate, postponedShow);

                message = String.format("%s has been postponed from %s to %s\n", showName, oldDate, newDate);
            }
        }

        ui.setMessage(message);
    }

    @Override
    public boolean isExit() {
        return super.isExit();
    }

    private String getFormat(String date) {
        int padCount = 0;
        String format = "";
        String[] timeType = {"d","M","y","H","H","m","m"};
        for (int i = 0; i < date.length(); i += 1) {
            char c = date.charAt(i);
            if (Character.isDigit(c)) {
                format += timeType[padCount];
                if (padCount >= 3) { padCount += 1;}
            } else {
                format += c;
                padCount += 1;
            }
        }
        return format;
    }

    //TODO create a date formatter class
    /**
     * function to convert String to localDate
     * note thzt currently the formzt is fixed 1/1/1997
     * @param dateString
     * @return
     */
    private LocalDate toLocalDate(String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(getFormat(dateString));
        //Convert string to localdate
        LocalDate localDate = LocalDate.parse(dateString,formatter);
        return localDate;
    }
}
