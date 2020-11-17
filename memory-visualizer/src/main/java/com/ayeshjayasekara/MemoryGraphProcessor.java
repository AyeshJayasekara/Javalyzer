package com.ayeshjayasekara;

import com.ayeshjayasekara.configuration.ConfigurationProcessor;
import com.opencsv.CSVReader;
import javafx.application.Application;

import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

import java.io.FileReader;
import java.util.List;
import java.util.Properties;

public class MemoryGraphProcessor extends Application{

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Memory Usage Visualization");

        List<String> rawParameters = getParameters().getRaw();
        Properties properties = ConfigurationProcessor.fetchProperties(rawParameters.get(0));

        final NumberAxis yAxis = new NumberAxis(Double.parseDouble(properties.getProperty(ConfigurationProcessor.MEMORY_GRAPH_Y_AXIS_START)),
                Double.parseDouble(properties.getProperty(ConfigurationProcessor.MEMORY_GRAPH_Y_MAX)),
                Double.parseDouble(properties.getProperty(ConfigurationProcessor.MEMORY_GRAPH_Y_TICKS)));
        final CategoryAxis xAxis = new CategoryAxis();

        final LineChart<String, Number> lineChart = new LineChart<>(xAxis, yAxis);
        yAxis.setLabel("Usage in Megabytes");
        xAxis.setLabel("Time");
        lineChart.setCreateSymbols(false);
        lineChart.setTitle("Memory Usage Over Time");

        XYChart.Series series = new XYChart.Series();
        XYChart.Series availableMemory = new XYChart.Series();
        XYChart.Series usedMemory = new XYChart.Series();
        XYChart.Series freeMemory = new XYChart.Series();
        XYChart.Series swapMemory = new XYChart.Series();

        series.setName("Main Memory");
        availableMemory.setName("Available Memory");
        usedMemory.setName("Used Memory");
        freeMemory.setName("Free Memory");
        swapMemory.setName("Swap Memory");

        try (CSVReader dataReader = new CSVReader(new FileReader(properties.getProperty(ConfigurationProcessor.CSV_PATH)))) {
            String[] nextLine;
            dataReader.readNext();
            while ((nextLine = dataReader.readNext()) != null) {

                String time = String.valueOf(nextLine[6]);
                int totalMemory = Integer.parseInt(nextLine[7]);
                series.getData().add(new XYChart.Data(time, totalMemory));

                int available = Integer.parseInt(nextLine[0]);

                availableMemory.getData().add(new XYChart.Data(time, available));

                int used = Integer.parseInt(nextLine[9]);
                usedMemory.getData().add(new XYChart.Data(time, used));

                int free = Integer.parseInt(nextLine[4]);
                freeMemory.getData().add(new XYChart.Data(time, free));

                int swap = Integer.parseInt(nextLine[10]);
                swapMemory.getData().add(new XYChart.Data(time, swap));
            }
        }

        lineChart.getData().addAll(series, availableMemory, usedMemory, freeMemory, swapMemory);
        Scene scene = new Scene(lineChart,
                Double.parseDouble(properties.getProperty(ConfigurationProcessor.MEMORY_GRAPH_WIDTH)),
                Double.parseDouble(properties.getProperty(ConfigurationProcessor.MEMORY_GRAPH_HEIGHT)));

        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
