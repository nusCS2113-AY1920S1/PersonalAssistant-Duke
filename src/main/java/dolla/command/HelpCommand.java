package dolla.command;

import dolla.model.DollaData;
import dolla.ui.HelpUi;

import static dolla.parser.ParserStringList.COMMAND_HELP;

//@@author Weng-Kexin
public class HelpCommand extends Command {

    @Override
    public void execute(DollaData dollaData) {
        HelpUi.helpCommandPrinter();
    }

    @Override
    public String getCommandInfo() {
        return COMMAND_HELP;
    }
}
