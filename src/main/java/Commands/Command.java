package Commands;

import Exceptions.FarmioFatalException;
import Farmio.Farmio;
import Exceptions.FarmioException;

public abstract class Command {
    public abstract void execute(Farmio farmio) throws FarmioException, FarmioFatalException;
}
