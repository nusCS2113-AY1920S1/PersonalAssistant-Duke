//@@author namiwa

package planner.logic.exceptions.legacy;

public class LegacyExceptionTest {

    private final String test = "test";
    private ModException modException = new ModException();
    private ModCommandException modCommandException = new ModCommandException();
    private ModEmptyCommandException modEmptyCommandException = new ModEmptyCommandException();
    private ModEmptyListException modEmptyListException = new ModEmptyListException();
    private ModInvalidIndexException modInvalidIndexException = new ModInvalidIndexException();
    private ModInvalidTimeException modInvalidTimeException = new ModInvalidTimeException();
    private ModInvalidTimePeriodException modInvalidTimePeriodException = new ModInvalidTimePeriodException(test);
    private ModMissingArgumentException modMissingArgumentException = new ModMissingArgumentException(test);
    private ModNoTimeException modNoTimeException = new ModNoTimeException();
    private ModOutOfBoundException modOutOfBoundException = new ModOutOfBoundException();
    private ModScheduleException modScheduleException = new ModScheduleException();
    private ModTimeIntervalTooCloseException modTimeIntervalTooCloseException = new ModTimeIntervalTooCloseException();
    private ModMultipleValuesForSameArgumentException modMultipleValuesForSameArgumentException =
            new ModMultipleValuesForSameArgumentException();

}
