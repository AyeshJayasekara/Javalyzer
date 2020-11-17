package com.ayeshjayasekara.visualizer;

import com.opencsv.CSVReader;
import javafx.application.Application;

import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

import java.io.FileReader;

public class GraphProcessor extends Application{


    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Memory Usage Visualization");
        final NumberAxis yAxis = new NumberAxis(0, 16500, 50);
        final CategoryAxis xAxis = new CategoryAxis();

        final LineChart<String, Number> lineChart = new LineChart<>(xAxis, yAxis);
        yAxis.setLabel("Usage in Megabytes");
        xAxis.setLabel("Time");
        lineChart.setTitle("Memory Usage Over Time");

        XYChart.Series series = new XYChart.Series();
        XYChart.Series availableMemory = new XYChart.Series();
        XYChart.Series usedMemory = new XYChart.Series();

        series.setName("Main Memory");
        availableMemory.setName("Available Memory");
        usedMemory.setName("Used Memory");

        try (CSVReader dataReader = new CSVReader(new FileReader("/home/ayesh/Desktop/yourfile.csv"))) {
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
            }
        }

        lineChart.getData().addAll(series, availableMemory, usedMemory);
        Scene scene = new Scene(lineChart, 800, 700);
        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
