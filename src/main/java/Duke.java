import exceptions.DukeException;
import views.CLIView;

public class Duke {
    /**
     * Main class.
     *
     * @param args Refers to CLI arguments
     */
    public static void main(String[] args) throws DukeException {
        CLIView cliView = new CLIView();

        cliView.start();
    }
}
