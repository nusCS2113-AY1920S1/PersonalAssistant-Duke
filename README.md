[![Build Status](https://travis-ci.org/AY1920S1-CS2113-T13-1/main.svg?branch=master)](https://travis-ci.org/AY1920S1-CS2113-T13-1/main)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/7d04f3f11b98408999c532f54b787d37)](https://www.codacy.com/manual/Lucria/main?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=AY1920S1-CS2113-T13-1/main&amp;utm_campaign=Badge_Grade)
[![Coverage Status](https://coveralls.io/repos/github/AY1920S1-CS2113-T13-1/main/badge.svg?branch=master)](https://coveralls.io/github/AY1920S1-CS2113-T13-1/main?branch=master)

# ArchDuke - A CLI Project Manager with Kanban board
**Done by:**
  * Sean
  * Abhishek
  * Dillen
  * Cynthia
  * Jerry

## Introduction
* ArchDuke is a desktop Project Manager application. It has a GUI for better visualisation of the Kanban board and tasks
, but most of the user interactions will happen using a Command Line Interface (CLI).
* The Kanban board will also be printed onto command line for users that favour the usage of a CLI interface compared
 to a GUI.

# Table of Contents
* [Setting Up](#setting-up)
* [Usage](#usage)
* [Acknowledgements](#acknowledgements)
* [License](#license)

## Setting up

**Prerequisites**

* JDK 11
* Recommended: IntelliJ IDE
* Fork this repo to your GitHub account and clone the fork to your computer

**Importing the project into IntelliJ**

1.  Open IntelliJ (if you are not in the welcome screen, click `File` > `Close Project` to close the existing project dialog first).

2.  Set up the correct JDK version.
    * Click `Configure` > `Structure for new Projects` (in older versions of Intellij:`Configure` > `Project Defaults` > `Project Structure`).
    * If JDK 11 is listed in the drop down, select it. If it is not, click `New...` and select the directory where you installed JDK 11.
    * Click `OK`.
3.  Click `Import Project`.

4.  Locate the project directory and click `OK`.

5.  Select `Create project from existing sources` and click `Next`.

6.  Rename the project if you want. Click `Next`.

7.  Ensure that your src folder is checked. Keep clicking `Next`.

8.  Click `Finish`.

## Tutorials 

|Duke Increment   |Tutorial                                                                                                                                                                                       |
|---------------  |---------------                                                                                                                                                                                |
|`A-Gradle`       |[Gradle Tutorial](tutorials/gradleTutorial.md)                                                                                                                                                 |
|`Level-10`       |JavaFX tutorials:<br>→ [Part 1: Introduction to JavaFX][fx1]<br>→ [Part 2: Creating a GUI for Duke][fx2]<br>→ [Part 3: Interacting with the user][fx3]<br>→ [Part 4: Introduction to FXML][fx4]|

[fx1]: <tutorials/javaFxTutorialPart1.md>
[fx2]: <tutorials/javaFxTutorialPart2.md>
[fx3]: <tutorials/javaFxTutorialPart3.md>
[fx4]: <tutorials/javaFxTutorialPart4.md>

## Usage
You can refer to our user guide at this link:
*   [User Guide](./docs/UserGuide.adoc)

For more advanced users and developers, do refer to our Developer Guide:
*   [Developer Guide](./docs/DeveloperGuide.adoc)

## Acknowledgements 
  * This application was written as submission for our Team project under the module CS2113 Software Engineering and
 Object-Oriented Programming

  * Libraries used in this project: Natty?, GSON?, some Text to Speech?, JavaFX?, JUnit5

## License
MIT Licensesd
