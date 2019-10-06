package Optix.commands;

import optix.Ui;
import optix.commands.shows.AddCommand;
import optix.core.Storage;
import optix.util.ShowMap;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class AddCommandTest {
	private ShowMap shows = new ShowMap();
	private Ui ui = new Ui();
	private File currentDir = new File(System.getProperty("user.dir"));
	private File filePath = new File(currentDir.toString() + "\\src\\main\\data\\testOptix.txt");
	private Storage storage = new Storage(filePath);

	@Test
	void execute() {
		AddCommand testCommand = new AddCommand("dummy show name","5/5/2020",2000,20);

		testCommand.execute(shows, ui, storage);
		String expected = "__________________________________________________________________________________\n"
				+ "Got it. I've added this show:\n"
				+ "dummy show name at: 5/5/2020\n"
				+ "__________________________________________________________________________________\n";
		assertEquals(expected,ui.showLine());
		filePath.deleteOnExit();
	}
}