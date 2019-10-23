package duke.model.shortcut;

import java.util.List;
import java.util.Objects;

/**
 * TODO: add comments
 */
public class Shortcut {

    //Identity field
    private final String name;

    private final List<String> userInputs;

    public Shortcut(String name, List<String> userInputs) {
        this.name = name;
        this.userInputs = userInputs;
    }

    public String getName() {
        return name;
    }

    public List<String> getUserInputs() {
        return userInputs;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (String userInput : userInputs) {
            stringBuilder.append(userInput).append("; ");
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
