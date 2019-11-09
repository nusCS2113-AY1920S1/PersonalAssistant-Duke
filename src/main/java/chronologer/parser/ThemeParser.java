package chronologer.parser;

import chronologer.command.Command;
import chronologer.command.ThemeCommand;
import chronologer.exception.ChronologerException;

public class ThemeParser extends DescriptionParser {

    ThemeParser(String userInput, String command)  {
        super(userInput, command);
    }

    @Override
    public Command parse() throws ChronologerException {
        super.extract();
        int choiceOfTheme = extractTheme(taskFeatures);
        return new ThemeCommand(choiceOfTheme);
    }

    private int extractTheme(String taskFeatures) {
        int themeChosen;
        if (taskFeatures.startsWith("d")) {
            themeChosen = 0;
        } else if (taskFeatures.startsWith("l")) {
            themeChosen = 1;
        } else {
            themeChosen = -1;
        }
        return themeChosen;
    }
}
