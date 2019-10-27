# User Guide

## Introduction
WordUp (WU) is a desktop-based application for english language learners who prefer having a digital version of a personal vocabulary booklet. More importantly, WU is great for on-the-go revision, which is not easily achieved with traditional hardcopy wordbanks. WU provides convenience and efficiency of adding words in a wordbank, and allows users to manage their word collection faster with a Command Line Interface (CLI), as well as having a Graphical User Interface (GUI) for more interactive functions and for better visualisation of content stored. 

### Quick start
1.  Ensure you have Java 11 or above installed on your computer.
2. Download the latest wordbank.jar here.
3. Copy the file to the folder you want to use as the home folder for your Word Bank.
4. Double-click the file to start the app. The GUI should appear in a few seconds.
[insert GUI mockup]
5. Type the command in the command box and press Enter to execute it. e.g. typing help and pressing Enter opens up the window showing a list of commands available.
6. Some example commands you could try:
   - list shows the list of words currently stored in the Word Bank in alphabetical displayOrder or otherwise customised in the app settings
   - add w/life m/the existence of an individual human being or animal : adds a word tagged by “w/” with its corresponding meaning tagged “m/” into the Word Bank
   - delete freedom : deletes the Word Bank entry of the word “freedom”
   - exit : exits the application
7. Refer to Section 3, “Features” for details of each command.

## Features
*Command Overview*
* `Add` add w/WORD [w/MEANING] [r/REMINDER] [t/TAG]
* `List` list [t/EXISTING_TAG] [o/ORDER]
* `Search` search w/WORD_TO_BE_SEARCHED
* `Edit` edit w/WORD_TO_BE_EDITED [w/EDITED_WORD] [m/EDITED_MEANING]
* `Tag` tag w/WORD_TO_BE_TAGGED t/NEW_TAG
* `Delete` delete w/TAGGED_WORD t/TAG_TO_BE_DELETED
* `Help` help
* `Exit` exit

*Command Format*
- Words in UPPER_CASE are parameter values to be supplied by the user e.g. in add w/WORD m/MEANING, WORD and MEANING are parameters to be replaced by user input, such as in the form of w/life m/the existence of an individual human being or animal.
- Items in square brackets are optional e.g. r/REMINDERDATE [TIME] can be used as r/24/02/2300 0900 or r/24/02/2300.
- Items with … after them can be used multiple times including zero times e.g. [t/TAG]... can be used as t/negative, t/emotions, t/lesson3
- Parameters can be in any displayOrder e.g. if the command supplies m/MEANING w/WORD is also acceptable.

*Details*
1. **Viewing commands : `help`**
  Format: help

2. **Adding a word: `add`**
  Adds a word to the Word Bank
  Format: add w/WORD w/MEANING [r/REMINDER] [t/TAG]

3. **Listing all words : `list`**
	Shows a list of words categorised as specified
	Format: list [t/EXISTING_TAG] [o/ORDER] 
  e.g. list t/emotions o/asc shows the list of words tagged “emotions” in alphabetical displayOrder
	! Not supplying any tags will show a list containing all words listed in alphabetical displayOrder
	! List of possible o/ORDER values: 
    asc - displayOrder the list in ascending alphabetical displayOrder
    desc - displayOrder the list in descending alphabetical displayOrder

4. **Find meaning of a word: `search`**
	Shows the meaning of a specific word
	Format: search w/WORD_TO_BE_SEARCHED

5. **Editing an entry: `edit`**
  Edits the word and/or meaning specified
  Format: edit w/WORD_TO_BE_EDITED [w/EDITED_WORD] [m/EDITED_MEANING]
  At least one of the optional fields must be provided

6. **Tagging an entry: `tag`**
  Assigns a tag to an existing word in the database
  Format: tag w/WORD_TO_BE_TAGGED t/NEW_TAG

7. **Deletion : `delete`**
   - Deleting an entry: `delete_word`
    Deletes the entry of the word specified
    Format: delete w/WORD_TO_BE_DELETED
   - Deleting a tag on a word: `delete_tag`
    Deletes the tag of the word specified
    Format: delete w/TAGGED_WORD t/TAG_TO_BE_DELETED

8. **Exiting the program: `exit`**
  Exits the program
  Format: exit

9. **Saving the data**
  Word Bank entries are automatically saved to the hard disk whenever there is a command entered resulting in a change in data

10. Creating quizzes [coming in v2.0]
{help users practice learning on a regular basis}

11. Show search history [coming in v2.0]
{List out the most recent searches by users}

### FAQ
Q: Where can I find the text file containing the list of words I have stored?
A: Navigate to the home folder of this application, then go to the data folder. Words with the same tag e.g. emotions share the same text file i.e. all words tagged under emotions are saved in the file named emotions.txt

