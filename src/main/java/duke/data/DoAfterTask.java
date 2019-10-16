package duke.data;

import duke.command.Command;

import java.time.LocalDateTime;

public class DoAfterTask extends TimedTask {

    private Command afterCommand;

    public DoAfterTask(String name, LocalDateTime time) {
        super(name, time);
        this.afterCommand = null;
    }

    public DoAfterTask(String name, Command afterCommand) {
        super(name, null);
        this.afterCommand = afterCommand;
    }
}
