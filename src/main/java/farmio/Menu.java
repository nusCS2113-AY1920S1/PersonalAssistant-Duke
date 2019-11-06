package farmio;

import exceptions.FarmioFatalException;
import frontend.Simulation;
import frontend.Ui;

public class Menu {

    /**
     * Shows the menu.
     * @param farmio the game of which the menu controls
     * @param hasResume if there is an ongoing game
     * @throws FarmioFatalException if simulation file cannot be found
     */
    public static void show(Farmio farmio, boolean hasResume) throws FarmioFatalException {
        Storage storage = farmio.getStorage();
        Ui ui = farmio.getUi();
        Simulation simulation = farmio.getSimulation();
        if (hasResume && storage.getSaveExist()) { //hasResume, save and load
            simulation.simulate("Menu", 2, false);
            ui.typeWriter("Enter the option of your choice\nPress [Enter] to hasResume game", false);
        } else if (hasResume) { //hasResume and save
            simulation.simulate("Menu", 4, false);
            ui.typeWriter("Enter the option of your choice\nPress [Enter] to hasResume game", false);
        } else if (storage.getSaveExist()) { //load
            simulation.simulate("Menu", 3, true);
            ui.typeWriter("Enter the option of your choice\nFor example, if you want to create a new game,"
                    + " Enter [New Game]", false);
        } else {
            simulation.simulate("Menu", 0, true); //only new and quit
            ui.typeWriter("Enter the option of your choice\nFor example, if you want to create a new game,"
                    + " Enter [New Game]", false);
        }
    }
}