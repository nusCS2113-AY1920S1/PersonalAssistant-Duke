# Chronologer User Guide

## 1. Introduction

## 2. Quickstart

## 3. Features 
#### Command Format
- Words in `UPPER_CASE` are parameters to be inputted by the user eg. in `todo DESCRIPTION`, `DESCRIPTION` is a parameter inputted as `todo homework`.

### Viewing help : `help`

List all commands along with description of commands.
Format: `help`

### Listing all tasks : `list`

Shows a list of all tasks in the task scheduler.
Format: `list`

### Add tasks of todo type : `todo`

Adds a todo task.
Format: `todo DESCRIPTION`

### Add tasks of event type : `event`

Adds an event task.
Format: `event DESCRIPTION /at DATE_TIME - DATE_TIME`

### Add a deadline task : `deadline`

Adds an deadline task.
Format: `deadline DESCRIPTION /by DATE_TIME`

### Postponing a task : `postpone`

Edits an existing task in the task scheduler to a different date.
Format: `postpone INDEX DATE_TIME`

- Edits the task at the specified `INDEX`. The index refers to the index number shown in the task list. `INDEX` must be a positive integer.


Examples:

* `postpone 1 25/09/2019 1500`
* `postpone 5 27/09/2019 0900 - 03/10/2019 2359`

### Searching for a free timeslot: 'search'

Finds a timeslot that spans the specified period.
Format: `search PERIOD UNIT_OF_TIME`

- `PERIOD` must be a positive integer.
- `UNIT_OF_TIME` can be `minutes`, `hours` or `days`.

Examples:

* `search 20 hours`
* `search 2 days`

Expected outcome:

`You can schedule something after [E][X] Dinner (at: 24/09/2019 1800 - 24/09/2019 1930)`
