package com.ayeshjayasekara;

import com.ayeshjayasekara.configuration.ConfigurationProcessor;
import com.ayeshjayasekara.processor.FileProcessor;

import java.io.IOException;
import java.util.Properties;

public class Javalyzer{

    public static void main(String[] args) throws IOException {

        Properties properties = ConfigurationProcessor.fetchProperties(args[0]);

        FileProcessor processor = new FileProcessor(properties.getProperty(ConfigurationProcessor.LOG_PATH));
        processor.process(properties.getProperty(ConfigurationProcessor.CSV_PATH));
    }
}
