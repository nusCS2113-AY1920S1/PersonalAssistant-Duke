package javacake.commands;

import javacake.Logic;
import javacake.storage.Profile;
import javacake.storage.Storage;
import javacake.ui.Ui;

public class ChangeColorCommand extends Command {
    @Override
    public String execute(Logic logic, Ui ui, Storage storage, Profile profile) {
        return "Changed color mode!\nType 'list' for more commands\nType 'help' for command info\n";
    }
}
