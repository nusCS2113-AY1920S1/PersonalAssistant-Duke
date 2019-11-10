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

    //@@author cctt1014
    public UserIcon() throws IOException {
        File iconDir = new File("dataFG/userCustomizedIcons");
        if (!iconDir.isDirectory()) {
            iconDir.mkdir();
        }
        File iconFile = new File("dataFG/iconPath.txt");
        if (!iconFile.isFile()) {
            iconFile.createNewFile();
            initialPath = "/images/DaUser.png";
            icon = new Image(this.getClass().getResourceAsStream(initialPath));
        } else {
            FileReader fileReader = new FileReader(iconFile);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            initialPath = bufferedReader.readLine();
            icon = new Image(initialPath);
            bufferedReader.close();
        }
    }

    public Image getIcon() {
        return icon;
    }

    public void changeIcon() throws IOException {
        FileWriter fileWriter = new FileWriter("dataFG/iconPath.txt", false);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Select a picture:");
        File defaultDirectory = new File(".");
        chooser.setInitialDirectory(defaultDirectory);
        File selectedFile = chooser.showOpenDialog(null);
        if (selectedFile == null) {
            bufferedWriter.write(initialPath);
            bufferedWriter.close();
            return;
        }
        Path from = Paths.get(selectedFile.toURI());
        Path to = Paths.get("dataFG/userCustomizedIcons/" + selectedFile.getName());
        Files.copy(from, to, StandardCopyOption.REPLACE_EXISTING);
        File newIcon = new File("dataFG/userCustomizedIcons/" + selectedFile.getName());
        icon = new Image(newIcon.toURI().toString());
        bufferedWriter.write(newIcon.toURI().toString());
        bufferedWriter.close();
    }
}