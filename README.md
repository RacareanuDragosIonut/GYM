# GYM APPLICATION
This application aims to help clients with choosing a membership option that suits them, to make an
appointment at a gym class and to see all the gym trainers and their prices. It also helps the employees
to keep track of all the signed-up members, their specific type of gym membership and gym class and it
specifically helps the trainers with structuring and organizing their clients’ class schedules.

Registration (for both Client and Trainer):
The user needs to first register into the application by selecting one of the 2 roles: client or trainer. Both
roles require a unique username, a password and the basic information like full name, age and phone
number. If an user tries to make an account with an existing username, the message ”An account with
username X already exists!” will be displayed.

Login (for both Client and Trainer):
After the user creates an account, he has to login by entering his username and password and by
selecting his role.



A JavaFX Application demonstrating how to implement a simplistic approach of a registration use case using the following technologies:
* [Java 15 or 16](https://www.oracle.com/java/technologies/javase-downloads.html)
* [JavaFX](https://openjfx.io/openjfx-docs/) (as GUI)
*  [Gradle](https://gradle.org/) (as build tools)
* [MYSQL](https://www.mysql.com/downloads/) (as Database)

## Prerequisites
To be able to install and run this project, please make sure you have installed Java 11 or higher. Otherwise, the setup will note work!
To check your java version, please run `java -version` in the command line.

To install a newer version of Java, you can go to [Oracle](https://www.oracle.com/java/technologies/javase-downloads.html) or [OpenJDK](https://jdk.java.net/).

It would be good if you also installed Gradle to your system. To check if you have Gradle installed run `gradle -version`.

If you need to install Gradle, please refer to this [official Gradle docs](https://docs.gradle.org/current/userguide/installation.html).

Make sure you install JavaFX SDK on your machine, using the instructions provided in the [Official Documentation](https://openjfx.io/openjfx-docs/#install-javafx). Make sure to export the `PATH_TO_FX` environment variable, or to replace it in every command you will find in this documentation from now on, with the `path/to/javafx-sdk-15.0.1/lib`.

_Note: you can download version 15 of the javafx-sdk, by replacing in the download link for version 16 the `16` with `15`._

## Setup & Run
To set up and run the project locally on your machine, please follow the next steps.

### Clone the repository
Clone the repository using:
```git
git clone https://github.com/RacareanuDragosIonut/GYM.git
```

### Verify that the project Builds locally
If you have installed all the prerequisites, you should be able to run the command:
```
gradle clean build
```
If you prefer to run using the wrappers, you could also build the project using 
```


./gradlew clean build (for Linux or MacOS)
or 
gradlew.bat clean build (for Windows)
```

### Open in IntelliJ IDEA
To open the project in IntelliJ idea, you have to import it as either a Maven, or a Gradle project, depending on what you prefer. 
After you import it, in order to be able to run it, you need to set up your IDE according to the [official documentation](https://openjfx.io/openjfx-docs/). Please read the section for `Non-Modular Projects from IDE`.
If you managed to follow all the steps from the tutorial, you should also be able to start the application by pressing the run key to the left of the Main class.

### Run the project with Maven / Gradle
The project has already been setup for Maven and Gradle according to the above link.
To start and run the project use the command:
* `gradle run` or `./gradlew run` (to start the `run` task of the `org.openjfx.javafxplugin` plugin)

To understand better how to set up a project using JavaFX 11+ and [Gradle](https://openjfx.io/openjfx-docs/#gradle), please check the [official OpenJFX documentation](https://openjfx.io/).



## Resources
To understand and learn more about **JavaFX**, you can take a look at some of the following links:
* [Introduction to FXML](https://openjfx.io/javadoc/16/javafx.fxml/javafx/fxml/doc-files/introduction_to_fxml.html)
* [Getting Started with JavaFX](https://openjfx.io/openjfx-docs/)
* [JavaFX Tutorial](https://code.makery.ch/library/javafx-tutorial/)
* [JavaFX Java GUI Design Tutorials](https://www.youtube.com/playlist?list=PL6gx4Cwl9DGBzfXLWLSYVy8EbTdpGbUIG)

