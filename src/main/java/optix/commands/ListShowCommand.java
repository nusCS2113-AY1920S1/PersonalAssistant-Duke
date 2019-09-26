package optix.commands;

import optix.Ui;
import optix.core.Show;
import optix.core.Storage;
import optix.util.ShowMap;

import java.time.LocalDate;
import java.util.Map;

public class ListShowCommand extends Command {
    private String showName;

    public ListShowCommand(String showName) {
        this.showName = showName;
    }


    @Override
    public void execute(ShowMap shows, Ui ui, Storage storage) {
        StringBuilder message = new StringBuilder(String.format("The show %s is showing on the following following dates: \n", showName));
        boolean existsShow = false;
        String today = LocalDate.now().toString();

        int counter = 1;

        for (Map.Entry<LocalDate, Show> entry : shows.entrySet()) {
            String showDate = entry.getKey().toString();

            if(showDate.compareTo(today) <= 0) {
                continue;
            }

            // Can add to check whether the show has seats available. If not seats are available we can remove it from the listing.
            if (entry.getValue().hasSameName(showName.trim())) {
                existsShow = true;
                message.append(String.format("%d. %s\n", counter, showDate));
                counter++;
            }
        }
        
        if (!existsShow) { 
            message = new StringBuilder("☹ OOPS!!! The show cannot be found.");
        }

        ui.setMessage(message.toString());
    }

    @Override
    public boolean isExit() {
        return super.isExit();
    }


}
