package optix.commands;

import optix.Ui;
import optix.constant.OptixResponse;
import optix.core.Storage;
import optix.core.Theatre;
import optix.util.ShowMap;

import java.time.LocalDate;
import java.util.Map;

public class ListCommand extends Command {
    private OptixResponse response = new OptixResponse();

    @Override
    public void execute(ShowMap shows, Ui ui, Storage storage) {
        StringBuilder message = new StringBuilder();
        LocalDate today = storage.getToday();

        int counter = 1;

        if (!shows.isEmpty()) {
            message.append(response.LIST_FOUND);
            for (Map.Entry<LocalDate, Theatre> entry : shows.entrySet()) {
                Theatre show = entry.getValue();
                LocalDate showDate = entry.getKey();

                if (showDate.compareTo(today) > 0) {
                    message.append(String.format("%d. %s (on: %s)\n", counter, show.getShowName(), showDate));
                    counter++;
                }
            }
        }

        if (shows.isEmpty() || counter == 1) {
            message = new StringBuilder(response.LIST_NOT_FOUND);
        }

        ui.setMessage(message.toString());
    }

    @Override
    public boolean isExit() {
        return super.isExit();
    }
}
