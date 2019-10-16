package Commands;

import Farmio.Farmer;
import Farmio.Farmio;
import Farmio.Storage;
import FrontEnd.Ui;
import FarmioExceptions.FarmioException;
import org.json.simple.parser.ParseException;

import java.io.IOException;

public class CommandGameLoad extends Command {
    @Override
    public void execute(Farmio farmio) throws FarmioException {
        Ui ui = farmio.getUi();
        Storage storage = farmio.getStorage();
        try {
            farmio.setFarmer(new Farmer(storage.loadFarmer()));
        } catch (FarmioException | ParseException e) {
            ui.showWarning("Game save is corrupted!");
            ui.showInfo("Starting a new game.");
        } catch (IOException e) {
            ui.showWarning("No game save detected!");
            ui.showInfo("Starting a new game.");
        }
    }
}
