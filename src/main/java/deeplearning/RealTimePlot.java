/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2020
 * Instructor: Prof. Brian King
 *
 * Name: Nick Caravias
 * Section: 01 - 11:30am
 * Date: 11/30/2020
 * Time: 5:00 PM
 *
 * Project: csci205finalproject
 * Package: deeplearning
 *
 * Description: The realtime graph for the accuracy and runtime
 *
 * ****************************************
 */

package deeplearning;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

public class RealTimePlot extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        startPrimaryStage(primaryStage);


        //defining the axes
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        createAxes(xAxis, yAxis);

        //creating the line chart with two axis created above
        final LineChart<String, Number> lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setTitle("Neural Network Accuracy Plot");
        lineChart.setAnimated(false); // disable animations

        //defining a series to display data
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Data Series");

        // add series to chart
        lineChart.getData().add(series);

        setupScene(primaryStage, lineChart);

    }

    /**
     * sets up the line chart and adds it to the scene
     * @param primaryStage the stage
     * @param lineChart the chart used to plot the data
     */
    private void setupScene(Stage primaryStage, LineChart<String, Number> lineChart) {
        // setup scene
        Scene scene = new Scene(lineChart, 800, 600);
        primaryStage.setScene(scene);
    }

    private void startPrimaryStage(Stage primaryStage) {
        primaryStage.setTitle("Neural Network Accuracy Plot");

        //show the stage
        primaryStage.show();
    }

    private void createAxes(CategoryAxis xAxis, NumberAxis yAxis) {
        xAxis.setLabel("Simulations");
        xAxis.setAnimated(false); // axis animations are removed
        yAxis.setLabel("Accuracy");
        yAxis.setAnimated(false); // axis animations are removed
    }
}
