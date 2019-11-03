package chronologer.parser;

public abstract class VersionParser extends DescriptionParser {
    VersionParser(String userInput, String command)  {
        super(userInput, command);
    }

    public int extractVersion(String taskFeatures) {
        return (Integer.parseInt(taskFeatures));
    }
}
