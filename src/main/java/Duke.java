import views.CLIView;

import java.io.IOException;

public class Duke {
    /**
     * Main class.
     *
     * @param args Refers to CLI arguments
     */
    public static void main(String[] args) throws IOException {
        CLIView cliView = new CLIView();

        cliView.start();
    }
}
