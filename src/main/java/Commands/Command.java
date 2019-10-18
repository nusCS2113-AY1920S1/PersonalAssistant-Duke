package Commands;

import Farmio.Farmio;
import Exceptions.FarmioException;

public abstract class Command {
    public boolean isExit = false;
    public abstract void execute(Farmio farmio) throws FarmioException;
}
