package cube;

import javafx.application.Application;

public class CubeLauncher {
    public static void main(String[] args) {

        if (args.length == 1 && args[0].equals("cli")) {
            Cube.main(args);
        } else {
            Application.launch(CubeApp.class, args);
        }
    }
}
