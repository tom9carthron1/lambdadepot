_This document describes how to build and import lambdadepot into an IDE._

lambdadepot uses a [Gradle](https://gradle.org/) build. The instructions below use [Gradle Wrapper](https://docs.gradle.org/current/userguide/gradle_wrapper.html) from the root of the source tree. The wrapper script serves as a cross-platform, self-contained bootstrap mechanism for the build system.

## Before You Start
To build you will need [Git](https://help.github.com/en/articles/set-up-git) and [OpenJDK 8](https://adoptopenjdk.net/?variant=openjdk8&jvmVariant=hotspot) or later. Be sure that your `JAVA_HOME` environment variable points to the `jdk-8` folder extracted from the JDK download.

## Get the Source Code
```bash
git clone git@github.com:tbd.git
cd lambdadepot
```

## Build from the Command Line
To compile and test use:

```bash
./gradlew build
```

The first time you run the build, it may take a while to download Gradle and all build dependencies, as well as to run all tests. Once you've bootstrapped a Gradle distribution and downloaded dependencies, those are cached in your `$HOME/.gradle` directory.

Gradle has good incremental build support, so run without clean to keep things snappy.
