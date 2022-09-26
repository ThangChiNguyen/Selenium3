cd /D %~dp0
 
title Start Windows Desktop Node

set CHROME_DRIVER="..\Executables\chromedriver.exe"
set FIREFOX_DRIVER="..\Executables\geckodriver.exe"
set GRID_PATH="..\Executables\selenium-server-standalone-3.141.59.jar"

java -Dwebdriver.chrome.driver=%CHROME_DRIVER% -Dwebdriver.gecko.driver=%FIREFOX_DRIVER%  -jar %GRID_PATH% -role node -nodeConfig node_config.json

pause