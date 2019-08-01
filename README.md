# ui-no-gradle

This branch contains code samples for Duke's JavaFX tutorial.

# Setting up

1. Clone this branch with: 

	> `git clone --single-branch --branch ui-no-gradle https://github.com/se-edu/duke.git`


1. Download the [JavaFX 11 SDK](https://gluonhq.com/products/javafx/) and unzip it.
1. Import the `libs` folder from the SDK into your project. 
   
   `File` > `Project Structure` > `New Project Library` > `From Java` > `{JAVAFX_HOME/libs}`
   
   Where `JAVAFX_HOME` is the directory in which you have unzipped the JavaFX SDK.
   
1. From `Run` > `Edit Configurations`, add the following lines into your `VM options` for each of the `main` classes.

    `--module-path {JAVAFX_HOME/libs} --add-modules javafx.controls,javafx.fxml`
    
    Where `JAVAFX_HOME` is the directory in which you have unzipped the JavaFX SDK.
