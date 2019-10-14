package Commands;

import Farmio.Farmer;
import Farmio.Farmio;
import Farmio.Storage;
import FarmioExceptions.FarmioException;
import Farmio.Ui;

public abstract class Command {
    public boolean isExit = false;
    public abstract void execute(Farmio farmio) throws FarmioException;
}
