package com.ayeshjayasekara;

import com.ayeshjayasekara.configuration.ConfigurationProcessor;
import lombok.SneakyThrows;

import java.io.IOException;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;

public class Executor {

    public static void main(String[] args) throws IOException {


        String command = "(uptime && echo ----- && free -m && echo =====) >> ";
        ProcessBuilder processBuilder = new ProcessBuilder();

        Properties properties = ConfigurationProcessor.fetchProperties(args[0]);

        command = command.concat(properties.getProperty(ConfigurationProcessor.LOG_PATH));

        processBuilder.command("bash", "-c", command);


        System.out.println("Starting Execution.");
        System.out.print("Executing");
        Timer timer = new Timer();


        timer.schedule(new TimerTask() {
            @SneakyThrows
            public void run() {
                Process process = processBuilder.start();
                process.waitFor();
                System.out.print(".");
            }
        }, 0, Long.parseLong(properties.getProperty(ConfigurationProcessor.EXECUTION_INTERVAL)));


    }
    }
