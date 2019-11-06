package farmio;

import exceptions.FarmioFatalException;
import frontend.Simulation;
import frontend.Ui;

public class Menu {
    private static final String ART_NAME = "menu";
    private static final String BULLET = "\t\u2022 ";

    public static void show(Farmio farmio, boolean resume) throws FarmioFatalException {
        Storage storage = farmio.getStorage();
        Ui ui = farmio.getUi();
        Simulation simulation =farmio.getSimulation();
        if(resume && storage.getSaveExist()) { //resume, save and load
            simulation.simulate("Menu", 2, false);
            ui.typeWriter("Enter the option of your choice\nPress [Enter] to resume game", false);
        }
        else if(resume){ //resume and save
            simulation.simulate("Menu", 4, false);
            ui.typeWriter("Enter the option of your choice\nPress [Enter] to resume game", false);

        }
        else if(storage.getSaveExist()){ //load
            simulation.simulate("Menu", 3, true);
            ui.typeWriter("Enter the option of your choice\nFor example, if you want to create a new game," +
                    " Enter [New Game]", false);
        }
        else {
            simulation.simulate("Menu", 0, true); //only new and quit
            ui.typeWriter("Enter the option of your choice\nFor example, if you want to create a new game," +
                    " Enter [New Game]", false);
        }

    }

}
