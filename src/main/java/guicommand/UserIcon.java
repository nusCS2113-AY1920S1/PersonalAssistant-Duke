package guicommand;

import javafx.scene.image.Image;
import javafx.stage.FileChooser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class UserIcon {

    private Image icon;
    private String initialPath;

    //@@author cctt1014

    /**
     * This constructor initialize the user icon related files and directory. Then it will
     * load the initial icon depending on whether the file exists or not. If the initial file
     * does not exist, the constructor will assign the default icon. Otherwise, it will
     * load the icon according to the data in the existing file.
     * @throws IOException The initial exception
     */
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

    /**
     * The getter which is used to return the current user icon.
     * @return THe image of the current user icon.
     */
    public Image getIcon() {
        return icon;
    }

    /**
     * This method handle the command to change user icon. It will call a file selector
     * for the user to choose his/her own pictures. Then it will copy the image to a specific
     * folder to store and save it as the current user icon.
     * @throws IOException The IOE exception
     */
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