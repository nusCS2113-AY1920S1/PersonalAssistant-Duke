package duke.util;

import java.time.LocalDateTime;
import java.time.temporal.Temporal;

public class DateTime<E extends Temporal> {

    private E temporal;

    public DateTime(E Temporal) {
        this.temporal = Temporal;
    }

    public E value() {
        return this.temporal;
    }

    public static LocalDateTime now() {
        return LocalDateTime.now();
    }
}
