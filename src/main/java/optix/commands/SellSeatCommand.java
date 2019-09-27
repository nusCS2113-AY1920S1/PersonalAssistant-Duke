package optix.commands;

import optix.Ui;
import optix.core.Storage;
import optix.core.Theatre;
import optix.util.ShowMap;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class SellSeatCommand extends Command {
    private String showName;
    private String showDate;
    private String[] seats;
    private String buyerName;

    public SellSeatCommand(String showName, String showDate, String buyerName, String seats) {
        this.showName = showName;
        this.showDate = showDate;
        this.buyerName = buyerName;
        this.seats = seats.split(" ");
    }

    @Override
    public void execute(ShowMap shows, Ui ui, Storage storage) {
        LocalDate key = toLocalDate(showDate);
        if (!shows.isEmpty() && shows.containsKey(key) && shows.get(key).hasSameName(showName)) {
            Theatre show = shows.get(key);
            show.sellSeats(buyerName, seats);
        }
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
