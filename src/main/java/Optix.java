import optix.Ui;
import optix.commands.Command;
import optix.core.Storage;
import optix.parser.Parser;
import optix.util.ShowMap;

import java.io.File;

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

    public void run() {
        boolean isExit = false;
        System.out.println(ui.showWelcome());

        while (!isExit) {
            String fullCommand = ui.readCommand();
            Command c = Parser.parse(fullCommand);
            c.execute(shows, ui, storage);
            System.out.println(ui.showLine());
            isExit = c.isExit();
        }

    }
}

