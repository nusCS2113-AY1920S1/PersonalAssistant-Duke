package owlmoney.logic.command.bank;

import owlmoney.model.profile.Profile;

public class DeleteSavingsCommand {
    private final String bankName;

    public DeleteSavingsCommand (String bankName) {
        this.bankName = bankName;
    }

    public void execute(Profile profile) {
        profile.deleteBank(this.bankName);
        System.out.println("bank deleted");
    }
}
