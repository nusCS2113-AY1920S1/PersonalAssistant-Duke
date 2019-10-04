import optix.Ui;
import optix.commands.Command;
import optix.core.Storage;

import optix.exceptions.OptixException;
import optix.util.Parser;
import optix.util.ShowMap;

import java.io.File;


/**
 * Software that stores all the finance for the Opera Hall.
 */
public class Optix {
    private ShowMap shows;

    private Ui ui;

    private Storage storage;

    public Optix(File filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        shows = storage.load();
    }

    public static void main(String[] args) {
        File currentDir = new File(System.getProperty("user.dir"));
        File filePath = new File(currentDir.toString() + "\\src\\main\\data\\optix.txt");
        new Optix(filePath).run();
    }

    /**
     * To boot up the software.
     */
    public void run() {

        boolean isExit = false;
        System.out.println(ui.showWelcome());

        while (!isExit) {
            try {
                String fullCommand = ui.readCommand();
                Command c = Parser.parse(fullCommand);
                c.execute(shows, ui, storage);
                isExit = c.isExit();
            } catch (OptixException e) {
                ui.setMessage(e.getMessage());
            } finally {
                System.out.println(ui.showLine());
            }
        }

    }
}

