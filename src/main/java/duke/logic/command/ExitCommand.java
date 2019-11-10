package duke.logic.command;

import duke.logic.CommandParams;
import duke.logic.CommandResult;
import duke.model.Model;
import duke.storage.Storage;

import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Exits the application.
 */
public class ExitCommand extends Command {
    private static final String name = "bye";
    private static final String description = "Exits Duke++";
    private static final String usage = "bye";

    private static final String COMPLETE_MESSAGE = "Bye. Hope to see you again soon!";

    /**
     * Contains all secondary parameters used by {@code ExitCommand}.
     * Here the {@code ExitCommand} does not demand secondary parameters.
     */
    private enum SecondaryParam {
        ;

        private String name;
        private String description;

        /**
         * Constructs a {@code SecondaryParam} with its name and usage.
         *
         * @param name        The name of the secondary parameter.
         * @param description The usage of this parameter.
         */
        SecondaryParam(String name, String description) {
            this.name = name;
            this.description = description;
        }
    }

    /**
     * Creates an ExitCommand, with its name, description, usage and secondary parameters.
     */
    public ExitCommand() {
        super(name, description, usage, Stream.of(SecondaryParam.values())
            .collect(Collectors.toMap(s -> s.name, s -> s.description)));
    }

    @Override
    public CommandResult execute(CommandParams commandParams, Model model, Storage storage) {
        return new CommandResult(COMPLETE_MESSAGE, CommandResult.DisplayedPane.EXPENSE, true);
    }
}
