#!/bin/bash

#
# Copyright 2000-2016 Apache Software Foundation
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#

# ----------------------------------------------------------------------------
# Maven2 Start Up Batch script
#
# Required ENV vars:
# JAVA_HOME - location of a Java SDK.
#
# Optional ENV vars:
# M2_HOME - location of Maven2's installed home directory. If not set,
#           the script will try to figure it out from its own launch path.
# M2_USER_HOME - User's home directory to store maven repository.
# MAVEN_OPTS - parameters to pass to the Java VM when running Maven.
# MAVEN_CMD_LINE_ARGS - Extra arguments to pass to the Maven command line.
#                       (cli args take precedence)
# ----------------------------------------------------------------------------

# OS specific support.  $name means OS name, $version means OS version.
cygwin=false
darwin=false
mingw=false
case "`uname`" in
  CYGWIN*) cygwin=true;; 
  Darwin*) darwin=true;; 
  MINGW*) mingw=true;; 
esac

# set up parameters for the java virtual machine
#
#   NOTE: This value is encoded in the image of the Java process.
#         Changing the script will not change the value in a running process.
#         On some systems this parameter may be called "-Xmx"
#
if [ -z "$MAVEN_OPTS" ]; then
    MAVEN_OPTS="-Xmx1g"
fi

if [ -z "$JAVA_HOME" ]; then
  echo "Error: JAVA_HOME is not set." >&2
  exit 1
fi

if [ -z "$M2_HOME" ]; then
  # guess M2_HOME from script location
  # see https://stackoverflow.com/questions/4774052/what-is-the-difference-between-realpath-and-readlink
  # for the difference between realpath and readlink
  # this is required for Cygwin because shell scripts execute in Windows,
  # but symlinks will return a unix path
  if $cygwin; then
      _M2_HOME=`readlink -f "$0"`
  else
      _M2_HOME=`realpath "$0"`
  fi

  _M2_HOME=`dirname "$_M2_HOME"`/../`
  # take care of symlinks
  if [ -L "$_M2_HOME" ]; then
      _M2_HOME=`readlink "$_M2_HOME"`
  fi

  # assume we're in a bin directory if that's what was passed in
  # otherwise we should be in a lib directory and M2_HOME should be the parent
  if [ -d "$_M2_HOME"/bin ]; then
    M2_HOME="$_M2_HOME"
  elif [ -d "$_M2_HOME"/lib ]; then
    M2_HOME="`dirname "$_M2_HOME"`"
  else
    echo "Error: M2_HOME is not set and could not be found." >&2
    exit 1
  fi
fi

# if there's a user home override, use that instead of the default
if [ -z "$M2_USER_HOME" ]; then
  M2_USER_HOME="$HOME"
fi

# set up MAVEN_CONFIG
if [ -z "$MAVEN_CONFIG" ]; then
  MAVEN_CONFIG="$M2_USER_HOME"/.m2
fi

# set up classpath
MAVEN_CLASSPATH="$M2_HOME"/boot/*

# For Cygwin, ensure paths are in Windows format before they are used by Java
if $cygwin; then
  [ -n "$JAVA_HOME" ] && JAVA_HOME=`cygpath --unix "$JAVA_HOME"`
  [ -n "$M2_HOME" ] && M2_HOME=`cygpath --unix "$M2_HOME"`
  [ -n "$M2_USER_HOME" ] && M2_USER_HOME=`cygpath --unix "$M2_USER_HOME"`
  [ -n "$MAVEN_CONFIG" ] && MAVEN_CONFIG=`cygpath --unix "$MAVEN_CONFIG"`
  [ -n "$MAVEN_CLASSPATH" ] && MAVEN_CLASSPATH=`cygpath --path --unix "$MAVEN_CLASSPATH"`
fi

# run Maven
exec "$JAVA_HOME"/bin/java \
  $MAVEN_OPTS \
  -Dmaven.home="$M2_HOME" \
  -Dmaven.multiModuleProjectDirectory="$M2_USER_HOME" \
  -Dmaven.conf="$MAVEN_CONFIG" \
  -cp "$MAVEN_CLASSPATH" \
  org.apache.maven.cli.MavenCli \
  "$@" \
  $MAVEN_CMD_LINE_ARGS
