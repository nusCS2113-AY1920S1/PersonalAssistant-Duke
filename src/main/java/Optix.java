import optix.Ui;
import optix.commands.Command;
import optix.parser.Parser;
import optix.util.ShowMap;

import java.util.Scanner;

public class Optix {

    private ShowMap shows;

    private Ui ui;

    public Optix() {
        ui = new Ui();
        shows = new ShowMap();
    }

    public static void main(String[] args) {
        new Optix().run();
    }

    public void run() {
        boolean isExit = false;
        Scanner sc = new Scanner(System.in);
        System.out.println(ui.showWelcome());

        while (!isExit) {
            String fullCommand = ui.readCommand(sc);
            Command c = Parser.parse(fullCommand);
            c.execute(shows, ui);
            System.out.println(ui.showLine());
            isExit = c.isExit();
        }

        sc.close();
    }
}

