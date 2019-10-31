package dolla.command.modify;

import dolla.DollaData;
import dolla.command.Command;
import dolla.ui.Ui;
import dolla.task.Entry;

import java.time.LocalDate;

//@@author omupenguin
public class FullModifyEntryCommand extends Command {

    private String type;
    private double amount;
    private String description;
    private LocalDate date;

    /**
     * Instantiates a new FullModifyEntryCommand.
     * @param type type of modification
     * @param amount amount to modify
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
        Entry newEntry = new Entry(type, amount, description, date);
        dollaData.modifyRecordList(newEntry);
        Ui.echoAddRecord(newEntry);
        dollaData.updateMode("entry");
    }

    @Override
    public String getCommandInfo() {
        return null;
    }
}
