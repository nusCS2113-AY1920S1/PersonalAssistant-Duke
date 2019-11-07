package dolla.command.modify;

import dolla.model.DollaData;
import dolla.command.Command;
import dolla.ui.ModifyUi;
import dolla.model.Entry;

import java.time.LocalDate;

//@@author omupenguin
public class FullModifyEntryCommand extends Command {

    private String type;
    private double amount;
    private String description;
    private LocalDate date;

    /**
     * Instantiates a new FullModifyEntryCommand.
     * @param type type of entry
     * @param amount of money
     * @param description description
     * @param date date
     */
    public FullModifyEntryCommand(String type, double amount, String description, LocalDate date) {
        //System.out.println("Creating new FullModifyEntryCommand....");
        this.type = type;
        this.amount = amount;
        this.description = description;
        this.date = date;
    }

    @Override
    public void execute(DollaData dollaData) {
        //System.out.println("Executing FullModifyEntryCommand....");
        Entry newEntry = new Entry(type, amount, description, date, "");
        dollaData.modifyRecordList(newEntry);
        ModifyUi.echoModifyRecord(newEntry);
        dollaData.updateMode("entry");
    }

    @Override
    public String getCommandInfo() {
        return null;
    }
}
