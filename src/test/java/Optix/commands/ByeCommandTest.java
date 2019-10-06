package Optix.commands;

import optix.Ui;
import optix.commands.ByeCommand;
import optix.core.Storage;
import optix.util.ShowMap;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class ByeCommandTest {
	private ShowMap shows = new ShowMap();
	private Ui ui = new Ui();
	private File currentDir = new File(System.getProperty("user.dir"));
	private File filePath = new File(currentDir.toString() + "\\src\\main\\data\\testOptix.txt");
	private Storage storage = new Storage(filePath);
	@Test
	void execute() {
		ByeCommand testCommand = new ByeCommand();
		testCommand.execute(shows, ui, storage);

		String expected = "__________________________________________________________________________________\n"
				+ "Bye. Hope to see you again soon!\n"
				+ "__________________________________________________________________________________\n";
		assertEquals(expected, ui.showLine());
	}
}