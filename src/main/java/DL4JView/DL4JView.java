/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2020
 * Instructor: Prof. Brian King
 *
 * Name: Brandon Burkett
 * Section: 02 - 8:50am
 * Date: 11/24/20
 * Time: 4:19 PM
 *
 * Project: csci205finalproject
 * Package: DL4JView
 * Class: DL4JView
 *
 * Description:
 *
 * ****************************************
 */
package DL4JView;

import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.control.Label;

import java.util.ArrayList;

public class DL4JView {
    private HBox root;
    private VBox modelAdj;
    private ComboBox numLayers;
    private TextField batchSize;
    private TextField numEpochs;
    private VBox layerAdj;
    private VBox outputPane;
    private Button resetBtn;
    private ArrayList<VBox> layers;
    private VBox newV;
    private TextField inDataPoints;
    private TextField outDataPoints;
    private ComboBox weightInit;
    private ComboBox activationFunc;
    private ComboBox lossFunc;
    private Button modelAdjBtn;
    private Label outputHead;
    private Button nextLayerBtn;
    private LineChart<Number, Number> accuracyChart;
    private Button outputBtn;
    private Label trainLbl;
    private Label accuracyLbl;
    private LineChart runTimeChart;

    public XYChart.Series getAccSeries() {
        return accSeries;
    }

    public XYChart.Series getRunTimeSeries() {
        return runTimeSeries;
    }

    private XYChart.Series accSeries;
    private XYChart.Series runTimeSeries;


    public  DL4JView() {
        this.root = new HBox();
        root.setPadding(new Insets(15));

        // Left Pane for modelAdj
        layerPane();

        modelPane();


        outputPane();

        // Separators for different columns
        Separator leftSeparator = new Separator(Orientation.VERTICAL);
        leftSeparator.setPadding(new Insets(0, 10, 0, 10));
        Separator rightSeparator = new Separator(Orientation.VERTICAL);
        rightSeparator.setPadding(new Insets(0, 10, 0, 10));

        root.getChildren().addAll(modelAdj,
                leftSeparator,
                layerAdj,
                rightSeparator,
                outputPane);

    }



    /**
     * Output pane initialization
     * Add Label Head, buttons for event handlers and lables for the last simulations
     * run time and accuracy
     *
     */
    private void outputPane() {

        outputPane = new VBox(10);
        outputPane.setPrefWidth(400);
        //outputPane.setAlignment(Pos.CENTER);
        outputHead = new Label("Output Data:");
        outputHead.setStyle("-fx-font: 20 arial; -fx-font-weight: bold;");
        outputHead.setPadding(new Insets(10, 0, 20, 0));
        outputHead.setUnderline(true);
        outputPane.getChildren().add(outputHead);

        //defining the axes
        final CategoryAxis accXAxis = new CategoryAxis();
        final NumberAxis accYAxis = new NumberAxis();
        accXAxis.setLabel("Test Number");
        accYAxis.setLabel("Accuracy(%)");
        accSeries = new XYChart.Series();
        accSeries.setName("Accuracy");

        //creating accuracy chart
        accuracyChart = new LineChart(accXAxis,accYAxis);
        accuracyChart.getData().add(accSeries);
        accuracyChart.setLayoutY(.6);
        accuracyChart.setTitle("Accuracy Plot");
        outputPane.getChildren().add(accuracyChart);




        final CategoryAxis runTimeXAxis = new CategoryAxis();
        final NumberAxis runTimeYAxis = new NumberAxis();
        runTimeXAxis.setLabel("Test Number");
        runTimeYAxis.setLabel("Run Time(seconds)");
        runTimeSeries = new XYChart.Series();
        runTimeSeries.setName("Run Time");

        //creating the chart
        runTimeChart = new LineChart(runTimeXAxis,runTimeYAxis);
        runTimeChart.setTitle("Run Time Plot");
        runTimeChart.setLayoutY(7.0);
        runTimeChart.getData().add(runTimeSeries);
        outputPane.getChildren().add(runTimeChart);









        outputBtn = new Button("Start Simulation");
        outputPane.getChildren().add(outputBtn);

        outputPane.getChildren().add(new Label("Most Recent Run:"));

        outputPane.getChildren().add((new Label("Accuracy:")));
        accuracyLbl = new Label("");
        accuracyLbl.setMinWidth(75);
        accuracyLbl.setMinHeight(25);
        accuracyLbl.setBorder(new Border(new BorderStroke(null,
                BorderStrokeStyle.SOLID,
                new CornerRadii(4),
                BorderWidths.DEFAULT)));
        outputPane.getChildren().add(accuracyLbl);

        outputPane.getChildren().add((new Label("Training Time:")));
        trainLbl = new Label("");
        trainLbl.setMinWidth(75);
        trainLbl.setMinHeight(25);
        trainLbl.setBorder(new Border(new BorderStroke(null,
                BorderStrokeStyle.SOLID,
                new CornerRadii(4),
                BorderWidths.DEFAULT)));
        outputPane.getChildren().add(trainLbl);

        resetBtn = new Button("Reset Output Data");

        outputPane.getChildren().add(resetBtn);

    }


    // Middle Pane for Layer Adjustments
   private  void layerPane() {
        layerAdj = new VBox(10);
        layerAdj.setPrefWidth(250);
        //layerAdj.setAlignment(Pos.CENTER_LEFT);
        Label layerHead = new Label("Layer Adjustments:");
        layerHead.setStyle("-fx-font: 20 arial; -fx-font-weight: bold;");
        layerHead.setPadding(new Insets(10, 0, 20, 0));
        layerHead.setUnderline(true);
        layerAdj.getChildren().add(layerHead);

       layerAdj.getChildren().add(new Label("Input Data Points must be the same as " +
               "\nprevious layers' output data points"));

        layerAdj.getChildren().add(new Label("First layer input data points for MNIST " +
                "\nmust be 784 (28x28 pixels)"));

        layerAdj.getChildren().add(new Label("Input Data Points"));
        inDataPoints = new TextField();
        layerAdj.getChildren().add(inDataPoints);


        layerAdj.getChildren().add(new Label("Output Data Points"));
        outDataPoints = new TextField();
        layerAdj.getChildren().add(outDataPoints);

        layerAdj.getChildren().add(new Label("Weight Initialization"));
        weightInit = new ComboBox();
        weightInit.getItems().addAll(
                "XAVIER",
                "NORMAL",
                "ZERO",
                "ONES",
                "RELU"
        );
        layerAdj.getChildren().add(weightInit);

        layerAdj.getChildren().add(new Label("Activation Function"));
        activationFunc = new ComboBox();
        activationFunc.getItems().addAll(
                "RELU",
                "SOFTMAX",
                "SIGMOID",
                "SOFTSIGN",
                "CUBE",
                "TANH",
                "SOFTPLUS"
        );
        layerAdj.getChildren().add(activationFunc);

        layerAdj.getChildren().add(new Label("Loss Function"));
        lossFunc = new ComboBox();
        lossFunc.getItems().addAll(
                "NEGATIVELOSSLIKELIHOOD",
                "MSE",
                "MEAN_ABSOLUTE_ERROR",
                "POISSON",
                "SQUARED_LOSS"
        );
        layerAdj.getChildren().add(lossFunc);




       /**Unused Pagination feature for layer pane

        //Pagination pagination = new Pagination(numLayers);





         * Created an array of Vboxs so each page will be able to contain different values. Then added the VBoxs to the overall layer Vbox


        layers = new ArrayList<VBox>(3);
        for(int i=0; i<3; i++){
            newV = new VBox();
            ArrayList layerElements = new ArrayList();



            newV.getChildren().add(new Label("Input Data Points"));
            inDataPoints = new TextField();
            newV.getChildren().add(inDataPoints);


            newV.getChildren().add(new Label("Output Data Points"));
            outDataPoints = new TextField();
            newV.getChildren().add(outDataPoints);

            newV.getChildren().add(new Label("Weight Initialization "));
            weightInit = new ComboBox();
            weightInit.getItems().addAll(
                    "XAVIER",
                    "RELU",
                    "NORMAL"
            );
            newV.getChildren().add(weightInit);

            newV.getChildren().add(new Label("Activation Function"));
            activationFunc = new ComboBox();
            activationFunc.getItems().addAll(
                    "SIGMOID",
                    "TANH",
                    "STEP"
            );
            newV.getChildren().add(activationFunc);

            newV.getChildren().add(new Label("Loss Function"));
            lossFunc = new ComboBox();
            lossFunc.getItems().addAll(
                    "NEGATIVELOSSLIKELIHOOD",
                    "POISSON",
                    "SQUARED_LOSS"
            );

            newV.getChildren().add(lossFunc);


            layers.add(newV);
        }

        pagination.setPageFactory((pageIndex) -> {

            Label label1 = new Label("Layer " + (pageIndex+1));
            label1.setFont(new Font("Arial", 24));

            return layers.get(pageIndex);
        });


        layerAdj.getChildren().add(pagination);
         */




        nextLayerBtn = new Button("Enter Layer Settings");
        layerAdj.getChildren().add(nextLayerBtn);



    }



    private void modelPane() {
        modelAdj = new VBox(10);
        modelAdj.setPrefWidth(250);
        Label modelHead = new Label("Model Adjustments:");
        modelHead.setStyle("-fx-font: 20 arial; -fx-font-weight: bold;");
        modelHead.setPadding(new Insets(10, 0, 20, 0));
        modelHead.setUnderline(true);
        modelAdj.getChildren().add(modelHead);

        // Combo Box for layer selector
        modelAdj.getChildren().add(new Label("Number of layers: "));
        this.numLayers = new ComboBox();
        numLayers.getItems().addAll(
                1,
                2,
                3
        );

        modelAdj.getChildren().add(numLayers);

        // Number of Epochs Text Field
        modelAdj.getChildren().add(new Label("Number of Epochs: "));
        numEpochs = new TextField();
        numEpochs.setAlignment(Pos.CENTER);
        numEpochs.setPrefColumnCount(5);

        modelAdj.getChildren().add(numEpochs);

        // Batch Size Text Field
        modelAdj.getChildren().add(new Label("Batch Size: "));
        batchSize = new TextField();
        batchSize.setAlignment(Pos.CENTER);
        batchSize.setPrefColumnCount(5);

        modelAdj.getChildren().add(batchSize);

        // Create a start button
        modelAdjBtn = new Button("Enter Model Adjustments");
        modelAdj.getChildren().add(modelAdjBtn);
    }

    /**
     * method to set a point in the accuracy plot
     * @param xAxis String var added to x axis
     * @param yAxis double var added toy axis
     */
    public void setAccuracy(String xAxis, double yAxis) {
        accSeries.getData().add(new XYChart.Data<>(xAxis, yAxis));
    }

    /**
     * method to set a point in the runtime plot
     * @param xAxis String var added to x axis
     * @param yAxis double var added toy axis
     */
    public void setRuntime(String xAxis, double yAxis) {
        runTimeSeries.getData().add(new XYChart.Data<>(xAxis, yAxis));
    }

    /**
     * Getters for elements of the front end
     */
    public HBox getRoot() {
        return root;
    }

    public VBox getModelAdj() {
        return modelAdj;
    }

    public ComboBox getNumLayers() {
        return numLayers;
    }



    public TextField getBatchSize() {
        return batchSize;
    }

    public TextField getNumEpochs() {
        return numEpochs;
    }

    public VBox getLayerAdj() {
        return layerAdj;
    }

    public VBox getOutputPane() {
        return outputPane;
    }

    public Button getResetBtn() {
        return resetBtn;
    }

    public ArrayList<VBox> getLayers() {
        return layers;
    }

    public VBox getNewV() {
        return newV;
    }

    public TextField getInDataPoints() {
        return inDataPoints;
    }

    public TextField getOutDataPoints() {
        return outDataPoints;
    }

    public ComboBox getWeightInit() {
        return weightInit;
    }

    public ComboBox getActivationFunc() {
        return activationFunc;
    }

    public ComboBox getLossFunc() {
        return lossFunc;
    }

    public Button getModelAdjBtn() {
        return modelAdjBtn;
    }

    public Button getNextLayerBtn() {
        return nextLayerBtn;
    }

    public LineChart<Number, Number> getAccuracyChart() {
        return accuracyChart;
    }

    public Button getOutputBtn() {
        return outputBtn;
    }


    public Label getTrainLbl() {
        return trainLbl;
    }

    public Label getOutputHead() {
        return outputHead;
    }

    public void setOutputHead(Label outputHead) {
        this.outputHead = outputHead;
    }

    public Label getAccuracyLbl() {
        return accuracyLbl;
    }

    public LineChart getRunTimeChart() {
        return runTimeChart;
    }

}

