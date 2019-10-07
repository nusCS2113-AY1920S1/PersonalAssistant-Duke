package dolla.command;

import dolla.Ui;
import dolla.task.Entry;

import java.time.LocalDateTime;

public class AddEntryCommand extends Command {

    private String type;
    private double amount;
    private String description;
    private LocalDateTime date;

    public AddEntryCommand(String type, double amount, String description, LocalDateTime date) {
        this.type = type;
        this.amount = amount;
        this.description = description;
        this.date = date;
    }

    @Override
    public void execute() {
        Entry newEntry = new Entry(type, amount, description, date);
        Ui.echoAddEntry(newEntry);
    }
}
