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
    private String date;
    private double cost;

    public AddCommand(String name, String date, double cost) {
        this.name = name;
        this.date = date;
        this.cost = cost;
    }

    @Override
    public void execute(ShowMap shows, Ui ui, Storage storage) {
        Show show = new Show(name, cost);
        // to abstract the  date formatting in the future extensions
        LocalDate date = toLocalDate(this.date);
        shows.put(date, show);
        ui.setMessage(new OptixResponse().ADD + show.toString() + " at: " + this.date + "\n");
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

    @Override
    public boolean isExit() {
        return super.isExit();
    }
}
