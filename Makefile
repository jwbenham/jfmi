
CP+=.:./lib/sqlite-jdbc-3.7.2.jar

all:
	javac -classpath ${CLASSPATH}:${CP} @src.txt

