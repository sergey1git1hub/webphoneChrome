taskkill /F /IM "chrome.exe">nul 2>&1
"c:\program files (x86)\gbphone_service\uninstall.exe" -q
rd /s /q "C:\Program Files (x86)\GBPhone_Service"
rem https://stackoverflow.com/questions/10716803/batch-file-to-perform-start-run-temp-and-delete-all?utm_medium=organic&utm_source=google_rich_qa&utm_campaign=google_rich_qa
cd /D %temp%
for /d %%D in (*) do rd /s /q "%%D"
del /f /q *

ECHO --------------------------------------
ECHO **** Clearing Chrome cache
set ChromeDataDir=C:\Users\%USERNAME%\AppData\Local\Google\Chrome\User Data\Default
set ChromeCache=%ChromeDataDir%\Cache>nul 2>&1  
del /q /s /f "%ChromeCache%\*.*">nul 2>&1    
del /q /f "%ChromeDataDir%\*Cookies*.*">nul 2>&1    
rem del /q /f "%ChromeDataDir%\*History*.*">nul 2>&1    


set ChromeDataDir=C:\Users\%USERNAME%\Local Settings\Application Data\Google\Chrome\User Data\Default
set ChromeCache=%ChromeDataDir%\Cache>nul 2>&1
del /q /s /f "%ChromeCache%\*.*">nul 2>&1   
rem del /q /f "%ChromeDataDir%\*Cookies*.*">nul 2>&1    
rem del /q /f "%ChromeDataDir%\*History*.*">nul 2>&1    
ECHO **** Clearing Chrome cache DONE

powershell -Command "Invoke-WebRequest http://172.21.7.239/gbwebphone/scripts/mizu-js/native/WebPhoneService_Install.exe -OutFile %HOMEPATH%\Downloads\WebPhoneService_Install.exe"

%HOMEPATH%\Downloads\WebPhoneService_Install.exe