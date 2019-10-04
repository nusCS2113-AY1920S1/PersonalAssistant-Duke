package Optix.commands;

import optix.Ui;
import optix.commands.shows.AddCommand;
import optix.commands.shows.ListCommand;
import optix.core.Storage;
import optix.util.ShowMap;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class ListCommandTest {
	private ShowMap shows = new ShowMap();
	private Ui ui = new Ui();
	private File currentDir = new File(System.getProperty("user.dir"));
	private File filePath = new File(currentDir.toString() + "\\src\\main\\data\\testOptix.txt");
	private Storage storage = new Storage(filePath);

@Test
void execute() {
	// testing for an empty show list
	ListCommand testCommand1 = new ListCommand();
	testCommand1.execute(shows, ui, storage);
	String expected1 = "_________________________________________\n"
			+ "☹ OOPS!!! There are no shows in the near future.\n"
			+ "_________________________________________\n";
	assertEquals(expected1, ui.showLine());

	// testing for a filled show list
	AddCommand addShow1 = new AddCommand("dummy test 1","5/5/2020",2000,20);
	addShow1.execute(shows,ui,storage);
	AddCommand addShow2 = new AddCommand("dummy test 2", "6/5/2020",2000,20);
	addShow2.execute(shows,ui,storage);
	ListCommand testCommand2 = new ListCommand();
	testCommand2.execute(shows,ui,storage);
	String expected2 = "_________________________________________\n"
			+ "Here are the list of shows:\n"
			+ "1. dummy test 1 (on: 2020-05-05)\n"
			+ "2. dummy test 2 (on: 2020-05-06)\n"
			+ "_________________________________________\n";
	assertEquals(expected2,ui.showLine());
	filePath.deleteOnExit();
	}
}