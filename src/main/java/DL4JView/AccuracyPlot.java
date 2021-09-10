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

package DL4JView;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;


public class AccuracyPlot extends Application {
    private LineChart<String, Number> lineChart;
    private XYChart.Series<String, Number> series;

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

        //creates the line chart and adds the axes
        initLineChart(xAxis, yAxis);

        //defines the series to display data
        defineSeries();

        // add series to chart
        lineChart.getData().add(series);
        
        setupScene(primaryStage, lineChart);

    }

    /**
     * defining a series to display data
     * sets series name
     */
    private void defineSeries() {
        series = new XYChart.Series<>();
        series.setName("Data Series");
    }

    /**
     * Creates the line chart, sets the title and disables animations
     * @param xAxis
     * @param yAxis
     */
    private void initLineChart(CategoryAxis xAxis, NumberAxis yAxis) {
        //creating the line chart with two axis created above
        lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setTitle("Neural Network Accuracy Plot");
        lineChart.setAnimated(false); // disable animations
    }

    /**
     * sets up the line chart and adds it to the scene
     * @param primaryStage the stage
     * @param lineChart the chart used to plot the data
     */
    public void setupScene(Stage primaryStage, LineChart<String, Number> lineChart) {
        // setup scene
        Scene scene = new Scene(lineChart, 800, 600);
        primaryStage.setScene(scene);
    }

    /**
     * method to add
     * @param xAxis String var added to x axis
     * @param yAxis double var added toy axis
     */
    public void addAccuracyToRealTime(String xAxis, double yAxis) {
        series.getData().add(new XYChart.Data<>(xAxis, yAxis));
    }

    /**
     * Handles the primary stage and scene
     * @param primaryStage
     */
    public void startPrimaryStage(Stage primaryStage) {
        primaryStage.setTitle("Neural Network Accuracy Plot");

        //show the stage
        primaryStage.show();
    }

    /**
     * Creates the x and y axes on the chart
     * @param xAxis String
     * @param yAxis Double
     */
    public void createAxes(CategoryAxis xAxis, NumberAxis yAxis) {
        xAxis.setLabel("Simulations");
        xAxis.setAnimated(false); // axis animations are removed
        yAxis.setLabel("Accuracy");
        yAxis.setAnimated(false); // axis animations are removed
    }
}
