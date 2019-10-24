//@@author LongLeCE

package planner.util.legacy.datetime;

import java.time.LocalDateTime;
import java.time.temporal.Temporal;

public class DateTime<E extends Temporal> {

    private E temporal;

    public DateTime(E temporal) {
        this.temporal = temporal;
    }

    public E value() {
        return this.temporal;
    }

    public static LocalDateTime now() {
        return LocalDateTime.now();
    }
}
