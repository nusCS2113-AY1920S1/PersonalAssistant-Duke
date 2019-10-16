package duke.util;

import java.time.LocalDateTime;
import java.time.temporal.Temporal;

public class DateTime<E extends Temporal> {

    private E Temporal;

    public DateTime(E Temporal) {
        this.Temporal = Temporal;
    }

    public E value() {
        return this.Temporal;
    }

    public static LocalDateTime now() {
        return LocalDateTime.now();
    }
}
