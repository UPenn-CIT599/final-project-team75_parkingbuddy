Summary.txt file, with nicer formatting :) 

## Overview 

Parking Buddy is a parking tracking application that makes use of OpenALPR, an open-source OCR library, to help parking enforcement officers automatically recognize license plates. A SQLite database is also used to track the number of times each vehicle is parked at a parking garage over a period of time.

Parking Buddy has 2 main functionalities - upload photos, or view parking report. 

1) To upload photos, the parking enforcement officer could choose to upload photos from folder or files. When photos are uploaded, the OpenALPR API is used to automatically detect the license plate of the vehicle in each photo. These parking instances are then stored in an SQLite database.

2) There is also a parking report viewer, where the officer can select a date range, and get a summary aggregate table view of vehicles, and the number of times that each vehicle were parked overnight. A common problem with parking enforcement is that owners may ask for proof that the vehicle had been parked overnight. As such, when the officier taps on each car’s row in the report, it will expand to display the photos and times of each time the car is parked overnight in the selected date period.

## Work breakdown
### Claire - Backend, database, frontend

#### Project classes
1) Database (SQLite database integration, and all SQL logic)
2) PhotoInstancesTableFactory.java (Uploaded parking instances GUI) 
3) ParkingAggregatesTableFactory.java (Parking report GUI)
4) ParkingException.java (Catch-all exception class)
5) ParkingAggregate (Aggregated parking instances)
6) ParkingController (Handle split logic of upload / report viewer)
7) GUI (Set up java fx and integrate GUI with the other classes to ensure end-to-end works)
8) LicenseOCR (specific logic to handle horizontal that was not compatible with OCR library)


#### Tests
1) DatabaseTest
2) FakeDatabase


#### Infra work
* Project milestone: Class design, UML diagram for class design, skeleton code to link up all classes
* Integrate all classes by making them call each other so that the program works as a whole
Final class diagram
Set up jar file for non-code users to run Parking Buddy program without build file
Set up gradle build file
UI Wireframe mock

Sam - Backend

Classes
Car.java
LicenseOCR.java (Call to OpenALPR API)
Photo.java (photo object)
ParkingInstance.java (ParkingInstance object)
ParkingInstanceProcessor.java (Process each uploaded parking instance)
PhotoFactory.java


Tests
LicenseOCRTest
ParkingControllerTest
ParkingInstanceProcessorTest
PhotoFactoryTest

Infra work
Project milestone -  Method in JPEGReader, javadoc comments, unit tests
Milestone README
Investigate, and got OpenALPR library working
Final Project README - detailed report for final submission, screenshots and gif 

Min Choi - Frontend

Project Classes
GUI.java (Welcome page, upload buttons, and calendar views)
JPEGReader.java (project milestone, later refactored to PhotoFactory.java)
PennDraw (Decided to not use)


Tests
CarTest
JPEGReaderTest (project milestone, later refactored to PhotoFactoryTest.java)

Infra work
Project milestone - Method in JPEGReader, and milestone unit tests
Javadoc comments on classes and methods
Debug eclipse

