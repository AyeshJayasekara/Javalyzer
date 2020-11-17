package com.ayeshjayasekara.configuration;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public interface ConfigurationProcessor {

    String EXECUTION_INTERVAL = "execution_interval";
    String LOG_PATH = "log_path";
    String CSV_PATH = "csv_path";
    String MEMORY_GRAPH_WIDTH = "memory_width";
    String MEMORY_GRAPH_HEIGHT = "memory_height";
    String MEMORY_GRAPH_Y_AXIS_START = "memory_y_start";
    String MEMORY_GRAPH_Y_MAX= "memory_y_max";
    String MEMORY_GRAPH_Y_TICKS= "memory_y_ticks";
    String CPU_GRAPH_WIDTH = "cpu_width";
    String CPU_GRAPH_HEIGHT = "cpu_height";
    String CPU_GRAPH_Y_AXIS_START = "cpu_y_start";
    String CPU_GRAPH_Y_MAX= "cpu_y_max";
    String CPU_GRAPH_Y_TICKS= "cpu_y_ticks";

    static Properties fetchProperties(String propertyFilePath) throws IOException {
        Properties prop = new Properties();

        try (FileInputStream inputStream = new FileInputStream(propertyFilePath)) {
            prop.load(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return prop;
    }
}
