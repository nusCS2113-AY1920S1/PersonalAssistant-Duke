package duke.logic.commands;

import duke.exceptions.DukeException;
import duke.models.LockerList;
import duke.models.locker.Locker;
import duke.models.tag.Tag;
import duke.storage.Storage;
import duke.ui.Ui;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class RemindersCommand extends Command {

    public static final String COMMAND_WORD = "reminders";

    @Override
    public void execute(LockerList lockerList, Ui ui, Storage storage) throws DukeException {

        Tag unauthorized = new Tag("unauthorized");
        Tag broken = new Tag("broken");

        LocalDate now = LocalDate.now();
        String dateNow = now.format(DateTimeFormatter.ofPattern("dd-MM-uuuu"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-uuuu");
        LocalDate localDateNow = LocalDate.parse(dateNow,formatter);

        List<Locker> containsExpiringLockers = lockerList.getLockerList().stream()
                .filter(s -> s.findExpiryDate(localDateNow))
                .collect(Collectors.toList());

        List<Locker> containsUnauthorizedLockers = lockerList.getLockerList().stream()
                .filter(p -> p.getTag().equals(unauthorized))
                .collect(Collectors.toList());

        List<Locker> containsBrokenLockers = lockerList.getLockerList().stream()
                .filter(p -> p.getTag().equals(broken))
                .collect(Collectors.toList());

        ui.printReminders(containsExpiringLockers, containsUnauthorizedLockers, containsBrokenLockers);
    }

}
