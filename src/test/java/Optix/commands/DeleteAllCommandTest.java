package Optix.commands;

import optix.commands.shows.AddCommand;
import optix.Ui;
import optix.commands.shows.DeleteAllCommand;
import optix.core.Storage;
import optix.util.ShowMap;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class DeleteAllCommandTest {
	private ShowMap shows = new ShowMap();
	private Ui ui = new Ui();
	private File currentDir = new File(System.getProperty("user.dir"));
	private File filePath = new File(currentDir.toString() + "\\src\\main\\data\\testOptix.txt");
	private Storage storage = new Storage(filePath);
	@Test
	void execute() {
		AddCommand addTestShow1 = new AddCommand("Test Show 1","5/5/2020", 2000, 20);
		AddCommand addTestShow2 = new AddCommand("Test Show 2", "6/5/2020", 2000, 50);
		addTestShow1.execute(shows, ui, storage);
		addTestShow2.execute(shows, ui, storage);
		DeleteAllCommand testCommand = new DeleteAllCommand(new String[]{"Test Show 1", "Test Show 2","Intentionally missing show"});
		testCommand.execute(shows, ui, storage);
		String expected = "_________________________________________\n"
				+ "Noted. These are the deleted entries:\n"
				+ "2020-05-05 Test Show 1\n"
				+ "2020-05-06 Test Show 2\n"
				+ "Sorry, these shows were not found:\n"
				+ "Intentionally missing show\n"
				+ "_________________________________________\n";

		assertEquals(expected, ui.showLine());
		filePath.deleteOnExit();
	}
}