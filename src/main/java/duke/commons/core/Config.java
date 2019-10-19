package duke.commons.core;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Config values used by the app.
 */
public class Config {
    /**
     * Path of BakingHome data.
     */
    public static final Path BAKING_HOME_DATA_PATH = Paths.get("data", "baking.json");
}
