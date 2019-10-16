package duke.util;

import java.time.LocalDateTime;
import java.time.temporal.TemporalAccessor;

public class DateTime<E extends TemporalAccessor> {

    private E temporalAccessor;

    public DateTime(E temporalAccessor) {
        this.temporalAccessor = temporalAccessor;
    }

    public E value() {
        return this.temporalAccessor;
    }

    public static LocalDateTime now() {
        return LocalDateTime.now();
    }
}
