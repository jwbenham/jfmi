#jfmi

Java File Meta-Information (JFMI) application - allows the user to associate 
files with tags and comments. The application is written in Java and uses an
SQLite database. Utility scripts for building and running the application are
provided for both Windows 7 and Linux (Ubuntu 12.04).

The application allows a user to select files and directories on their file
system to be saved for tagging. A user may also create tags - keywords or phrases
to be associated with a path. A path saved in the JFMI database can be associated
with one of the user's created tags, and a comment can be added to such a
path "tagging."

The goal of JFMI is to make it easier for users to find files and directories on
their file system by allowing them to sort and search through the tagged files 
in JFMI by tags and comment keywords.

The following features are currently implemented:
* Addition/removal of paths available for tagging
* Addition/removal/editing of tags
* Saved paths can have tags added, removed, or updated with new/revised comments 
* User can sort displayed files by file name or file path
* Files can be shown in their parent folder using the local system's windowing
system

Future work:
* Searching paths by tag
* Searching paths by keywords in tagging comments
* Unit tests need to be added for most classes
* Develop an interface for scrolling through a path's taggings and comments


## Building
The application requires the Java SE 7 JDK for building.

### Building the Project Source Classes
#### Linux
Running `make all` in the top-level directory will build the project source files.

#### Windows 
Running `winmake.bat all` in the top-level directory will build the project source 
files.

### Building the Project Unit Tests
#### Linux 
Running `make tests` in the top-level directory will build the project JUnit
test classes.


## Running
### Running the JFMI Application
The application requires the Java SE 7 JRE in order to run. The 
"jfmi/control/JFMIMain" class contains the `main()` method for starting the
application. The utility shell scripts for running the application will pass 
JFMIMain to the java launcher and set the classpath to the appropriate value.

#### Linux
Execute the `run.sh` script in the top-level project directory.

#### Windows
Execute the `winrun.bat` script in the top-level project directory.

### Running Unit Tests
#### Linux
The "runtest.sh" shell script located in the top-level project directory is
a convenience script for running unit tests.

Example:
Say we have a class located at src/jfmi/util/StringUtil.class, and that we have
written a JUnit test for it in src/tests/jfmi/util/StringUtilTest.class. The JUnit
test can be started with the following command:
`./runtest.sh util.StringUtilTest`


## Documentation
Documentation associated with JFMI is contained in the 'docs/' directory. This
included javadoc documentation as well as other information - e.g. the SQL
statements used to build the application's database tables.

### Java Class Documentation
#### Linux
Execute `make jdoc` to build the javadoc documentation.
Execute `make cleandoc` to remove the javadoc documentation.

#### Windows
Execute `winmake.bat jdoc` to build the javadoc documentation.
Execute `winmake.bat cleandoc` to remove the javadoc documentation.


