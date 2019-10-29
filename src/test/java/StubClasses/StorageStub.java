package StubClasses;

import Commons.Storage;

/**
 * This class represents Storage to be used for unit testing.
 */
public class StorageStub extends Storage {

    private ReminderStub reminderStub = new ReminderStub();

    public ReminderStub getReminderObject() {
        return reminderStub;
    }
}
