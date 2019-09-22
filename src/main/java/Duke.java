import exceptions.DukeException;
import views.CLIView;

public class Duke {
    /**
     * Main class.
     *
     * @param args Refers to CLI arguments
     */
    public static void main(String[] args) {
        CLIView cliView = new CLIView();

        try {
            cliView.start();
        } catch (DukeException e) {
            System.out.println(e.getMessage());
        }
    }
}
