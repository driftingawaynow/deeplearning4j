package deeplearning;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class ModelBuilderPlot extends Application {
    private HBox root;
    private VBox chartPane;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        QuickstartPlot();

        Scene scene = new Scene(chartPane, 800, 600, Color.DARKGRAY);
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public void QuickstartPlot(){
        this.root = new HBox();
        root.setPadding(new Insets(15));

        chartPane = new VBox(10);
        chartPane.setPrefWidth(300);

         NumberAxis xAxis = new NumberAxis();
         NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("xAxis");
        yAxis.setLabel("yAxis");
        //creating the chart
        final LineChart<Number,Number> dlChart =
                new LineChart<Number,Number>(xAxis,yAxis);
        dlChart.setTitle("Deeplearning Chart");

        XYChart.Series series = new XYChart.Series();
        series.setName("Deeplearning Chart");

        series.getData().add(new XYChart.Data(1, 23));
        series.getData().add(new XYChart.Data(12, 14));
        series.getData().add(new XYChart.Data(2, 15));
        series.getData().add(new XYChart.Data(3, 24));
        series.getData().add(new XYChart.Data(4, 34));
        series.getData().add(new XYChart.Data(5, 36));
        series.getData().add(new XYChart.Data(6, 22));
        series.getData().add(new XYChart.Data(7, 45));
        series.getData().add(new XYChart.Data(8, 43));
        series.getData().add(new XYChart.Data(9, 17));
        series.getData().add(new XYChart.Data(10, 29));
        series.getData().add(new XYChart.Data(11, 25));


        dlChart.getData().add(series);

        chartPane.getChildren().add(dlChart);



    }
}
