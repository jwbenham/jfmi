#!/bin/bash
# This is a convenience script for starting up the JFMI application.
# It specifies the required classpath, and loads the class whose main()
# method should be invoked.

java -classpath ".:./src:./lib/*" jfmi.control.JFMIMain

