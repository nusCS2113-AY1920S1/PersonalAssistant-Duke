import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

public class DeadlineTest {
    @Test
    public void DeadlineTest(){
        LocalDateTime localDateTime = new DukeDateTime().getLocalDateTime("01-01-2019 1800");
        Deadline deadline = new Deadline("Test", localDateTime);
        assert deadline.toString().equals("[D][\u2718] Test (by: 2019-01-01T18:00)");
        deadline.markDone();
        assert deadline.toString().equals("[D][\u2713] Test (by: 2019-01-01T18:00)");
    }
}
