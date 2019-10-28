package commands;

import exceptions.FarmioFatalException;
import farmio.Farmio;
import exceptions.FarmioException;

public abstract class Command {
    public abstract void execute(Farmio farmio) throws FarmioException, FarmioFatalException;
}
