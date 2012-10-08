#!/bin/bash

CP=.
CP+=:./src
CP+=:./lib/*

SUBPACKAGE=tests.jfmi

java -classpath "${CP}" org.junit.runner.JUnitCore ${SUBPACKAGE}.$1 

