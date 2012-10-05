# Makefile for the JFMI project.
# Automates project building, and javadoc documentation generation.
#

# Source directory
SRC_DIR+=./src

# Subpackages for javadoc generation
SUB_PACK+=jfmi.control:jfmi.database:jfmi.gui:jfmi.util

# Destination for javadoc generation
JAVADOC_DEST+=./doc/javadoc

# javac -classpath option
CP+=.:./src:./lib/sqlite-jdbc-3.7.2.jar



# Build the whole project
all:
	javac -classpath ${CLASSPATH}:${CP} @src.txt

# Generate javadoc HTML documentation
jdoc: cleandoc
	javadoc -quiet -d ${JAVADOC_DEST} -sourcepath ${SRC_DIR} \
		-subpackages ${SUB_PACK}

cleandoc:
	rm -f -r ${JAVADOC_DEST}

