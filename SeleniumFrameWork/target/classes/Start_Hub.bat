cd /D %~dp0

title Start Selenium Hub Server

set GRID_PATH="..\Executables\selenium-server-standalone-3.141.59.jar"

java -jar %GRID_PATH% -role hub -hubConfig hub_config.json

pause
