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

public class CPUGraphProcessor extends Application{

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("CPU Usage Visualization");

        List<String> rawParameters = getParameters().getRaw();
        Properties properties = ConfigurationProcessor.fetchProperties(rawParameters.get(0));

        final NumberAxis yAxis = new NumberAxis(Double.parseDouble(properties.getProperty(ConfigurationProcessor.CPU_GRAPH_Y_AXIS_START)),
                Double.parseDouble(properties.getProperty(ConfigurationProcessor.CPU_GRAPH_Y_MAX)),
                Double.parseDouble(properties.getProperty(ConfigurationProcessor.CPU_GRAPH_Y_TICKS)));
        final CategoryAxis xAxis = new CategoryAxis();

        final LineChart<String, Number> lineChart = new LineChart<>(xAxis, yAxis);
        yAxis.setLabel("CPU LOADING (%)");
        xAxis.setLabel("Time");
        lineChart.setCreateSymbols(false);
        lineChart.setTitle("CPU Load Over Time");

        XYChart.Series series = new XYChart.Series();
        XYChart.Series last5 = new XYChart.Series();
        XYChart.Series last1 = new XYChart.Series();
        XYChart.Series last15 = new XYChart.Series();


        series.setName("CPU Load Averages");
        last5.setName("Load Average (Recent 5 Minutes)");
        last1.setName("Load Average (Recent 1 Minute)");
        last15.setName("Load Average (Recent 15 Minutes)");


        try (CSVReader dataReader = new CSVReader(new FileReader(properties.getProperty(ConfigurationProcessor.CSV_PATH)))) {
            String[] nextLine;
            dataReader.readNext();
            while ((nextLine = dataReader.readNext()) != null) {

                String time = String.valueOf(nextLine[6]);
                int totalMemory = Integer.parseInt(nextLine[7]);
                series.getData().add(new XYChart.Data(time, totalMemory));

                double recent5 = Double.parseDouble(nextLine[3]);

                last5.getData().add(new XYChart.Data(time, recent5));

                double recent1 = Double.parseDouble(nextLine[2]);
                last1.getData().add(new XYChart.Data(time, recent1));

                double recent15 = Double.parseDouble(nextLine[1]);
                last15.getData().add(new XYChart.Data(time, recent15));

            }
        }

        lineChart.getData().addAll(series, last5, last1, last15);
        Scene scene = new Scene(lineChart,
                Double.parseDouble(properties.getProperty(ConfigurationProcessor.CPU_GRAPH_WIDTH)),
                Double.parseDouble(properties.getProperty(ConfigurationProcessor.CPU_GRAPH_HEIGHT)));

        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
