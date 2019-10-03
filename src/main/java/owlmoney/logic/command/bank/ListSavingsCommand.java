package owlmoney.logic.command.bank;

import owlmoney.logic.command.OwlMoneyCommand;
import owlmoney.model.profile.Profile;
import owlmoney.ui.Ui;

public class ListSavingsCommand extends OwlMoneyCommand {

    public ListSavingsCommand (){
    }

    public void execute(Profile profile, Ui ui) {
        profile.listBanks(ui);
    }
}
