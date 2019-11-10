package duke.logic.command;

import duke.exception.DukeException;
import duke.logic.CommandParams;
import duke.logic.CommandResult;
import duke.model.Model;
import duke.storage.Storage;

import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Switches panes in the main window.
 */
public class GoToCommand extends Command {

    private static final String name = "goto";
    private static final String description = "go to a desired pane.";
    private static final String usage = "goto $paneName";

    private static final String COMPLETE_MESSAGE = "";

    /**
     * Contains all secondary parameters used by {@code GoToCommand}.
     * Here the {@code GoToCommand} does not demand secondary parameters.
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
     * Creates a GoToCommand, with its name, description, usage and secondary parameters.
     */
    public GoToCommand() {
        super(name,
                description,
                usage,
                Stream.of(GoToCommand.SecondaryParam.values())
                        .collect(Collectors.toMap(s -> s.name, s -> s.description))
        );
    }

    @Override
    public CommandResult execute(CommandParams commandParams, Model model, Storage storage) throws DukeException {
        String desiredPane = commandParams.getMainParam();
        CommandResult.DisplayedPane displayedPane;
        try {
            displayedPane = CommandResult.DisplayedPane.valueOf(desiredPane.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new DukeException(String.format(DukeException.MESSAGE_PANE_NAME_INVALID, desiredPane));
        }

        return new CommandResult(COMPLETE_MESSAGE, displayedPane);
    }
}
