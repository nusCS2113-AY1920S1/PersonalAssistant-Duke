package logic.command;

import common.DukeException;
import model.Model;

public class UnlinkCommand extends LinkCommand {

    public UnlinkCommand(int[] tasksIndexes, String[] membersNames) {
        super(tasksIndexes, membersNames);
    }

    //@@author chengyuheng
    @Override
    public CommandOutput execute(Model model) throws DukeException {
        this.checkAvailability(model);
        String output = "";
        for (int i = 0; i < tasksIndexes.length; i++) {
            for (int j = 0; j < membersNames.length; j++) {
                try {
                    model.unlink(tasksIndexes[i], membersNames[j]);
                    output += "Noted, unlinked task "
                            + model.getTasksManager().getTaskById(tasksIndexes[i]).getName()
                            + " from member "
                            + membersNames[j] + ".\n";
                } catch (DukeException e) {
                    output += e.getMessage() + ".\n";
                }
            }
        }
        model.save();
        return new CommandOutput(output);
    }
}
