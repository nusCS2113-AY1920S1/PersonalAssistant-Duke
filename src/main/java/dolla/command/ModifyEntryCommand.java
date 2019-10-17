package dolla.command;

import dolla.DollaData;
import dolla.ui.Ui;
import dolla.task.Entry;

import java.time.LocalDateTime;

public class ModifyEntryCommand extends Command {

    private String type;
    private double amount;
    private String description;
    private LocalDateTime date;

    public ModifyEntryCommand(String type, double amount, String description, LocalDateTime date) {
        this.type = type;
        this.amount = amount;
        this.description = description;
        this.date = date;
    }

    @Override
    public void execute(DollaData dollaData) {
        Entry newEntry = new Entry(type, amount, description, date);
        dollaData.modifyLogList(newEntry);
        Ui.echoAddEntry(newEntry);
        dollaData.updateMode("entry");
    }
}
