@echo off
:: A batch file that can be used to build the JFMI application or its
:: supporting javadoc documentation.


IF "%1"=="" GOTO USAGE
IF "%1"=="all" GOTO ALL
IF "%1"=="jdoc" GOTO JDOC
IF "%1"=="cleandoc" GOTO CLEANDOC


:ALL
echo building jfmi..
javac -classpath ".;lib\*;src" -Xlint:unchecked @src.txt
echo build complete
GOTO END


:JDOC
echo building javadoc..
javadoc -quiet -d "doc\javadoc" -sourcepath src -classpath ".;lib\*;src" -subpackages jfmi
echo build complete
GOTO END


:CLEANDOC
echo cleaning javadoc..
rd /S /Q "doc\javadoc"
echo cleaning complete
GOTO END


:USAGE
echo.
echo USAGE: %0 ^<all ^| jdoc ^| cleandoc^>
echo.


:END