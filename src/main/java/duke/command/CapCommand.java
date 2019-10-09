package duke.command;

import duke.exceptions.ModException;
import duke.util.Reminder;
import duke.util.Storage;
import duke.util.TaskList;
import duke.util.Ui;

public class CapCommand extends Command {

    /*
    Cap report overall METHOD
    get list of done modules from tasklist, store as tuple? new class? (mcs, letter grade, s/u) in a new arraylist
    calculate mc weightage and cap, request for additional cap info if necessary eg. letter grade missing, s/u options
    show cap

    Cap what-if report one module METHOD
    get list of prereq/preclusions of that module, store in arraylist
    iterate through donemodules from tasklist, remove from above arraylist if done
    if empty, proceed, else throw new exception modules not completed
    calculate expected cap from the donemodules and their mc weightages
    print expected cap

    Cap what-if report overall METHOD
    repeat call above method for all 2k modules and above
    print expected cap @ graduation

    */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage store, Reminder reminder) throws ModException {

    }

    @Override
    public boolean isExit() {
        return false;
    }
}
