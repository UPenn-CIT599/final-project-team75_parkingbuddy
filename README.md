# :car: ParkingBuddy :blue_car:
Claire Lua [clua@seas.upenn.edu]

Samantha Lau [samlauj@seas.upenn.edu]

Min Choi [minschoi@seas.upenn.edu]


## Overview 
Parking is a scarce resource in many neighborhoods. As such, neighborhoods have parking policies to limit the number of times each vehicle can park at the neighborhood. Tracking the vehicles parked at a parking lot is manually-intensive work, as the enforcement volunteer must manually log the license plates and dates of every vehicle parked in a complex and determine the violations. 

The ParkingBuddy desktop program thus aims to automate this manual logging and tracking of vehicles so as to streamline the enforcement process.

## Setup
### Clone
Clone this repo to your local machine using `https://github.com/UPenn-CIT599/final-project-team75_parkingbuddy.git`

### Run Program
Build the gradle project to ensure all dependencies are loaded
```sh
./gradlew run
```

Run the main method (`GUI.java`) to launch the program
```sh
./gradlew run -Pmain=Parking.<class name> 
./gradlew run -Pmain=Parking.GUI
```

## Usage

When ParkingBuddy is run, the following window is launched: 
![alt text](src/src/main/java/Images/LaunchWindow.png)

### User Actions

There are 2 actions the user can take: 
1. Upload Photos
* This can be done either via a Folder upload or uploading individual image files
* A new window displays a table confirming the parking instances that have been parsed from the image files and uploaded to the database
![alt text](src/src/main/java/Images/ParkingInstancesUploaded.png)

2. Generate Report 
* The user must specify a date range (e.g. the week of May 13-19) by using the calendar function to input a start and end date
* The program outputs a table containing aggregated parking data over the inputted date range; the `Count` represents the number of instances the particular car has parked overnight within the specified date range
* Helps to easily determine violations for the following parking rule: `No overnight parking for more than 3 nights in a 7-day period` (in this case, no car has violated this rule!)
![alt text](src/src/main/java/Images/ParkingAggregates.png)

___
## Design Milestone
### Class Design 
Our class design with the relevant methods for each class can be seen from the following diagram:

![alt text](src/src/main/java/Images/classDiagram_updated.png)


### Non-trivial Method and JUnit Test:

- readDates() method in [JPEGReader.java](/src/src/main/java/Parking/JPEGReader.java): This method extracts the original date of the image files from a folder by extracting exif data, converting the date strings into LocalDateTime objects, and storing them in an ArrayList<LocalDateTime>
- Various unit tests in [JPEGReaderTest.java](/src/src/test/java/Parking/JPEGReaderTest.java)

## Commands
./gradlew run -Pmain=Parking.<class name>
  
./gradlew run -Pmain=Parking.JPEGReader
