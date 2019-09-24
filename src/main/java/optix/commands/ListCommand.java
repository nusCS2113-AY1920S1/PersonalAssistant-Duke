package optix.commands;

import optix.Ui;
import optix.constant.OptixResponse;
import optix.core.Show;
import optix.core.Storage;
import optix.util.ShowMap;

import java.time.LocalDate;
import java.util.Map;

public class ListCommand extends Command {

    @Override
    public void execute(ShowMap shows, Ui ui, Storage storage) {
        String message = "";
        LocalDate today = storage.getToday();

        int counter = 1;

        if (!shows.isEmpty()) {
            for (Map.Entry<LocalDate, Show> entry : shows.entrySet()) {
                Show show = entry.getValue();
                LocalDate showDate = entry.getKey();

                if (showDate.compareTo(today) > 0) {
                    message += String.format("%d. %s (on: %s)\n", counter, show.toString(), showDate);
                    counter++;
                }
            }
        }

        if (shows.isEmpty() || counter == 1) {
            message = new OptixResponse().LIST_NOT_FOUND;
        }

        ui.setMessage(message);
    }

    @Override
    public boolean isExit() {
        return super.isExit();
    }
}
