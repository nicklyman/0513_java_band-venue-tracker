# _Band Venue Tracker_

#### _A web app that tracks bands, venues, and the venues that each band has played at, May 13, 2016_

#### By _**Nick Lyman**_

## Description

_This web application allows a user to add, update, delete, and list bands. The user can also add venues for the bands to play at. The user can assign venues to bands to show where the bands have played._

## Setup/Installation Requirements

* _Link to repository: https://github.com/nicklyman/0513_java_band-venue-tracker.git_
* _Clone this repository to your Github account_
* _Create a project directory up on your computer_
* _Have Java installed on your computer_
* _Have Gradle installed on your computer_
* _Use your command line to access the cloned repository on your computer_
* _Have Postgres SQL installed on your computer_
* _Use the command line to start Postgres SQL by typing "psql" without quotes into the command prompt and create a database as follows: CREATE DATABASE band_tracker_
* _After the band_tracker database is created, type \c band_tracker to connect to that database prior to building any database tables_
* _Next, create your band database table as follows: CREATE TABLE bands (id serial PRIMARY KEY, band_name varchar)_
* _Next, create your venue database table as follows: CREATE TABLE venues (id serial PRIMARY KEY, venue_name varchar)_
* _Next, create your join table to link the bands and venues as follows: CREATE TABLE bands_venues (id serial PRIMARY KEY, band_id int, venue_id int)_
* _Now the two tables are linked together using the join table which allows for a clean way to work with many-to-many relationships_
* _For creating a testing database, type: CREATE DATABASE band_tracker_test WITH TEMPLATE band_tracker_
* _In a seperate tab in the command prompt, type "gradle run" without quotation marks in the command line and it should build the application_
* _Open a web browser window and type "localhost:4567" without quotation marks and it should open the application in your browser window_
* _Now it is possible to add, update, delete, and list bands and add venues from the database_

_Database details listed below:_
* _CREATE DATABASE band_tracker_
* _CREATE TABLE bands (id serial PRIMARY KEY, band_name varchar)_
* _CREATE TABLE venues (id serial PRIMARY KEY, venue_name varchar)_
* _CREATE TABLE bands_venues (id serial PRIMARY KEY, band_id int, venue_id int)_
* _CREATE DATABASE band_tracker_test WITH TEMPLATE band_tracker_

## Known Bugs

_User is able to enter multiple bands/venues with the same name and they are given different IDs._

## Support and contact details

_If a bug is found, please let me know via Github. Feel free to contact me with questions or suggestions and contribute to the code._

## Technologies Used

* _Git_
* _Github_
* _Atom text editor_
* _Java_
* _Postgres SQL_
* _Spark_
* _VelocityTemplateEngine_

### License

*This software is licensed under the MIT license*

Copyright (c) 2016 **_Nick Lyman_**
