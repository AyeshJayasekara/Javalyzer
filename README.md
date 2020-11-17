# Javalyzer
A simple modularized Java toolkit for parsing log files to generate visual outputs.

## How To Set Up

The modularized architecture enables each module to be used individually and independently.

<ul>
<li>
To get started change the javalyzer.properties with preferred settings. Initially you will only need to change 
file paths for log and csv files
</li>

<li>
You have to pass the absolute path of that property file as a command line argument whenever you run any of the below 
jars
</li>

<li>
Run Executor Module as long as you want to record the statistics
</li>

<li>
Run the Processor Module to parse the generated log file to csv format
</li>

<li>
Run the CPU Visualizer / Memory Visualizer Modules as needed.
</li>
</ul>

---
**NOTE**

Since the JavaFx packages are no longer bundled with the JRE, you will have to manually download the required packages

Steps are:

<ul>

<li>
Download the required libraries from : <a href="https://gluonhq.com/products/javafx/"> OpenJFX</a> (Tested with 11.0.2)
</li>

<li>
OpenJDK 11 has to be used.
</li>

<li>
java -p <<>JAVA FX HOME<<>/lib --add-modules javafx.controls -jar <<>jar name<<>.jar <<>absolute path to<<>/javalyzer.properties
</li>
</ul>

---

### Roadmap

You are most certainly welcome to contribute! Fork your own copy to get started. Don't forget to show some love! 
It's free!!

#### How to reach out?

##### Drop a mail to <a href="mailto:ejkpac@gmail.com">Ayesh Jayasekara</a> for your hugs & bugs! Peace!
 
