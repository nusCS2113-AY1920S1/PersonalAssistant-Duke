package farmio;

import exceptions.FarmioFatalException;
import frontend.Simulation;
import frontend.Ui;

public class Menu {

    /**
     * Display menu options onto terminal.
     * @param ui Prints menu text onto terminal.
     * @param simulation Formatter for menu design.
     * @param hasSave True if game save file exist.
     * @param canResume True if game can be resumed.
     * @throws FarmioFatalException Fatal error from Simulator and must stop program.
     */
    public static void show(Ui ui, Simulation simulation, boolean hasSave, boolean canResume)
            throws FarmioFatalException {
        if (canResume && hasSave) {
            simulation.simulate("Menu", 2, false);
            ui.typeWriter("Enter the option of your choice\nPress [Enter] to canResume game", false);
        } else if (canResume) {
            simulation.simulate("Menu", 4, false);
            ui.typeWriter("Enter the option of your choice\nPress [Enter] to canResume game", false);

        } else if (hasSave) {
            simulation.simulate("Menu", 3, true);
            ui.typeWriter("Enter the option of your choice\nFor example, if you want to create a new game,"
                    + " Enter [New Game]", false);
        } else {
            simulation.simulate("Menu", 0, true);
            ui.typeWriter("Enter the option of your choice\nFor example, if you want to create a new game,"
                    + " Enter [New Game]", false);
        }
    }
}