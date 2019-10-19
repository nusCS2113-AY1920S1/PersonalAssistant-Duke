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
        final String PATH = "Level1-01";
        Ui ui = farmio.getUi();
        Storage storage = farmio.getStorage();
        farmio.getUi().show("Press ENTER to start the day!");
        ArrayList<String> narratives = farmio.getLevel().getNarratives();
        for (int i = 0; i < narratives.size(); ++i) {
            ui.clearScreen();
            Simulation.animate(ui, storage, farmio.getFarmer(), PATH, i);
            ui.typeWriter(narratives.get(i));
            if (i != narratives.size() - 1) {
                ui.show("Press ENTER to continue.");
                ui.getInput();
            }
        }
    }
}
