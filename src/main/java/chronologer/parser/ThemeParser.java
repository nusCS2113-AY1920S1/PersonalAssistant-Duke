package chronologer.parser;

import chronologer.command.Command;
import chronologer.command.ThemeCommand;
import chronologer.exception.ChronologerException;

/**
 * Extracts the theme the user wants.
 *
 * @author Sai Ganesh Suresh
 * @version v1.4
 */
public class ThemeParser extends DescriptionParser {
    private static final int DARK_THEME = 0;
    private static final int LIGHT_THEME = 1;
    private static final int SAME_THEME = -1;

    ThemeParser(String userInput, String command)  {
        super(userInput, command);
    }

    /**
     * Extracts the theme the user wants and determines if the theme selected is the same.
     *
     * @param taskFeatures contains the integer the user provides.
     */
    private int extractTheme(String taskFeatures) {
        int themeChosen;
        if (taskFeatures.startsWith("d")) {
            themeChosen = DARK_THEME;
        } else if (taskFeatures.startsWith("l")) {
            themeChosen = LIGHT_THEME;
        } else {
            themeChosen = SAME_THEME;
        }
        return themeChosen;
    }

    @Override
    public Command parse() throws ChronologerException {
        super.extract();
        int choiceOfTheme = extractTheme(taskFeatures);
        return new ThemeCommand(choiceOfTheme);
    }
}
