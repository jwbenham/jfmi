#!/bin/bash
# This is a convenience script for running test classes located under the
# directory src/tests/jfmi.
#
# usage: runtest.sh <class>
#

CP=.
CP+=:./src
CP+=:./lib/*

SUBPACKAGE=tests.jfmi

java -classpath "${CP}" org.junit.runner.JUnitCore ${SUBPACKAGE}.$1 

