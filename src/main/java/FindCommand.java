<<<<<<< HEAD
import UI.Ui;
=======
>>>>>>> f7938ba1adc707a6bdf34ebdb286314c7a8a91d1

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class FindCommand extends Command {
    @Override
    public void execute(List<Task> list, Ui ui, Storage storage) throws DukeException, ParseException, IOException , NullPointerException {
        if(ui.FullCommand.length() == 5){
            throw new DukeException("OOPS!!! The description of a search cannot be empty.");
        }
        else {
            ArrayList<Task> searchedlist = new ArrayList<Task>();
            for(Task it: list){
                if(it.description.contains(ui.FullCommand.substring(6).trim())) {
                    searchedlist.add(it);
                }
            }
            System.out.println("Here are the matching tasks in your list:");
            for (int i = 0; i < searchedlist.size(); i++) {
                System.out.println(i + 1 + "." + searchedlist.get(i).listformat());
            }
        }
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
