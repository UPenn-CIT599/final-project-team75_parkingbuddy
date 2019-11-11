# :car: ParkingBuddy :blue_car:
Claire Lua [clua@seas.upenn.edu]

Samantha Lau [samlauj@seas.upenn.edu]

Min Choi [minschoi@seas.upenn.edu]


## Goal
Create a tracking tool to allow parking enforcement to track the vehicles that are parked in a parking lot. 

## Background
Parking is a scarce resource in many neighborhoods. As such, these neighborhoods have parking policies to limit the number of times each vehicle can park at the neighborhood.

Tracking of the number of vehicles parked at a parking lot can be very manually intensive work, as the enforcement volunteer has to manually log the license plates and dates of every vehicle parked in a complex. This can take more than an hour after parking walks. 

The ParkingBuddy desktop program aims to automate the manual logging and tracking of vehicles so as to streamline the enforcement process.

## User actions

This is the typical flow that a parking enforcement volunteer will take:

**A. During enforcement patrol**
- Take pictures of each vehicle parked in the parking lot

**B. Data Logging**
- In a spreadsheet, enter the following information:
- Vehicle licence plate number based on each picture taken
- Date and time of patrol 

[Here is a sample spreadsheet](https://docs.google.com/spreadsheets/d/1Mpp9opsv6qjvfOmCwG9bU2ZnEMJZBuzvr9L-fWJm1Qo/edit?usp=sharing) of how the manual logging may look like. 

**C. Data analysis**
- Plot data according to parking_rules. At the end of each 7-day period, count the number of times that a vehicle has parked in the parking lot.
- Produce a parking report ([sample report](https://docs.google.com/spreadsheets/u/2/d/1Mpp9opsv6qjvfOmCwG9bU2ZnEMJZBuzvr9L-fWJm1Qo/edit#gid=467789344)) for the specified time period to summarize the parking usage, and flag any vehicles that have violated parking rules.

## ParkingBuddy Functionality
There are 3 main functionalities of ParkingBuddy:

1. Read user input and perform some action: Give users the option to either (1) upload photos, or (2) pull a report on parking violations. To upload photos, user enters folder name where vehicle pictures are stored. Parse the images from folder into image file names, and read the image EXIF data for datetime. 

2. Data processing: Call OCR license plate reader OpenALPR API to automatically detect the vehicle in an image. Based on API results, log license plate, and date and time of patrol into a CSV file.

3. Data analysis: Produce analysis of vehicles parked in the parking lot if user chooses to pull analysis report. Implement parking rules logic, and flag vehicle violations. 

## Sample Parking Rules
1. No overnight parking for more than 3 nights in a 7-day period.
- Overnight is defined as parking at anytime  between 9pm - 6am the following day. As long as a vehicle is detected to be parked between these hours, it is deemed as having parked overnight (overnight_count = 1)
2. No day parking for more than 3 days in a 10-day period.
- Day is defined as time between 6am - 9pm (day_count = 1).

