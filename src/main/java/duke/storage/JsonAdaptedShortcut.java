package duke.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import duke.model.inventory.Ingredient;
import duke.model.shortcut.Shortcut;

import java.util.List;

public class JsonAdaptedShortcut {
    private final String name;
    private final List<String> userInputs;

    /**
     * Constructs a {@code JsonAdaptedShortcut} with the given details.
     */
    @JsonCreator
    public JsonAdaptedShortcut(
            @JsonProperty("name") String name,
            @JsonProperty("userInputs") List<String> userInputs) {
        this.name = name;
        this.userInputs = userInputs;
    }

    /**
     * Creates a jackson-friendly shortcut from {@code source}.
     */
    public JsonAdaptedShortcut(Shortcut source) {
        this.name = source.getName();
        this.userInputs = source.getUserInputs();
    }

    /**
     * Converts a given {@code Shortcut} into this class for Jackson use.
     */
    public Shortcut toModelType() {
        return new Shortcut(name, userInputs);
    }
}
