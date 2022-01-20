# ComposeGraphLibrary

## Table of Contents
+ [About](#about)
+ [Getting Started](#getting_started)
+ [Usage](#usage)
+ [Contributing](../CONTRIBUTING.md)

## About <a name = "about"></a>
The purpose of this project is to visualize data using different charts. The charts should be accessible as composables. 

## Getting Started <a name = "getting_started"></a>

### Gradle

Add the following repository and gradle to your depdendency.
project.gradle
```
repositories {
        google()
        mavenCentral()
        maven {
            url = uri("https://maven.pkg.github.com/asgerakerman/composegraphlibrary")
            credentials {
                username = username
                password = token
            }

        }
    }
```
module.gradle
```
implementation 'com.example.composegraphlibrary:release:1.0.3'

```

### Installing

A step by step series of examples that tell you how to get a development env running.

Say what the step will be

```
Give the example
```

And repeat

```
until finished
```

End with an example of getting some data out of the system or using it for a little demo.

## Usage <a name = "usage"></a>

Add notes about how to use the system.
