# Makefile for the JFMI project.
# Automates project building, and javadoc documentation generation.
#

# Source directory
SRC_DIR+=./src

# Subpackages for javadoc generation
SUB_PACK+=jfmi.app:jfmi.control:jfmi.dao:jfmi.gui:jfmi.repo:jfmi.util

# Destination for javadoc generation
JAVADOC_DEST+=./doc/javadoc

# javac -classpath option
CP+=.:./src:./lib/*

# javac options
OPT+=-Xlint:unchecked



# Build the whole project source
all:
	javac -classpath ${CLASSPATH}:${CP} ${OPT} @src.txt

# Build the project tests
tests:
	javac -classpath ${CLASSPATH}:${CP} ${OPT} @src-tests.txt

# Generate javadoc HTML documentation
jdoc: cleandoc
	javadoc -quiet -d ${JAVADOC_DEST} -sourcepath ${SRC_DIR} \
		-subpackages ${SUB_PACK}

# Remove javadoc
cleandoc:
	rm -f -r ${JAVADOC_DEST}

