package chronologer.parser;

import chronologer.command.Command;
import chronologer.command.LocationCommand;
import chronologer.exception.ChronologerException;
import chronologer.ui.UiTemporary;

/**
 * Extract the components required for the location command from the user input.
 *
 * @author Tan Yi Xiang
 * @version v1.0
 */
public class LocationParser extends IndexParser {

    public LocationParser(String userInput, String command) {
        super(userInput, command);
    }

    @Override
    public Command parse() throws ChronologerException {
        super.extract();
        String locationString = extractNewLocation(taskFeatures);
        return new LocationCommand(indexOfTask, locationString);
    }

    private String extractNewLocation(String taskFeatures) throws ChronologerException {
        String locationString;
        try {
            String[] locationCommandParts = taskFeatures.split("\\s+", 2);
            locationString = locationCommandParts[1].trim();
        } catch (ArrayIndexOutOfBoundsException e) {
            UiTemporary.printOutput(ChronologerException.invalidLocation());
            logger.writeLog(e.toString(), this.getClass().getName(), userInput);
            throw new ChronologerException(ChronologerException.invalidLocation());
        }
        return locationString;
    }
}
