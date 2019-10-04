package duke.list;

import java.io.Serializable;
import java.util.Set;

abstract class DukeItem implements Serializable {
    protected Set<String> tags;
    abstract String toStorageString();
}
