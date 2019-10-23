package duke.storage;

import duke.exception.DukeException;

import java.util.Map;

public interface PlanAttributesStorage {

    public void savePlanAttributes(Map<String, String> attributes) throws DukeException;

    public Map<String, String> loadAttributes();
}
