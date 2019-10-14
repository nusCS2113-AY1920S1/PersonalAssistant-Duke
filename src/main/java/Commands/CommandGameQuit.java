package Commands;

import Farmio.Farmer;
import Farmio.Farmio;
import Farmio.Storage;
import Farmio.Ui;
import FarmioExceptions.FarmioException;

public class CommandGameQuit extends Command {
    @Override
    public void execute(Farmio farmio) throws FarmioException {
        farmio.setExit();
    }
}
