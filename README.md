# Duke 2.0

A program for those who "prefer to use a desktop app for researching on degrees from NUS' Faculty of Engineering".

# Setting up

For those who wish to modify the source files for their own project, or just to see how Duke 2.0 works.

**Prerequisites**

* JDK 11
* Recommended: IntelliJ IDE
* Fork this repo to your GitHub account and clone the fork to your computer
* Gradle integration (Should be done automatically. If not, see Tutorials)

**Importing the project into IntelliJ**

1. Open IntelliJ (if you are not in the welcome screen, click `File` > `Close Project` to close the existing project dialog first).
1. Set up the correct JDK version.
   * Click `Configure` > `Structure for new Projects` (in older versions of Intellij:`Configure` > `Project Defaults` > `Project Structure`).
   * If JDK 11 is listed in the drop down, select it. If it is not, click `New...` and select the directory where you installed JDK 11.
   * Click `OK`.
1. Click `Import Project`.
1. Locate the project directory and click `OK`.
1. Select `Create project from existing sources` and click `Next`.
1. Rename the project if you want. Click `Next`.
1. Ensure that your src folder is checked. Keep clicking `Next`.
1. Click `Finish`.
1. If there is an option to import this project as a gradle project, accept it.

# Tutorials 

Duke Components | Tutorial
---------------|---------------
`Gradle` | [Gradle Tutorial](tutorials/gradleTutorial.md)
`TextUiTesting` | [Text UI.UI Testing Tutorial](tutorials/textUiTestingTutorial.md)
`JavaFX` | JavaFX tutorials:<br>→ [Part 1: Introduction to JavaFX][fx1]<br>→ [Part 2: Creating a GUI for JavaFX.Main.Duke][fx2]<br>→ [Part 3: Interacting with the user][fx3]<br>→ [Part 4: Introduction to FXML][fx4]

[fx1]: <tutorials/javaFxTutorialPart1.md>
[fx2]: <tutorials/javaFxTutorialPart2.md>
[fx3]: <tutorials/javaFxTutorialPart3.md>
[fx4]: <tutorials/javaFxTutorialPart4.md>

# Gradle Usage

This assumes the project has been imported as a gradle project. If not, follow the instructions at [Gradle Tutorial](tutorials/gradleTutorial.md).

Gradle Commands | Usage
-----------------|--------------------
`gradlew build` | Runs all available gradle tasks
`gradlew task`| Displays all runable gradle tasks
`gradlew checkstyleMain` | Checks source code against a given code style
`gradlew checkstyleTest` | Checks test code against a given code style
`gradlew run` | Runs the main class as specified in gradle.build
`gradlew properties` | Displays properties of this gradle project

# UI

Insert mock up UI here
