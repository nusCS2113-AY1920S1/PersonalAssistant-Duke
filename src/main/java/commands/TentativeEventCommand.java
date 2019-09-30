package commands;
import Tasks.Task;
import UI.Ui;
import Storage.Storage;
import Exception.DukeException;
import java.io.IOException;
import Tasks.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class TentativeEventCommand extends Command{
    @Override
    public void execute(ArrayList<Task> list, Ui ui, Storage storage) throws DukeException, ParseException, IOException, NullPointerException {
        String description = "";
        try {
            if (ui.FullCommand.length() == 9) {
                throw new DukeException("OOPS!!! The description of an tentative event cannot be empty.");
            } else {
                description = ui.FullCommand.substring(10);
                System.out.println("You are creating a tentative event: " + description);
                System.out.println("Please enter possible time slots of the event");
                System.out.println("When you are done, key in '/'.");
                ArrayList<Date> tentativeoptions = new ArrayList<Date>();
                ArrayList<String> tentativetimes = new ArrayList<String>();
                ui.ReadCommand();
//            System.out.println(ui.FullCommand);
                while (!ui.FullCommand.equals("/")) {
                    SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    tentativeoptions.add(fmt.parse(ui.FullCommand));
                    tentativetimes.add(ui.FullCommand);
                    ui.ReadCommand();
//                System.out.println(ui.FullCommand);

            }
            TentativeEvent newtentative = new TentativeEvent(description, tentativeoptions,tentativetimes);
            System.out.println("Got it. I've added this tentative event:");
            System.out.println(newtentative.listFormat());
            System.out.println("You could confirm one of the slots later.");
            list.add(newtentative);

            }
        }
        catch (DukeException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public boolean isExit() {
        return false;
    }
}