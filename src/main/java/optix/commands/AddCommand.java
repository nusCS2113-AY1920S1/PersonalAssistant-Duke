package optix.commands;

import optix.Ui;
import optix.constant.OptixResponse;
import optix.core.Show;
import optix.core.Storage;
import optix.util.ShowMap;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class AddCommand extends Command {
    private String name;
    private LocalDate date;
    private double cost;

    public AddCommand(String name, String date, double cost) {
        // need to check if it is a valid date if not need to throw exception
        this.name = name;
        this.date = toLocalDate(date);
        this.cost = cost;
    }

    @Override
    public void execute(ShowMap shows, Ui ui, Storage storage) {
        Show show = new Show(name, cost);
        LocalDate today = storage.getToday();

        if (date.compareTo(today) <= 0) {
            ui.setMessage("☹ OOPS!!! It is not possible to perform in the past.\n");
        } else if (shows.containsKey(date)) {
            // to abstract the  date formatting in the future extensions
            ui.setMessage("☹ OOPS!!! There is already a show being added on that date.\n"
                    + "Please try again. \n");
        } else {
            shows.put(date, show);
            ui.setMessage(new OptixResponse().ADD + show.toString() + " at: " + this.date + "\n");
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
