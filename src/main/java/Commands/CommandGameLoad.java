package Commands;

import Exceptions.FarmioFatalException;
import Farmio.Farmer;
import Farmio.Farmio;
import Farmio.Storage;
import Farmio.Level;
import FrontEnd.Ui;
import Exceptions.FarmioException;
import org.json.simple.parser.ParseException;

import java.io.IOException;

public class CommandGameLoad extends Command {
    @Override
    public void execute(Farmio farmio) throws FarmioFatalException {
        Ui ui = farmio.getUi();
        Storage storage = farmio.getStorage();
        try {
            farmio.setFarmer(new Farmer(storage.loadFarmer()));
            Level level = new Level(storage.getLevel(farmio.getFarmer().getLevel()));
            farmio.setLevel(level);
            ui.show("Load Game Success!");
        } catch (ParseException | FarmioException e) {
            ui.showWarning("Game save is corrupted!");
            ui.showInfo("Starting a new game.");
        } catch (IOException e) {
            ui.showWarning("No game save detected!");
            ui.showInfo("Starting a new game.");
        }
        farmio.setStage(Farmio.Stage.LEVEL_START);
    }
}
