package guicommand;

import javafx.scene.image.Image;
import javafx.stage.FileChooser;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class UserIcon {

    private Image icon;
    private String initialPath;

    //@@ cctt1014
    public UserIcon() throws IOException {
        FileReader fileReader = new FileReader("data/iconPath.txt");
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String iconPath = bufferedReader.readLine();
        initialPath = iconPath;
        icon = new Image(this.getClass().getResourceAsStream(iconPath));
        bufferedReader.close();
    }

    public Image getIcon() {
        return icon;
    }

    public void changeIcon() throws IOException {
        FileWriter fileWriter = new FileWriter("data/iconPath.txt", false);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Select a picture:");
        File defaultDirectory = new File("../");
        chooser.setInitialDirectory(defaultDirectory);
        File selectedFile = chooser.showOpenDialog(null);
        if (selectedFile == null) {
            bufferedWriter.write(initialPath);
            bufferedWriter.close();
            return;
        }
        Path from = Paths.get(selectedFile.toURI());
        Path to = Paths.get("src/main/resources/images/" + selectedFile.getName());
        Files.copy(from, to, StandardCopyOption.REPLACE_EXISTING);
        icon = new Image(this.getClass().getResourceAsStream("/images/" + selectedFile.getName()));
        bufferedWriter.write("/images/" + selectedFile.getName());
        bufferedWriter.close();
    }



}