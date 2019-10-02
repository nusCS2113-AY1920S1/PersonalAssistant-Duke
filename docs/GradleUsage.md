## Gradle Usage

This assumes the project has been imported as a gradle project. If not, follow the instructions at [Gradle Tutorial](../tutorials/gradleTutorial.md).

Gradle Commands | Usage
-----------------|--------------------
`gradlew build` | Runs all available gradle tasks
`gradlew task`| Displays all available gradle tasks
`gradlew checkstyleMain` | Checks source code against a given code style
`gradlew checkstyleTest` | Checks test code against a given code style
`gradlew run` | Runs the main class as specified in build.gradle
`gradlew properties` | Displays the properties of this gradle project
`gradlew shadowjar` | Compiles and creates a standalone executable .jar file

### Using build.gradle
The dependencies and properties of the gradle project can be changed in [build.gradle](build.gradle).

**Shadowjar**

When `gradlew shadowjar` is run, it will create a .jar file in the format {archiveBaseName}-{archiveVersion}.jar.
Modify the the following part in the `build.gradle` file to update the name of the jar file.
```
shadowJar {
	archiveBaseName = "duke"
	archiveVersion = "0.1.0"
	archiveClassifier = null
	archiveAppendix = null
}
```
**Working directory**

The following lines determines the working directory of the gradle project when `gradlew run` is used.
For the gradle project to detect the save file in `../data`, the working directory must be set to `../main/bin`.
```
File runningDir = new File('../main/bin')
runningDir.mkdirs()
tasks.run.workingDir = runningDir
```
**Main class**

This part of `build.gradle` determines which class is to be the main class of the project. 

Using `javafx.Launcher` means Duke 2.0 will launch with a GUI, while using `main.Duke` means Duke 2.0 will run in console mode only.
```
application {
	// Change this to your main class.
	mainClassName = "javafx.Launcher"
	//mainClassName = "main.Duke"
}
```
