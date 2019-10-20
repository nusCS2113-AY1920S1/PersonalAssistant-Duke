package duke.logic.command;

import duke.logic.CommandParams;
import duke.logic.CommandResult;
import duke.model.Model;
import duke.storage.Storage;
import javafx.application.Platform;

import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Represents a specified command as ExitCommand by extending the {@code Command} class.
 * Terminates the loop in {@code main} method of Duke.
 * Responses with the result.
 */
public class ExitCommand extends Command {
    private static final String name = "bye";
    private static final String description = "Exits Duke++";
    private static final String usage = "bye";

    private static final String COMPLETE_MESSAGE = "Bye. Hope to see you again soon!";

    private enum SecondaryParam {
        ;

        private String name;
        private String description;

        SecondaryParam(String name, String description) {
            this.name = name;
            this.description = description;
        }
    }

    /**
     * Constructs an {@code ExitCommand} object.
     */
    public ExitCommand() {
        super(name, description, usage, Stream.of(SecondaryParam.values())
            .collect(Collectors.toMap(s -> s.name, s -> s.description)));
    }

    /**
     * Shows bye to user.
     *
     */
    public CommandResult execute(CommandParams commandParams, Model model, Storage storage) {
        // duke.ui.println("Bye. Hope to see you again soon!");
        // Platform.exit();

        return new CommandResult(COMPLETE_MESSAGE, CommandResult.DisplayedPane.CURRENT, true);
    }
}
