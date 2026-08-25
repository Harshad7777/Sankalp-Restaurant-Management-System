@echo off
echo Stopping Apache Tomcat...
cd /d "%~dp0tomcat10\apache-tomcat-10.1.34\bin"
call shutdown.bat
echo Tomcat stopped.
pause
