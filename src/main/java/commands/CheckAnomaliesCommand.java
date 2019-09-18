package commands;

import tasks.Task;
import utils.DukeException;
import utils.Storage;

import java.util.ArrayList;

public class CheckAnomaliesCommand extends Command{

    public void execute(ArrayList<Task> tasks, Storage storage) throws DukeException{

    }

    public boolean isExit(){
        return false;
    }

}
