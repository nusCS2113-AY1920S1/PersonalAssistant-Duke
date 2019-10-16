package javacake.commands;

import javacake.*;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MegaListCommand extends Command {


    public String execute(ProgressStack progressStack, Ui ui, Storage storage, Profile profile) throws DukeException {

        try (Stream<Path> walk = Files.walk(Paths.get("src/main/resources/content/MainList"))) {
            List<String> result = walk.filter(Files::isDirectory).map(x -> x.toString()).collect(Collectors.toList());
            return String.join("", result);
        } catch (IOException e) {
            throw new DukeException(e.getMessage());
        }
    }

    public static void main(String args[]) throws DukeException {
        ProgressStack ps = new ProgressStack();
        Ui ui = new Ui();
        Storage s = new Storage();
        Profile p = new Profile();
        MegaListCommand mlc = new MegaListCommand();
        mlc.execute(ps, ui, s, p);
    }
}
