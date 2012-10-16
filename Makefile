# Makefile for the JFMI project.
# Automates project building, and javadoc documentation generation.
#

# Source directory
SRC_DIR+=./src

# Classpath
CP+=.:./src:./lib/*

# javadoc options
SUB_PACK+=jfmi
JAVADOC_DEST+=./doc/javadoc

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
		-classpath ${CLASSPATH}:${CP} -subpackages ${SUB_PACK}

# Remove javadoc
cleandoc:
	rm -f -r ${JAVADOC_DEST}

