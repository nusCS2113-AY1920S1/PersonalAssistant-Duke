package owlmoney.logic.command;

import owlmoney.model.profile.Profile;
import owlmoney.ui.Ui;

public class PlaceHolderEmptyCommand extends Command {
    String message;
    public PlaceHolderEmptyCommand () {
        this.message = "Execute command";
    }

    public void execute(Profile profile, Ui ui) {
        System.out.println(message);
    }
}
