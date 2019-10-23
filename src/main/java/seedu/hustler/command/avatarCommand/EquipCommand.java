package seedu.hustler.command.avatarCommand;

import seedu.hustler.Hustler;
import seedu.hustler.command.Command;
import seedu.hustler.data.AvatarStorage;

import java.io.IOException;

public class EquipCommand extends Command {

    private final int index;

    public EquipCommand(int index) {
        this.index = index;
    }

    @Override
    public void execute() throws IOException {
        System.out.println(index);
        Hustler.inventory.getToEquip(index - 1);
        AvatarStorage.save(Hustler.avatar);
    }
}
