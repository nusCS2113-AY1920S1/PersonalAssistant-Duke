package Commands;

import Exceptions.FarmioFatalException;
import Farmio.Farmio;
import Exceptions.FarmioException;
import FrontEnd.Simulation;
import FrontEnd.Ui;
import Farmio.Storage;

import java.util.ArrayList;

public class CommandDayNew extends Command {
    @Override
    public void execute(Farmio farmio) throws FarmioException, FarmioFatalException {
        final String PATH = farmio.getLevel().getPath();
        Ui ui = farmio.getUi();
        Storage storage = farmio.getStorage();
        farmio.getUi().show("Press ENTER to start the day!");
        ArrayList<String> narratives = farmio.getLevel().getNarratives();
        for (int i = 0; i < narratives.size(); ++i) {
            ui.clearScreen();
            farmio.getSimulation().animate(PATH, i, true);
            ui.typeWriter(narratives.get(i));
            if (i != narratives.size() - 1) {
                ui.show("Press ENTER to continue.");
                ui.getInput();
            }
        }
    }
}
