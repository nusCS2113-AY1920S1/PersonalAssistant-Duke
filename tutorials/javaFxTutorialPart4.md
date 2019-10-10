# JavaFX Tutorial 4 – FXML 

While we have produced a fully functional prototype, there are a few major problems with our application.

1. The process of visually enhancing the GUI is long and painful:
   * Does the `TextField` need to be 330px or 325px wide? 
   * How much padding is enough padding to look good?

   Every small change requires us to rebuild and run the application.  

1. Components are heavily dependent on each other:
   Why does `JavaFX.Main` need to know that `JavaFX.DialogBox` needs a `Label`? 
   What happens if we change the `Label` to a custom `ColoredLabel` in the future?  
    
    We need to minimize the amount of information each control needs to know about another.
    Otherwise, making changes in the future will break existing features.

1. The code is untidy and long:
   Why is all the code in one place?

   The `JavaFX.Main` class attempts to do it all. 
   Code for visual tweaks, listeners and even utility methods are all in one file.
   This makes it difficult to find and make changes to existing code.

FXML is a XML-based language that allows us to define our user interface. Properties of JavaFX objects can be defined in the FXML file. For example:  
```xml
 <TextField fx:id="userInput" layoutY="558.0" onAction="#handleUserInput" prefHeight="41.0" prefWidth="324.0" AnchorPane.bottomAnchor="1.0" />
```

The FXML snippet define a TextField similar to the one that we programmatically defined previous in Tutorial 2. Notice how concise FXML is compared to the plain Java version.

Let's return to JavaFX.Main.Duke and convert it to use FXML instead.

# Rebuilding the Scene using FXML

Scene Builder is a tool developed by Oracle and currently maintained by Gluon. It is a What-You-See-Is-What-You-Get GUI creation tool. [Download](https://gluonhq.com/products/scene-builder/#download) the appropriate version for your OS and install it.

Create the following files in `src/main/resources/view`:

**JavaFX.MainWindow.fxml**
```xml
<?xml version="1.0" encoding="UTF-8"?>

<?import javafx.scene.control.Button?>
<?import javafx.scene.control.ScrollPane?>
<?import javafx.scene.control.TextField?>
<?import javafx.scene.layout.AnchorPane?>
<?import javafx.scene.layout.VBox?>

<AnchorPane maxHeight="-Infinity" maxWidth="-Infinity" minHeight="-Infinity" minWidth="-Infinity" prefHeight="600.0" prefWidth="400.0" xmlns="http://javafx.com/javafx/8.0.171" xmlns:fx="http://javafx.com/fxml/1" fx:controller="JavaFX.MainWindow">
  <children>
    <TextField fx:id="userInput" layoutY="558.0" onAction="#handleUserInput" prefHeight="41.0" prefWidth="324.0" AnchorPane.bottomAnchor="1.0" />
    <Button fx:id="sendButton" layoutX="324.0" layoutY="558.0" mnemonicParsing="false" onAction="#handleUserInput" prefHeight="41.0" prefWidth="76.0" text="Send" />
    <ScrollPane fx:id="scrollPane" hbarPolicy="NEVER" hvalue="1.0" prefHeight="557.0" prefWidth="400.0" vvalue="1.0">
      <content>
        <VBox fx:id="dialogContainer" prefHeight="552.0" prefWidth="388.0" />
      </content>
    </ScrollPane>
  </children>
</AnchorPane>
```

**JavaFX.DialogBox.fxml**
```xml
<?xml version="1.0" encoding="UTF-8"?>

<?import javafx.geometry.Insets?>
<?import javafx.scene.control.Label?>
<?import javafx.scene.image.ImageView?>
<?import javafx.scene.layout.HBox?>

<fx:root alignment="TOP_RIGHT" maxHeight="1.7976931348623157E308" maxWidth="1.7976931348623157E308" prefWidth="400.0" type="javafx.scene.layout.HBox" xmlns="http://javafx.com/javafx/8.0.171" xmlns:fx="http://javafx.com/fxml/1">
  <children>
    <Label fx:id="dialog" text="Label" wrapText="true" />
    <ImageView fx:id="displayPicture" fitHeight="99.0" fitWidth="99.0" pickOnBounds="true" preserveRatio="true" />
  </children>
  <padding>
    <Insets bottom="15.0" left="5.0" right="5.0" top="15.0" />
  </padding>
</fx:root>
```

1. Let’s explore the provided FXML files in Scene Builder. 
    
    Running the tool brings up the main screen.
    Select `Open Project` > `src/main/resources/view/JavaFX.MainWindow.fxml`. Inspect each control and its properties.

   ![SceneBuilder opening JavaFX.MainWindow.fxml](assets/SceneBuilder.png)

1. On the right accordion pane, you can modify the properties of the control that you have selected. Try changing the various settings and see what they do!
 
1. On the left accordion, you can see that we have set the controller class to `JavaFX.MainWindow`. 
We will get to that later.
 
   ![Controller for JavaFX.MainWindow](assets/MainWindowController.png)

1. Let’s repeat the process for `JavaFX.DialogBox`.
   The main difference here is that JavaFX.DialogBox checks `Use fx:root construct` and _does not define a controller class_. 

   ![Settings for JavaFX.DialogBox](assets/DialogBoxController.png)

## Using Controllers

As part of the effort to separate the code handling JavaFX.Main.Duke's logic and UI.UI, let's _refactor_ the UI.UI-related code to its own class.
We call these UI.UI classes _controllers_. 

Let's implement the `JavaFX.MainWindow` controller class that we specified in `JavaFX.MainWindow.fxml`.

**JavaFX.MainWindow.java**
```java
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
/**
 * Controller for JavaFX.MainWindow. Provides the layout for the other controls.
 */
public class JavaFX.MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private JavaFX.Main.Duke duke;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/DaUser.png"));
    private Image dukeImage = new Image(this.getClass().getResourceAsStream("/images/DaDuke.png"));

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    public void setDuke(JavaFX.Main.Duke d) {
        duke = d;
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing JavaFX.Main.Duke's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        String response = duke.getResponse(input);
        dialogContainer.getChildren().addAll(
                JavaFX.DialogBox.getUserDialog(input, userImage),
                JavaFX.DialogBox.getDukeDialog(response, dukeImage)
        );
        userInput.clear();
    }
}
```

The `@FXML` annotation marks a `private` or `protected` member and makes it accessible to FXML despite its modifier.
Without the annotation, we will have to make everything `public` and expose our UI.UI to unwanted changes.

The `FXMLLoader` will map the a control with a `fx:id` defined in FXML to a variable with the same name in its controller.
Notice how in `JavaFX.MainWindow`, we can invoke `TextField#clear()` on `userInput` and access its content just as we did in the previous example.
Similarly, methods like private methods like `handleUserInput` can be used in FXML when annotated by `@FXML`. 

## Using FXML in our application

Let's create a new `JavaFX.Main` class as the bridge between the existing logic in `JavaFX.Main.Duke` and the UI.UI in `JavaFX.MainWindow`.

**JavaFX.Main.java**
```java
@Override
import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * A GUI for JavaFX.Main.Duke using FXML.
 */
public class JavaFX.Main extends Application {

    private JavaFX.Main.Duke duke = new JavaFX.Main.Duke();

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(JavaFX.Main.class.getResource("/view/JavaFX.MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);
            fxmlLoader.<JavaFX.MainWindow>getController().setDuke(duke);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
```

Again, we can interact with the `AnchorPane` defined in the FXML as we would have if we created the `AnchorPane` ourselves.

For our custom `JavaFX.DialogBox`, we did not define a controller so let's create a controller for it.

**JavaFX.DialogBox.java**
```java
import java.io.IOException;
import java.util.Collections;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

/**
 * An example of a custom control using FXML.
 * This control represents a dialog box consisting of an ImageView to represent the speaker's face and a label
 * containing text from the speaker.
 */
public class JavaFX.DialogBox extends HBox {
    @FXML
    private Label dialog;
    @FXML
    private ImageView displayPicture;

    private JavaFX.DialogBox(String text, Image img) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(JavaFX.MainWindow.class.getResource("/view/JavaFX.DialogBox.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        dialog.setText(text);
        displayPicture.setImage(img);
    }

    /**
     * Flips the dialog box such that the ImageView is on the left and text on the right.
     */
    private void flip() {
        ObservableList<Node> tmp = FXCollections.observableArrayList(this.getChildren());
        Collections.reverse(tmp);
        getChildren().setAll(tmp);
        setAlignment(Pos.TOP_LEFT);
    }

    public static JavaFX.DialogBox getUserDialog(String text, Image img) {
        return new JavaFX.DialogBox(text, img);
    }

    public static JavaFX.DialogBox getDukeDialog(String text, Image img) {
        var db = new JavaFX.DialogBox(text, img);
        db.flip();
        return db;
    }
}
```

When we create a new instance of `JavaFX.DialogBox`, we set both the controller and root Node to `JavaFX.DialogBox`. 
From this point onwards we can interact with `JavaFX.DialogBox` as we have in the previous tutorials.

The last change that we have to make is to point our `JavaFX.Launcher` class in the right direction:
In `JavaFX.Launcher.java`
```java
//...    
Application.launch(JavaFX.Main.class, args);
//...
```
[todo]: # (Discussion on the fx:root pattern.)

## Exercises

1. Convert `JavaFX.MainWindow` to use the `fx:root` construct.
1. Extend `JavaFX.MainWindow` to have a `Stage` as a root Node.
1. Customize the appearance of the application further with CSS.

--------------------------------------------------------------------------------
**Authors:**
* Initial Version: Jeffry Lum
