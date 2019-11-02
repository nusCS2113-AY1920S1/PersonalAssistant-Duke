package duke.command.dishesCommand;

import duke.command.Command;
import duke.dish.Dish;
import duke.exception.DukeException;
import duke.list.GenericList;
import duke.storage.Storage;
import duke.ui.Ui;

public class InitCommand extends Command<Dish> {

    public InitCommand() {
        //clears all the amount in dishes
    }

    @Override
    public void execute(GenericList<Dish> dish1, Ui ui, Storage storage) throws DukeException {
        System.out.println("\t are you sure you want to clear list? [y/n]");
        String command = ui.readCommand();
        if(command.toLowerCase().equals("y")){
            dish1.clearList();
            System.out.println("\t LIST IS CLEARED");
        }
        else if(command.toLowerCase().equals("n")){
            System.out.println("\t LIST IS NOT CLEARED");
        }
        else {throw new DukeException("Please enter y or n after 'initialize' command");}
    }
}
