package sgtravel.logic.commands;

import sgtravel.commons.Messages;
import sgtravel.commons.exceptions.HelpFailException;
import sgtravel.logic.commands.results.CommandResultText;
import sgtravel.model.Model;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Shows the help message.
 */
public class HelpCommand extends Command {
    private static final String HELP_URL = "https://github.com/AY1920S1-CS2113T-W13-3/main/blob/master/docs/UserGuide.adoc";

    /**
     * Executes this command and returns a text result.
     *
     * @param model The model object containing event list.
     */
    @Override
    public CommandResultText execute(Model model) throws HelpFailException {
        try {
            Desktop desktop = Desktop.getDesktop();
            desktop.browse(new URI(HELP_URL));
        } catch (URISyntaxException | IOException e) {
            throw new HelpFailException();
        }
        return new CommandResultText(Messages.HELP_SUCCESS);
    }
}
