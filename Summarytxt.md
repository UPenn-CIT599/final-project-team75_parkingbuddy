Summary.txt file, with nicer formatting :) 

## Overview 

Parking Buddy is a parking tracking application that makes use of OpenALPR, an open-source OCR library, to help parking enforcement officers automatically recognize license plates. A SQLite database is also used to track the number of times each vehicle is parked at a parking garage over a period of time.

Parking Buddy has 2 main functionalities - upload photos, or view parking report. 

1) To upload photos, the parking enforcement officer could choose to upload photos from folder or files. When photos are uploaded, the OpenALPR API is used to automatically detect the license plate of the vehicle in each photo. These parking instances are then stored in an SQLite database.

2) There is also a parking report viewer, where the officer can select a date range, and get a summary aggregate table view of vehicles, and the number of times that each vehicle were parked overnight. A common problem with parking enforcement is that owners may ask for proof that the vehicle had been parked overnight. As such, when the officier taps on each car’s row in the report, it will expand to display the photos and times of each time the car is parked overnight in the selected date period.

## Work breakdown
### Claire (Jiasin) Lua - Backend, database, frontend

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

### Samantha Lau - Backend

#### Project Classes
1) Car.java
2) LicenseOCR.java (Call to OpenALPR API)
3) Photo.java (photo object)
4) ParkingInstance.java (ParkingInstance object)
5) ParkingInstanceProcessor.java (Process each uploaded parking instance)
6) PhotoFactory.java


#### Tests
1) LicenseOCRTest
2) ParkingControllerTest
3) ParkingInstanceProcessorTest
4) PhotoFactoryTest

#### Infra work
* Project milestone -  Method in JPEGReader, javadoc comments, unit tests
* Milestone README
* Investigate, and got OpenALPR library working
* Final Project README - detailed report for final submission, screenshots and gif 

### Min Choi - Frontend

#### Project Classes
1) GUI.java (Welcome page, upload buttons, and calendar views)
2) JPEGReader.java (project milestone, later refactored to PhotoFactory.java)
3) PennDraw (Decided to not use)

#### Tests
1) CarTest
2) JPEGReaderTest (project milestone, later refactored to PhotoFactoryTest.java)

#### Infra work
1) Project milestone - Method in JPEGReader, and milestone unit tests
2) Javadoc comments on classes and methods
3) Debug eclipse

