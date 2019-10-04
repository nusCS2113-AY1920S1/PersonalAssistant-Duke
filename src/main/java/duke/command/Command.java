package duke.command;

import duke.list.ExpenseList;
import duke.parser.CommandParams;
import duke.storage.Storage;
import duke.ui.Ui;

import java.util.Map;

/**
 * Acts as the parent class of all commands in the duke.command package, with fields meant to be
 * populated by the individual commands.
 */
public abstract class Command {
    private String name;
    private String description;
    private String usage;
    private Map<String, String> secondaryParams;

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getUsage() {
        return usage;
    }

    public Map<String, String> getSecondaryParams() {
        return secondaryParams;
    }

    /**
     * Creates a new duke.command object, with its name, description, usage and secondary parameters.
     *
     * @param name the name of the duke.command to create.
     * @param description the description of the duke.command to create.
     * @param usage the usage of the duke.command to create.
     * @param secondaryParams the secondary parameters of the duke.command to create.
     */
    protected Command(String name, String description, String usage, Map<String, String> secondaryParams) {
        this.name = name;
        this.description = description;
        this.usage = usage;
        this.secondaryParams = secondaryParams;
    }

    /**
     * Executes the duke.command with parameters given by the user.
     *
     * @param commandParams the parameters given by the user, parsed into a {@code CommandParams} object.
     * @param expensesList The ExpenseList of duke.Duke.
     * @param ui The duke.ui of duke.Duke.
     * @param storage The duke.storage of duke.Duke.
     */
    public void execute(CommandParams commandParams, ExpenseList expensesList, Ui ui, Storage storage) {
    }
}