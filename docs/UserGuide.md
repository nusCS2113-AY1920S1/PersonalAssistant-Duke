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
       1. Download the latest ​betterduke.jar​ ​here​.
       2. Copy the file to the folder you want to use as the home folder for your
          Address Book.
       3. Double-click the file to start the app. The GUI should appear in a few
          seconds.
          <img src="https://github.com/AY1920S1-CS2113T-W12-4/main/blob/master/docs/images/UG%20images/Example%20UI1.png" width="200" height="250">
        4. The Command line at the bottom is where users will input their commands
        and the response box is where the system will return it’s output.
        5. Type the command in the command box and press ​Submit ​to execute it.
        ![UI2](https://github.com/AY1920S1-CS2113T-W12-4/main/blob/master/docs/images/UG%20images/Example%20UI2.png)
        6. E.g. typing help and pressing ​Submit ​will open the help window.
        7. Some example commands you can try:
            a.     Show workload     ​ : show workload for following week’s timetable
            b.     Filter CS2113T     ​ : show tasks which contains keyword
               “CS2113T”
            c.     bye     ​: exits the app
        Refer to [Features](#Features)​ for details of each command.


# 3. Features
   ## Command Format
    Words in ​UPPER_CASE​ are the parameters to be supplied by the user     e.g. in ​add
    td/TASK_DESCRIPTION, DESCRIPTION​ is a parameter which can be used as     ​add
    td/do tutorial 6
    
  ##  3.1. Adding task: ​ add
    Adds a task to the list
    Format    
    -for event : ​add/e Mod_Code TASK_DESCRIPTION /at
    DATE(xx/xx/xxxx or week x day) /from TIME /to TIME
    -for deadline :​add/d Mod_Code TASK_DESCRIPTION /by
    DATE(xx/xx/xxxx or week x day) TIME
        Example:    
    add/e CS2101 assignment /at 12/08/2019 /from 1200 /to
    1300

 ##   3.2. Delete task: ​ delete
    Delete a task from the list
    Format:    
    -for event : ​delete/e Mod_Code TASK_DESCRIPTION /at
    DATE(xx/xx/xxxx or week x day) /from TIME /to TIME
    -for deadline :​delete/d Mod_Code TASK_DESCRIPTION /by
    DATE(xx/xx/xxxx or week x day) TIME
        Example:    
    add/d CS2101 assignment /by week 1 mon 1200
    

 ##   3.3. Listing all tasks: ​ list
    Shows the list of all tasks in the schedule.
    Format ​:​ ​list NAME_OF_LIST
    Example ​:
    list todo
    Displays the list of tasks in todo

  ##  3.4. Search for specific taskings using keywords:     ​     find    
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

  ##  3.5. Set reminder for upcoming task:     ​     remind    
    This sets a reminder for current task. A notification will pop-up when
    set timing is met.    
    Format:     ​ ​remind LIST_TYPE INDEX @ DATE TIME    
        Example:        
    remind deadline 1 @ 1/1/2020 1800    
    The first task in deadline list will have a reminder set at 1800 on 1/1/


   ## 3.6. Labeling task:     ​     label    Tags 
   Task with a label so all related tasks can categorise total    
    Format:     ​ ​label td/TASK_DESCRIPTION tag/TAG_NAME    

   ## 3.7. Set reminder for Todo tasks which have to be done within a certain period:     ​     todo reminder    
    Sets reminder on the start and end date for Todo tasks    
    Format     ​: ‘​TASK_DESCRIPTION’ (from sd/START DATE to ed/END
    DATE)​ ​, where ​‘​TASK_DESCRIPTION’​ is the task

   ## 3.8. View the schedule in terms of Todo, Event and    Dateline:     ​     show schedule    
    Categorize the list based on Todo, Event and deadline and display to
    the user    
    Format:     ​ ​show schedule

   ## 3.9. Finding earliest free time based on the given schedule    
    Finds the earliest free time for the user’s task with a given duration    
    Format:     ​ ​when is the nearest day in which I have a ‘X’
    hour free slot?    
        where     ​‘X’​     is an integer

   ## 3.10. Set a fixed duration for a task without entering start time    
    Finds the five earliest free time slots available for the user to choose    
    Format:     ​ ​‘TASK DESCRIPTION’ (needs ‘X’ hours/days)    
        where     ​ ‘TASK DESCRIPTION’ ​     is the task and     ​ ‘X’ ​     is duration in
    integer    

   ## 3.11. Viewing help :     ​     help    
    Format     ​: ​help

   ## 3.12. Input recurring activities    
    To avoid adding the same activity repeatedly.

   ## 3.13. Exiting the program :     ​     bye    
    Exits the program.    
    Format:     ​ ​bye

   ## 3.14. Saving the data    
    All taskings are saved automatically when user wants to modify their
    schedule. No manual saving is required.

   ## 3.15. Strike off tasks and track the progress of the week
    (Coming in v1.4)    
    To help users ensure they are on schedule.

   ## 3.16. Mark task as important :     ​     markAs     ​     (Coming in v1.4)    
    Add a keyword “Important !” beside the task as the specific     ​     INDEX    
    Format:     ​ ​markAs INDEX    
        Example     ​:    
    markAs 2    
    The 2nd task in the list will have a label (Important !)     i.e. ​read book
    (Important !)

   ## 3.17. Track the task completion rate (Coming in v1.4)    
    To allow users to track their productivity.

   ## 3.18. Customise light and dark theme:     ​     custom     ​     (Coming
    in v1.4)
    Changes the GUI between a light theme and dark theme.    
    Format:     ​custom LIGHT_OR_DARK    
        Example:        
    custom dark    
    This changes the theme of the GUI to a dark theme.

   ## 3.19. Save file encryption (Coming in v2.0)    
    A privacy choice to enable/disable encryption of data files.

   ## 3.20. Sync timetable with nusmods (Coming in v2.0)    
    Added option to sync timetable with nusmods to properly match
    school schedule.

   ## 3.21. Sync timetable with external calendars (e.g.
    Google calendar) (Coming in v2.0)    
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

        ● Add:        
       -for event : ​add/e Mod_Code TASK_DESCRIPTION /at DATE(xx/xx/xxxx
       or week x day) /from TIME /to TIME    
       -for deadline :​add/d Mod_Code TASK_DESCRIPTION /by
       DATE(xx/xx/xxxx or week x day) TIME    
        e.g​ add/e CS2101 assignment /on 12/08/2019 /from 1200 /to 1300

         ● Delete:        
    -for event : ​delete/e Mod_Code TASK_DESCRIPTION /at DATE
    (xx/xx/xxxx or week x day) /from TIME to TIME    
    -for deadline :​delete/d Mod_Code TASK_DESCRIPTION /by
    DATE(xx/xx/xxxx or week x day) TIME    
    e.g. ​add/d CS2101 assignment /by week 1 mon 1200
    
        ● List:     ​list TASK_TYPE    
    e.g. ​list event
    
        ● Filter:     ​filter KEYWORD    
    e.g. ​filter book
    
        ● Remind:     ​ ​remind LIST_TYPE INDEX @ DATE TIME    
    e.g. ​remind deadline 1 @ 1/1/2020 1800
    
        ● Label:     ​ ​label td/TASK_DESCRIPTION tag/TAG_NAME    
    e.g.​ ​label td/Duke user guide tag/CS2113T week 6
    
        ● Todo reminder:     ​‘​TASK_DESCRIPTION’ (from sd/START DATE to
    ed/END DATE)​ ​, where ​‘​TASK_DESCRIPTION’​ is the task    
    e.g. ​Fishing (from 01/02/2019 to 04/02/2019)
    
        ● Show schedule:     ​show schedule
    
        ● Find earliest free slot:     ​ when is the nearest day in which I
    have a ‘X’ hour free slot?    
    e.g​. ​when is the nearest day in which I have a 2 hour free
    slot?
    
        ● Set a fixed duration for a task without entering start time:     ​‘TASK
    DESCRIPTION’ (needs ‘X’ hours/days)    
    e.g. ​Fishing (needs 2 hours)
    
        ● Help:     ​help
    
        ● Mark as important:     ​markAs INDEX    
    e.g.​ ​markAs 3
    
        ● Custom:     ​custom LIGHT_OR_DARK    
    e.g. ​custom dark    
        ● Exit:     ​bye
