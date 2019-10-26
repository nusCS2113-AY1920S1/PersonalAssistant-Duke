package duke.command;

import duke.DukeCore;
import duke.exception.DukeException;

import java.util.Map;
import java.util.Set;

public class ImpressionNewCommand extends ArgCommand {

    @Override
    protected ArgSpec getSpec() {
        return ImpressionNewSpec.getSpec();
    }

    @Override
    public void execute(DukeCore core) throws DukeException {
        super.execute(core);

        //check if the type of data to add was uniquely specified
        boolean addTypeFound = false;
        String[] typeArr = {"medicine", "investigation", "result", "observation", "plan"};
        for (String type : typeArr) {
            if ()
        }
    }
}
