package Optix.commands;

import optix.commands.ByeCommand;
import optix.commons.Model;
import optix.commons.Storage;
import optix.ui.Ui;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ByeCommandTest {
	private Ui ui = new Ui();
	private File currentDir = new File(System.getProperty("user.dir"));
	private File filePath = new File(currentDir.toString() + "\\src\\test\\data\\testOptix.txt");
	private Storage storage = new Storage(filePath);
	private Model model = new Model(storage);
	@Test
	void execute() {
		ByeCommand testCommand = new ByeCommand();
		testCommand.execute(model, ui, storage);

		String expected = "__________________________________________________________________________________\n"
				+ "Bye. Hope to see you again soon!\n"
				+ "__________________________________________________________________________________\n";
		assertEquals(expected, ui.showCommandLine());
	}
}