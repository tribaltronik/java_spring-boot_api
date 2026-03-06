@echo off
setlocal

rem
rem Copyright 2000-2016 Apache Software Foundation
rem
rem Licensed under the Apache License, Version 2.0 (the "License");
rem you may not use this file except in compliance with the License.
rem You may obtain a copy of the License at
rem
rem     http://www.apache.org/licenses/LICENSE-2.0
rem
rem Unless required by applicable law or agreed to in writing, software
rem distributed under the License is distributed on an "AS IS" BASIS,
rem WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
rem See the License for the specific language governing permissions and
rem limitations under the License.
rem

rem ----------------------------------------------------------------------------
rem Maven2 Start Up Batch script
rem
rem Required ENV vars:
rem JAVA_HOME - location of a Java SDK.
rem
rem Optional ENV vars:
rem M2_HOME - location of Maven2's installed home directory. If not set,
rem           the script will try to figure it out from its own launch path.
rem M2_USER_HOME - User's home directory to store maven repository.
rem MAVEN_OPTS - parameters to pass to the Java VM when running Maven.
rem MAVEN_CMD_LINE_ARGS - Extra arguments to pass to the Maven command line.
rem                       (cli args take precedence)
rem ----------------------------------------------------------------------------

rem set up parameters for the java virtual machine
rem
rem   NOTE: This value is encoded in the image of the Java process.
rem         Changing the script will not change the value in a running process.
rem         On some systems this parameter may be called "-Xmx"
rem
if "%MAVEN_OPTS%"=="" (
    set MAVEN_OPTS=-Xmx1g
)

if "%JAVA_HOME%"=="" (
    echo Error: JAVA_HOME is not set. >&2
    exit /b 1
)

if "%M2_HOME%"=="" (
  rem guess M2_HOME from script location
  for %%i in ("%0") do set _M2_HOME=%%~dpi
  set _M2_HOME=%_M2_HOME:~0,-1%
  rem take care of symlinks
  if exist "%_M2_HOME%\bin" (
    set M2_HOME="%_M2_HOME%"
  ) else if exist "%_M2_HOME%\lib" (
    for %%i in ("%_M2_HOME%") do set M2_HOME=%%~dpi
    set M2_HOME=%M2_HOME:~0,-1%
  ) else (
    echo Error: M2_HOME is not set and could not be found. >&2
    exit /b 1
  )
)

rem if there's a user home override, use that instead of the default
if "%M2_USER_HOME%"=="" (
  set M2_USER_HOME=%HOMEDRIVE%%HOMEPATH%
)

rem set up MAVEN_CONFIG
if "%MAVEN_CONFIG%"=="" (
  set MAVEN_CONFIG="%M2_USER_HOME%\.m2"
)

rem set up classpath
set MAVEN_CLASSPATH="%M2_HOME%\boot\*"

rem run Maven
"%JAVA_HOME%\bin\java" ^
  %MAVEN_OPTS% ^
  "-Dmaven.home=%M2_HOME%" ^
  "-Dmaven.multiModuleProjectDirectory=%M2_USER_HOME%" ^
  "-Dmaven.conf=%MAVEN_CONFIG%" ^
  -cp "%MAVEN_CLASSPATH%" ^
  org.apache.maven.cli.MavenCli ^
  %* ^
  %MAVEN_CMD_LINE_ARGS%
