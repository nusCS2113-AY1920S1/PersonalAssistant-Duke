package dolla.command;

import dolla.DollaData;
import dolla.task.Limit;
import dolla.ui.LimitUi;

public class RemoveLimitCommand extends Command {

    private String type;
    private String duration;

    public RemoveLimitCommand(String type, String duration) {
        this.type = type;
        this.duration = duration;
    }

    /**
     * This method is called execute the titular command after every user input.
     *
     * @param dollaData dollaData
     * @throws Exception handle exception
     */
    @Override
    public void execute(DollaData dollaData) throws Exception {
        dollaData.removeLimit(type, duration);
        LimitUi.echoRemoveLimit(type, duration);
    }
}
