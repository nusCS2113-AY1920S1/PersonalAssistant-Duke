package chronologer.parser;

import chronologer.exception.ChronologerException;

/**
 * Extracts the version that they want!.
 *
 * @author Sai Ganesh Suresh
 * @version v1.0
 */
public abstract class VersionParser extends DescriptionParser {
    VersionParser(String userInput, String command)  {
        super(userInput, command);
    }

    /**
     * Obtains an integer of the version the user wants.
     *
     * @param taskFeatures contains the integer the user provides.
     */
    public int extractVersion(String taskFeatures) throws ChronologerException {
        int desiredVersion = Integer.parseInt(taskFeatures);
        if (desiredVersion >= 1 && desiredVersion <= 3) {
            return desiredVersion;
        } else {
            throw new ChronologerException(ChronologerException.invalidVersion());
        }
    }
}
