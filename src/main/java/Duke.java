import java.io.*;
import java.text.ParseException;
import java.util.List;

/**
 * Returns void main function for duke.
 *
 * @param args.
 * @return Void.
 * @throws DukeException | ParseException | IOException | NullPointerException
 */

public class Duke {
    public static void main(String[] args) {

        List<Task> list;
        Storage store = new Storage();
        boolean isExit = false;
        Ui ui = new Ui();
        ui.showWelcome();
        try {
            list = store.Readfile();
            while(!isExit) {
                ui.ReadCommand();
                String command = ui.FullCommand.split(" ")[0];
                Command c = Parser.parse(command);
                c.execute(list,ui, store);
                isExit = c.isExit();
            }
        }
        catch (DukeException | ParseException | IOException | NullPointerException e){
            if(e instanceof ParseException){
                ui.showDateFormatError();
            }
            else {
                ui.showErrorMessage(e);
            }
        }
        finally{
            System.out.println("System exiting");
        }

//        finally{
//        level 9 branch
//            System.out.println("");
//        }
//        level 6
    }
}