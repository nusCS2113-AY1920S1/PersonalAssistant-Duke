# User Guide

The purpose of this user guide is to give a brief outline on how to use the different
features of the application BetterDuke.


## Table of Contents

- Introduction
- Quick Start
- Features
   - Adding task: add
   - Delete task: delete
   - Listing all tasks: list
   - Search for specific taskings using keywords: find
   - Set reminder for upcoming task: remind
   - Labeling task: label
   - Set reminder for Todo tasks which have to be done within a certain period: todo reminder
   - Viewing help : help
   - Input recurring activities
   - Exiting the program : bye
   - Saving the data
   - Strike off tasks and track the progress of the week (Coming in v1.4)
   - Mark task as important : markAs (Coming in v1.4)
   - Track the task completion rate (Coming in v1.4)
   - Customise light and dark theme: custom (Coming in v1.4)
   - Save file encryption (Coming in v2.0)
   - Sync timetable with nusmods (Coming in v2.0)
   - Sync timetable with external calendars (e.g. Google calendar) (Coming in v2.0)
- FAQ
- Command Summary


# 1. Introduction
    Betterduke is a desktop application for NUS students with poor time management. 
    Students, more often than not, are on their laptops, hence Betterduke
    will provide a convenient platform for students to toggle between checking their timetables and
    scheduling their tasks. More importantly, Betterduke is optimized for those who
    prefer to work with a Command Line Interface (CLI) while still having the benefits
    of a Graphical User Interface (GUI). If you can type fast, Betterduke can
    schedule your tasks faster than traditional GUI apps. ​ Enjoy!
# 2. Quick Start
    Ensure you have Java ​ 11 ​or above installed in your Computer.
    Installation guide:
       1. Download the latest jar file here
   ​[JAR](https://github.com/AY1920S1-CS2113T-W12-4/main/blob/master/jar-release/v1.3.1.jar)
   
       2. Copy the file to the folder you want to use as the home folder for your
          Address Book.
       3. Double-click the file to start the app. The GUI should appear in a few
          seconds.
 <img src="https://github.com/AY1920S1-CS2113T-W12-4/main/blob/master/docs/images/UG%20images/Example%20UI1.png" width = 800 length = 450>

         4. The Command line at the bottom is where users will input their commands
        and the response box is where the system will return it’s output.
        5. Type the command in the command box and press **Submit** to execute it.
  <img src="https://github.com/AY1920S1-CS2113T-W12-4/main/blob/master/docs/images/UG%20images/Example%20UI2.png" width = 800 length = 450>
       
        6. E.g. typing "help"  and pressing **Submit** will open the help window.
        7. Some example commands you can try:
            a.     Show workload     ​ : show workload for following week’s timetable
            b.     Filter CS2113T     ​ : show tasks which contains keyword
               “CS2113T”
            c.     bye     ​: exits the app
        Refer to [Features](#Features)​ for details of each command.


# 3. Features
   ## Command Format
    Words in ​UPPER_CASE​ are the parameters to be supplied by the user   
    e.g. in ​add\d MODCODE, DESCRIPTION​ is a parameter which can be used as   
    ​add\d CS2101 tutorial 6
    
  ##  3.1. Adding task: ​ add
    Adds a task to the list
      - Allow users to add in events or deadline, specifying NUS module code,
      task description, date and time
      - Clash detection added in the add function would inform user about 
      an event happening at the same time and same day preventing clashes in schedules
      - Module code checker to ensure Module code added is valid
   
      Format    
       -for event : ​add/e Mod_Code TASK_DESCRIPTION /at
       DATE(DD/MM/YYYY or week x day) /from TIME /to TIME
       -for deadline :​add/d Mod_Code TASK_DESCRIPTION /by
       DATE(DD/MM/YYYY or week x day) TIME
           Example:    
       add/e CS2101 assignment /at 12/08/2019 /from 1200 /to
       1300
##    3.2. Done task: done
      Complete a task from the list
      Format: 
      -for event : done/e Mod_Code TASK_DESCRIPTION /at 
      DATE(DD/MM/YYYY or week x day) /from TIME /to TIME
      -for deadline :done/d Mod_Code TASK_DESCRIPTION /by 
      DATE(DD/MM/YYYY or week x day) TIME
      Example:
      done/e CS2101 assignment /at 12/08/2019 /from 1200 /to 1300
      done/d CS2101 assignment /by week 1 mon 1200

 ##   3.3. Delete task: ​ delete
    Delete a task from the list
    Format:    
    -for event : ​delete/e Mod_Code TASK_DESCRIPTION /at
    DATE(DD/MM/YYYY or week x day) /from TIME /to TIME
    -for deadline :​delete/d Mod_Code TASK_DESCRIPTION /by
    DATE(DD/MM/YYYY or week x day) TIME
    Example:    
    delete/d CS2101 assignment /by week 1 mon 1200
    

 ##   3.4. Listing all tasks: ​ list (Coming in v1.4)
    Shows the list of all tasks in the schedule.
    Format ​:​ ​list MODULE_CODE
    Example ​:
    list CS2100
    Displays the list of tasks in todo

  ##  3.5. Search for specific taskings using keywords:     ​     filter  
    Find matching taskings using given keywords.    
    Format     ​: ​filter KEYWORD    
    ● Search is not case sensitive    
    ● Every tasking made by the user will be searched for    
    ● Only the full words will be matched e.g. ​books​ will not match​ book    
    ● List of all task matching the keyword will be displayed for the user    
    ●     Example:        
    filter book

    Displays:
    1. Buy book

  ##  3.6. Set reminder for upcoming task:     ​     remind    
    This sets a reminder for current task. A notification will pop-up when
    set timing is met.    
    Format: 
      -Set Reminder: remind/set Mod_Code TASK_DESCRIPTION /by 
      DATE(DD/MM/YYYY or week x day) TIME /to      
      DATE(DD/MM/YYYY or week x day) TIME
      -Remove Reminder : remind/rm Mod_Code TASK_DESCRIPTION /by
      DATE(DD/MM/YYYY or week x day) TIME /to
      DATE(DD/MM/YYYY or week x day) TIME

      Example:
      remind/set CS2100 project /by 1/11/2019 1200 /to week 11 fri 0800
      remind/rm CS2100 project /by 1/11/2019 1200 /to week 11 fri 0800

      The date and time of /by and /to if remind/rm is called has to be 
      same as the date and time of /by and /to when remind/set was called.


   ## 3.7. Labeling task: label (Coming in v1.4)
   Tags Task with a label so all related tasks can categorise total    
    Format:     ​ ​label td/TASK_DESCRIPTION tag/TAG_NAME    

   ## 3.8. View the previous inputs: show/previous
      Displays previous inputs entered based on your choice
      Format: show/previous x where x is an integer
            Or show/previous <command type>
      Examples of command type: add/d, add/d, delete/d

      Example: 
      show/previous 3
      This command will show the 3 previous input from the current
      input in the response box as shown below.
      1. add/d CS2105 assignment 1 /by 29/10/2019 1300
      2. add/d CS2105 practical test by 29/10/2019 1300
      3. Find 2 hours

   ## 3.9.Retrieve the previous input that users want to re-enter: retrieve/previous
      Set the command line text to the chosen previous command retrieved
      Format: retrieve/previous x where x is an integer

      Example: 
      retrieve/previous 2
      For instance the show previous list have: 	
      add/d CS2105 assignment 1 /by 29/10/2019 1300
      add/d CS2105 practical test by 29/10/2019 1300
      Find 2 hours

      The command retrieve/previous will set the text in the command line 
      to be add/d CS2105 practical test by 29/10/2019 1300 so that edits
      can be made easily without having to re-type the whole command. 

   ## 3.10. Finding earliest free time based on the given schedule    
      Finds 5 earliest free time block with a given duration in hours, 
      the information found will be displayed in the ‘Response’ Box.
      Format: find ‘x’ hours 
      where ‘x’ is an integer between 1 - 16

   ## 3.11. Retrieve earliest free time found based on the given schedule : retrieve/ft 
      Retrieves anything 1 of the 5 earliest free time block offered and
      populates into the command line
      Format: retrieve/ft ‘x’
      where ‘x’ is option selected in integer between 1 - 5

   ## 3.12. Select the week :  Week
      Generates all assignment entries in the week selected
      Format: Week ‘x’
      where ‘x’ is an integer between 1 - 13 or
      ‘x’ is non-integer either 'recess', 'reading', or 'exam'.

   ## 3.13. Viewing help : help 
         Shows list of commands and correct format
         Format: help

   ## 3.14. Show recommended workload 
      This will show a recommended workload schedule for the immediate coming week to the user 
      Format: /show workload 
   ## 3.15. Add or remove recurring activities 
         This will help user add or remove event that is recurring over a period of time.
         Format: 
         Add recurring task weekly:  recur/weekly Mod_Code TASK_DESCRIPTION 
         /start Start_Date(DD/MM/YYYY or week x day) /to 
         End_Date(DD/MM/YYYY or week x day) /from TIME /to TIME
         Remove a weekly recurring task that was set: recur/rmweekly Mod_Code TASK_DESCRIPTION
         /start Start_Date(DD/MM/YYYY or week x day) /to
         End_Date(DD/MM/YYYY or week x day) /from TIME /to TIME
         Add recurring bi-weekly task: recur/biweekly Mod_Code TASK_DESCRIPTION 
         /start Start_Date(DD/MM/YYYY or week x day) /to 
         End_Date(DD/MM/YYYY or week x day) /from TIME /to TIME
         Remove a bi-weekly recurring task that was set: recur/rmbiweekly Mod_Code TASK_DESCRIPTION
         /start Start_Date(DD/MM/YYYY or week x day) /to
         End_Date(DD/MM/YYYY or week x day) /from TIME /to TIME

         Example: 
         Add recurring task weekly:  recur/weekly CS2101 assignment /start 
         10/10/2019 /to 31/11/2019 /from 1200 /to 1400
         Remove a weekly recurring task that was set: recur/rmweekly CS2101 assignment /start
         10/10/2019 /to 31/11/2019 /from 1200 /to 1400
         Add recurring bi-weekly task: recur/biweekly CS2101 assignment /start 10/10/2019
         /to 31/11/2019 /from 1200 /to 1400
         Remove a bi-weekly recurring task that was set: recur/rmbiweekly CS2101 assignment /start
         10/10/2019 /to 31/11/2019 /from 1200 /to 1400

   ## 3.16. Exiting the program : bye    
    Exits the program.    
    Format:bye

   ## 3.17. Saving the data    
    All taskings are saved automatically when user wants to modify their
    schedule. No manual saving is required.

   ## 3.18. Strike off tasks and track the progress of the week (Coming in v1.4)    
    To help users ensure they are on schedule.

   ## 3.19. Mark task as important : markAs (Coming in v1.4)

      Add a keyword “Important !” beside the task as the specific INDEX
      Format: markAs INDEX

      Example:
      markAs 2
      The 2nd task in the list will have a label (Important !) i.e. read book (Important !)


   ## 3.20. Track the task completion rate (Coming in v1.4)    
    To allow users to track their productivity.

   ## 3.21. Customise light and dark theme:  custom (Coming in v1.4)
    Changes the GUI between a light theme and dark theme.    
    Format: custom LIGHT_OR_DARK    
    Example:        
    custom dark    
    This changes the theme of the GUI to a dark theme.

   ## 3.22. Save file encryption (Coming in v2.0)    
    A privacy choice to enable/disable encryption of data files.

   ## 3.23. Sync timetable with nusmods (Coming in v2.0)    
    Added option to sync timetable with nusmods to properly match
    school schedule.

   ## 3.24. Sync timetable with external calendars (e.g.Google calendar) (Coming in v2.0)    
    Added option to sync planned activities in external calendars to
    match with BetterDuke.    


# 4.     FAQ    

        Q:     ​ ​Do i have to install the application?    
        A:     ​ No you can just run the [betterduke].exe file
    
        Q:     ​ If I am unsure about the commands, how do i get help?    
        A:     ​ Just enter “help” into the command line and BetterDuke will show you a
                comprehensive guide on how to use our app
    
        Q     ​:How do I find specific tasks based on a keyword?    
        A     ​: You can make use of the ​filter​ function to look for such tasks.
    
# 5.     Command Summary    
      a) Add:
      -for event : add/e Mod_Code TASK_DESCRIPTION /at DATE(DD/MM/YYYY or week x day) /from TIME /to TIME
      -for deadline :add/d Mod_Code TASK_DESCRIPTION /by DATE(DD/MM/YYYY or week x day) TIME
      e.g 
      add/e CS2101 assignment /at 12/08/2019 /from 1200 /to 1300
      add/d CS2101 assignment /by week 1 mon 1200
       Done:
       -for event : done/e Mod_Code TASK_DESCRIPTION /at DATE  (DD/MM/YYYY or week x day) /from TIME to TIME
      -for deadline :done/d Mod_Code TASK_DESCRIPTION /by DATE(DD/MM/YYYY or week x day) TIME
      e.g
      done/e CS2101 assignment /at 12/08/2019 /from 1200 /to 1300
      done/d CS2101 assignment /by week 1 mon 1200

      b) Delete:
       -for event : delete/e Mod_Code TASK_DESCRIPTION /at DATE  (DD/MM/YYYY or week x day) /from TIME to TIME
      -for deadline : delete/d Mod_Code TASK_DESCRIPTION /by DATE(DD/MM/YYYY or week x day) TIME
      e.g.
      delete/e CS2101 assignment /at 12/08/2019 /from 1200 /to 1300
      delete/d CS2101 assignment /by week 1 mon 1200
      
      c) List: list MODULE_CODE
      e.g. list CS2100
      Filter: filter KEYWORD
      e.g. filter book
      
      d) Remind: 
      -Set Reminder: remind/set Mod_Code TASK_DESCRIPTION /by DATE(DD/MM/YYYY or week x day) TIME 
      /to DATE(DD/MM/YYYY or week x day) TIME
      -Remove Reminder : remind/rm Mod_Code TASK_DESCRIPTION /by DATE(DD/MM/YYYY or week x day) TIME 
      to DATE(DD/MM/YYYY or week x day) TIME
      e.g. 
      remind/set CS2100 project /by 1/11/2019 1200 /to week 11 fri 0800
      remind/rm CS2100 project /by 1/11/2019 1200 /to week 11 fri 0800
      Show recommended workload: /show workload
      
      e)Recur:  
      -Add recurring task weekly:  recur/weekly Mod_Code TASK_DESCRIPTION 
      /start Start_Date(DD/MM/YYYY or week x day) /to End_Date(DD/MM/YYYY or week x day) /from TIME /to TIME
      -Remove a weekly recurring task that was set: recur/rmweekly Mod_Code TASK_DESCRIPTION
      /start Start_Date(DD/MM/YYYY or week x day) /to End_Date(DD/MM/YYYY or week x day) /from TIME /to TIME
      -Add recurring bi-weekly task: recur/biweekly Mod_Code TASK_DESCRIPTION /start
      Start_Date(DD/MM/YYYY or week x day) /to End_Date(DD/MM/YYYY or week x day) /from TIME /to TIME
      -Remove a bi-weekly recurring task that was set: recur/rmbiweekly Mod_Code TASK_DESCRIPTION /start 
      Start_Date(DD/MM/YYYY or week x day) /to End_Date(DD/MM/YYYY or week x day) /from TIME /to TIME 
      e.g.  
      recur/weekly CS2101 assignment /start 10/10/2019 /to 31/11/2019 /from 1200 /to 1400
      recur/rmweekly CS2101 assignment /start 10/10/2019 /to 31/11/2019 /from 1200 /to 1400
      recur/biweekly CS2101 assignment /start 10/10/2019 /to 31/11/2019 /from 1200 /to 1400
      recur/rmbiweekly CS2101 assignment /start 10/10/2019 /to 31/11/2019 /from 1200 /to 1400
      Label: label td/TASK_DESCRIPTION tag/TAG_NAME
      e.g. label td/Duke user guide tag/CS2113T week 6
      
      f) Todo reminder: ‘TASK_DESCRIPTION’ (from sd/START DATE to ed/END DATE) ,
      where ‘TASK_DESCRIPTION’ is the task
      e.g. Fishing (from 01/02/2019 to 04/02/2019)
      
      g) Show previous: show/previous x , where ‘x’ is an integer OR show/previous <command type> 
      e.g. show/previous 3 OR  show/previous add/d
      
      h) Retrieve previous:  retrieve/previous x , where ‘x’ is an integer
      e.g. retrieve/previous 2
      
      i) Find earliest free time:  find ‘x’ hours , where ‘x’ is an integer between 1 to 16 
      e.g. find 5 hours
      
      j) Retrieve earliest free time:  retrieve/ft ‘x’ , where ‘x’ is an integer between 1 to 5 
      e.g. retrieve/ft 5  
      
      k) Select the Week: Week ‘x’,where ‘x’ is an integer between 1 to 13
      e.g. Week 5
      
      l) Mark as important: markAs INDEX
      e.g. markAs 3
      
      m) Custom: custom LIGHT_OR_DARK
      e.g. custom dark
      
      n) Help: help
      
      o)Exit: bye
