package optix.commands;

import optix.Ui;
import optix.constant.OptixResponse;
import optix.core.Storage;
import optix.core.Theatre;
import optix.util.ShowMap;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class AddCommand extends Command {
    private String showName;
    private LocalDate date;
    private double cost;
    private double seatBasePrice;

    private OptixResponse response = new OptixResponse();

    public AddCommand(String showName, String date, double cost, double seatBasePrice) {
        // need to check if it is a valid date if not need to throw exception
        this.showName = showName;
        this.date = toLocalDate(date);
        this.cost = cost;
        this.seatBasePrice = seatBasePrice;
    }

    @Override
    public void execute(ShowMap shows, Ui ui, Storage storage) {
        Theatre theatre = new Theatre(showName, cost, seatBasePrice);
        LocalDate today = storage.getToday();

        if (date.compareTo(today) <= 0) {
            ui.setMessage("☹ OOPS!!! It is not possible to perform in the past.\n");
        } else if (shows.containsKey(date)) {
            // to abstract the  date formatting in the future extensions
            ui.setMessage("☹ OOPS!!! There is already a show being added on that date.\n"
                    + "Please try again. \n");
        } else {
            shows.put(date, theatre);
            ui.setMessage(response.ADD + theatre.getShowName() + " at: " + this.date + "\n");
        }

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

    @Override
    public boolean isExit() {
        return super.isExit();
    }
}
