This health management system is used to record and monitor patients’ health condition.

How to run

The application has a command line interface based on JAVA. Before running it, all the five java files need to be compiled:
javac *.java


After compiling them, the two files to initiate database schemas and records should be executed at first in terminal:
java CreateTables
java InsertValues
After that, the application interface file can be executed for test:
java WolfHealth
