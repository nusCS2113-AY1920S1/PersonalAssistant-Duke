package dolla.command;

import dolla.DollaData;

public class ShortcutCommand extends Command {
    private String index;
    private String inputLine;
    private String inputArray[];

    public ShortcutCommand(String inputLine) {
        this.inputLine = inputLine;
    }



    @Override
    public void execute(DollaData dollaData) throws Exception {

    }

    @Override
    public String getCommandInfo() {
        return null;
    }
}
