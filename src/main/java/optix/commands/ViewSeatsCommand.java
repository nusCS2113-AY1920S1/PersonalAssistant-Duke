package optix.commands;

import optix.Ui;
import optix.core.Storage;
import optix.core.Theatre;
import optix.util.ShowMap;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ViewSeatsCommand extends Command {
    private String showName;
    private LocalDate showDate;

    public ViewSeatsCommand(String showName, String showDate) {
        this.showName = showName;
        this.showDate = toLocalDate(showDate);
    }

    @Override
    public void execute(ShowMap shows, Ui ui, Storage storage) {
        StringBuilder message = new StringBuilder("Here is the layout of the theatre for " + showName + " on " + showDate + ": \n");

        if (!shows.isEmpty() && shows.get(showDate).hasSameName(showName)) {
            Theatre theatre = shows.get(showDate);
            message.append(theatre.getSeatingArrangement());
        } else {
            message = new StringBuilder("☹ OOPS!!! Sorry the show " + showName + " cannot be found.");
        }

        ui.setMessage(message.toString());
    }

    @Override
    public boolean isExit() {
        return super.isExit();
    }

    private String getFormat(String date) {
        int padCount = 0;

        StringBuilder format = new StringBuilder();
        String[] timeType = {"d","M","y","H","H","m","m"};
        for (int i = 0; i < date.length(); i += 1) {
            char c = date.charAt(i);
            if (Character.isDigit(c)) {
                format.append(timeType[padCount]);
                if (padCount >= 3) { padCount += 1;}
            } else {
                format.append(c);
                padCount += 1;
            }
        }
        return format.toString();

    }

    //TODO create a date formatter class
    /**
     * function to convert String to localDate

     * note that currently the format is fixed 1/1/1997
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
