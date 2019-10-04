package Optix.commands;

import optix.Ui;
import optix.commands.shows.AddCommand;
import optix.commands.shows.DeleteOneCommand;
import optix.core.Storage;
import optix.util.ShowMap;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class DeleteOneCommandTest {

private ShowMap shows = new ShowMap();
private Ui ui = new Ui();
private File currentDir = new File(System.getProperty("user.dir"));
private File filePath = new File(currentDir.toString() + "\\src\\main\\data\\testOptix.txt");
private Storage storage = new Storage(filePath);
@Test
void execute() {
	AddCommand addTestShow1 = new AddCommand("Test Show 1","5/5/2020", 2000, 20);
	addTestShow1.execute(shows, ui, storage);
	DeleteOneCommand testCommand1 = new DeleteOneCommand("5/5/2020", "Test Show 1");
	testCommand1.execute(shows, ui, storage);
	String expected1 = "_________________________________________\n"
			+ "Noted. The show <Test Show 1> scheduled on <5/5/2020> has been removed.\n"
			+ "_________________________________________\n";
	assertEquals(expected1, ui.showLine());

	DeleteOneCommand testCommand2 = new DeleteOneCommand("4/5/2020", "Non-existent show");
	testCommand2.execute(shows, ui, storage);
	String expected2 = "_________________________________________\n"
			+ "Unable to find show called <Non-existent show> scheduled on <4/5/2020>.\n"
			+ "_________________________________________\n";
	assertEquals(expected2,ui.showLine());
	filePath.deleteOnExit();
	}
}