package owlmoney.logic.command.bank;

import owlmoney.logic.command.Command;
import owlmoney.model.profile.Profile;
import owlmoney.ui.Ui;

public class ListSavingsCommand extends Command {

    public ListSavingsCommand (){
    }

    public void execute(Profile profile, Ui ui) {
        profile.listBanks(ui);
    }
}
