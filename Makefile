# Makefile for the JFMI project.
# Automates project building, and javadoc documentation generation.
#

# Source directory
SRC_DIR+=./src

# Subpackages for javadoc generation
SUB_PACK+=jfmi

# Destination for javadoc generation
JAVADOC_DEST+=./docs/javadoc

# javac -classpath option
CP+=.:./src:./lib/sqlite-jdbc-3.7.2.jar



# Build the whole project
all:
	javac -classpath ${CLASSPATH}:${CP} @src.txt

# Generate javadoc HTML documentation
doc:
	javadoc -d ${JAVADOC_DEST} -sourcepath ${SRC_DIR} -subpackages ${SUB_PACK}

