package optix.commands.shows;

import optix.Ui;
import optix.commands.Command;
import optix.constant.OptixResponse;
import optix.core.Storage;
import optix.core.Theatre;
import optix.exceptions.OptixInvalidDateException;
import optix.util.OptixDateFormatter;
import optix.util.ShowMap;

import java.time.LocalDate;

public class AddCommand extends Command {
    private String showName;
    private String date;
    private double cost;
    private double seatBasePrice;

    private OptixResponse response = new OptixResponse();
    private OptixDateFormatter formatter = new OptixDateFormatter();

    public AddCommand(String showName, String date, double cost, double seatBasePrice) {
        // need to check if it is a valid date if not need to throw exception
        this.showName = showName;
        this.date = date;
        this.cost = cost;
        this.seatBasePrice = seatBasePrice;
    }

    @Override
    public void execute(ShowMap shows, Ui ui, Storage storage) {
        Theatre theatre = new Theatre(showName, cost, seatBasePrice);
        LocalDate today = storage.getToday();

        try {
            if (!formatter.isValidDate(date)) {
                throw new OptixInvalidDateException();
            }

            LocalDate showLocalDate = formatter.toLocalDate(date);

            if (showLocalDate.compareTo(today) <= 0) {
                ui.setMessage("☹ OOPS!!! It is not possible to perform in the past.\n");
            } else if (shows.containsKey(showLocalDate)) {
                ui.setMessage("☹ OOPS!!! There is already a show being added on that date.\n"
                        + "Please try again. \n");
            } else {
                shows.put(showLocalDate, theatre);
                ui.setMessage(response.ADD + theatre.getShowName() + " at: " + this.date + "\n");
            }
        } catch (OptixInvalidDateException e) {
            ui.setMessage(e.getMessage());
        }
    }



    @Override
    public boolean isExit() {
        return super.isExit();
    }
}
