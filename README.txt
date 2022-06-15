Title : Appointment and Contact Manager
Purpose : Manages Contacts and Appointments on a local Database. Generates reports about the Appointments and Contacts
Author : Paul Overfelt III Student ID 009278142
Contact : paul@overfelt.net or poverfe@wgu.edu
Application Version 1.0
Date 6-15-2022

Created With:
Intellij 2021.1.3 Community
JDK 11.0.11
JavaFX 17.0.1
MySQL Connector Java 8.0.25
Gluon Scene Builder 17.0.0

How to Build Program
In IntelliJ
File -> Project Structure -> Libraries
Add your path to JavaFX and the MySQL Connector Java

Run -> Edit Configurations
Select Java 11
Select Modify Options and Add VM Options
Add this to the VM options "--module-path ${PATH_TO_FX} --add-modules javafx.fxml,javafx.controls,javafx.graphics"
Make sure the main.Main is selected as the main program.

Description of Additional Report
"Customer Total Appointments by ID"
This displays a list of customers and displays how many appointments the customer has.

MySQL Connector Driver Version: MySQL Connector Java 8.0.25