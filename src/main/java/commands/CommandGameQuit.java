package commands;

import exceptions.FarmioFatalException;
import farmio.Farmio;

public class CommandGameQuit extends Command {
    /**
     * Quits the game
     * @param farmio the game to show exit message
     * @throws FarmioFatalException if simulation file cannot be found
     */
    @Override
    public void execute(Farmio farmio) throws FarmioFatalException {
        farmio.getSimulation().simulate("GameExit", 0, true);
        farmio.setExit();
    }
}
