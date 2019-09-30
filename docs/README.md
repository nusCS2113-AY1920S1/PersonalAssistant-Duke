# User Guide
## Introduction

## Features 
Optix is a command line interface for theatre managers. It allows users to add theatre bookings by performance groups to the selection. It also allows users to allocate seats to audience members. Finally, the finances can be tracked and tabulated for accounting.

## Quick Start
1. Ensure that Java 11 or above is installed on your computer.
1. Download the latest optix.jar here.
1. Copy the file to the folder you want to use as the home folder for your theatre bookings.
1. Double-click the file to start the application. The GUI should appear in a few seconds.
<<INSERT GUI MOCKUP HERE>>
1. Type the command in the command box and press Enter to execute it
(e.g. type help and press enter to open the help window.)
1. Some example commands you can try:  
  
  **`add`**`Phantom of the Opera|5/5/2020|2000` : adds a show called “Phantom of the Opera” to be scheduled on the 5th of May 2020, with a payment of $2000 for the booking. 
  
  **`list`**: list all shows currently added.
  
  **`delete-one`**`Phantom of the Opera|5/5/2020` : finds a show called Phantom of the Opera that is scheduled on the 5th of May 2020 within the current list of shows, and deletes it.
  
  **`bye`**: exits the application
## Features
### Command Format
Words in UPPER_CASE are the parameters to be supplied by the user 

e.g.in **`add`**`SHOW_NAME|SCHEDULED_DATE|PRICE` the `SHOW_NAME`, `SCHEDULED_DATE`, and `PRICE` are parameters that can be used such as **`add`**` Phantom of the Opera|5/5/2020|2000`.

## Feature 1: Adding a show
### Usage
Adds a show to the show list.
### Keyword
**`add`**`SHOW_NAME|SCHEDULED_DATE|PRICE`
  Example of Usage:
  **`add`**`Phantom of the Opera|5/5/2020|2000`
  
Expected Outcome:

>Got it. I've added this show:

>Phantom of the Opera at: 5/5/2020

# Format for future features
### Feature  : Adding a show

## Usage

### `Keyword` - Describe action

Describe action and its outcome.

Example of usage: 

`keyword (optional arguments)`

Expected outcome:

`outcome`
