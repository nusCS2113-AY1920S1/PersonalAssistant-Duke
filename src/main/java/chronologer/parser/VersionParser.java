package chronologer.parser;

import chronologer.exception.ChronologerException;

public abstract class VersionParser extends DescriptionParser {
    VersionParser(String userInput, String command)  {
        super(userInput, command);
    }

    public int extractVersion(String taskFeatures) throws ChronologerException{
        int desiredVersion = Integer.parseInt(taskFeatures);
        if (desiredVersion >= 0 && desiredVersion <= 1) {
            return desiredVersion;
        } else {
            throw new ChronologerException(ChronologerException.invalidVersion());
        }
    }
}
