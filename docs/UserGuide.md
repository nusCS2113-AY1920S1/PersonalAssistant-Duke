# Duke++ V1.1 User Guide

## Installation

Run the Jar file in the [releases](https://github.com/AY1920S1-CS2113T-T12-2/main/releases) page using Java 11 using the command <code>java.exe -jar duke.jar</code>

## Usage and commands

### Listing
<code>/dukeobjects</code> to dukeobjects all the expensesList in the to do dukeobjects.
<code>/find [item]</code> searches for entries containing the word <code>item</code>.

### Adding expensesList
<code>/todo [task]</code> adds <code>task</code> to the to do dukeobjects
<code>/deadline [task] /by [time]</code> adds <code>task</code> the to do dukeobjects that needs to be done by <code>time</code>. <code>time</code> needs to be in <code>dd/mm/yyyy hhmm</code> format.
<code>/event [task] /start [start_time] /end [end_time]</code> adds <code>task</code> the to do dukeobjects that starts at <code>start_time</code> and <code>end_time</code>. <code>start_time</code> and <code>end_time</code> needs to be in <code>dd/mm/yyyy hhmm</code> format.

### Deleting expensesList
<code>/delete [i] [task]</code> deletes <code>i</code>'th item of the dukeobjects listed in <code>/dukeobjects</code>


## Save file directory
