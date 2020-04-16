This README helps you getting started with GeoServer development. It will guide you through the process of checking out the source code, compiling it, and running.

GeoServer Development requires Java 8, Maven, and git. Due to subtle changes in Java class libraries we require development on Java 8 at this time (although the result is tested on Java 11).


Further readings:
  http://docs.geoserver.org/latest/en/developer/index.html

== Linux ==

1) Install prerequisites

Obtain OpenJDK 8, Maven and git from your Linux distribution.


== macOS ==

1) Install Java Runtime Environment

Download and install Java 8 runtime environment, as provided by AdoptOpenJDK macOS installers.
https://adoptopenjdk.net

Update your shell environment with:
  export JAVA_HOME=/Library/Java/JavaVirtualMachines/adoptopenjdk-8.jdk/Contents/Home
  
The system /usr/bin/java makes use of JAVA_HOME setting above.

2) Download and install git
https://git-scm.com/download/mac

3) Download and install Maven 3
http://maven.apache.org/download.html

== Windows ==

1) Install Java Runtime Environment
Download and install Java 8 runtime environment, as provided by AdoptOpenJDK windows installers.
https://adoptopenjdk.net

Update Windows *Environment Variables*:


* Create an environment variable JAVA_HOME and point it to your Java SDK directory.
* Modify the PATH variable and add: ;%JAVA_HOME%/bin

2) Download and install git
https://git-scm.com/download/windows

3) Download and install Maven 3
http://maven.apache.org/download.html

== OS independent tasks ==

1) Get the source code
Go to the command line and run:
  git clone https://github.com/geoserver/geoserver.git

2) Build the source code
Go to the command line and navigate to the folder you just checked out. Now run:
  cd geoserver
  cd src
  mvn clean install

3) Running locally
  cd web/app
  mvn jetty:run

== Troubleshooting ==

The build process may fail because of several reasons:

* Unavailable dependencies - Maven tries to download dependencies which might not be available on the server side yet.
Solution: Try again in some minutes.

* Failing tests - Maven runs existing tests automatically. If some of them fail, the build fails.
Solution: You can tell maven not to run the tests. This is discouraged.
Bug the developers instead or fix the test and send a patch, thanks!
If you really just want to disable the test, run maven like so:
  mvn -DskipTests=true install
