package duke.model.shortcut;

import java.util.List;
import java.util.Objects;

/**
 * Represents a collection of multiple commands strings.
 * Guarantees: immutable.
 */
public class Shortcut {

    //Identity field
    private final String name;

    //Data field
    private final List<String> commandStrings;

    /**
     * Creates a {@code Shortcut}.
     *
     * @param name           Name of the shortcut.
     * @param commandStrings A series of command strings supplied by the user.
     */
    public Shortcut(String name, List<String> commandStrings) {
        this.name = name;
        this.commandStrings = commandStrings;
    }

    public String getName() {
        return name;
    }

    public List<String> getCommandStrings() {
        return commandStrings;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (String commandString : commandStrings) {
            stringBuilder.append(commandString).append("; ");
        }
        return stringBuilder.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Shortcut shortcut = (Shortcut) o;
        return Objects.equals(name, shortcut.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
