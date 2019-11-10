package chronologer.parser;

import chronologer.command.Command;
import chronologer.command.LocationCommand;
import chronologer.exception.ChronologerException;
import chronologer.ui.UiMessageHandler;

/**
 * Extract the components required for the location command from the user input.
 *
 * @author Tan Yi Xiang
 * @version v1.1
 */
public class LocationParser extends IndexParser {

    LocationParser(String userInput, String command) {
        super(userInput, command);
    }

    @Override
    public Command parse() throws ChronologerException {
        super.extract();
        String locationString = extractNewLocation(taskFeatures);
        return new LocationCommand(indexOfTask, locationString);
    }

    /**
     * Extract the location string component from user input.
     *
     * @param taskFeatures The user input
     * @return The location string
     * @throws ChronologerException If the location string is empty.
     */
    private String extractNewLocation(String taskFeatures) throws ChronologerException {
        String locationString;
        try {
            String[] locationCommandParts = taskFeatures.split("\\s+", 2);
            locationString = locationCommandParts[1].trim();
        } catch (ArrayIndexOutOfBoundsException e) {
            UiMessageHandler.outputMessage(ChronologerException.invalidLocation());
            logger.writeLog(e.toString(), this.getClass().getName(), userInput);
            throw new ChronologerException(ChronologerException.invalidLocation());
        }
        return locationString;
    }
}
