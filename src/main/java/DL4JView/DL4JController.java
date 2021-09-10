package DL4JView;

import javafx.event.ActionEvent;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;

import java.io.IOException;
import java.util.ArrayList;

public class DL4JController {
    private final DL4JView theView;
    private  ArrayList inDataPoints;
    private  ArrayList outDataPoints;
    private ArrayList weightInit;
    private ArrayList activationFunction;
    private ArrayList lossFunction;
    private int batchSize;
    private int testCounter=1;




    private int numEpochs;
    private int numLayers;

    /**
     * The way the controller functions is first the user inputs the model adjustments. After the user enters the model
     * adjustments they enter the layer adjustments. These layer settings are stored in an arrays for each data type.
     * i.e. the input data points for all layers are stored in 1 array, similarly stored for outputdata points, loss and
     * activation function
     *
     * @param theView front end interface
     *
     */

    public DL4JController(DL4JView theView){
        this.theView = theView;
        /*
         *The following array lists are created to access the respective data points for each layer.
         *The first element in the ArrayList is the first layer, second element is second layer, etc.
         */
        this.inDataPoints = new ArrayList();
        this.outDataPoints = new ArrayList();
        this.weightInit = new ArrayList();
        this.activationFunction = new ArrayList();
        this.lossFunction = new ArrayList();







        //Action will get the values from the Model Pane

        theView.getModelAdjBtn().setOnAction((ActionEvent event ) ->{

            this.batchSize= Integer.parseInt(theView.getBatchSize().getText());
            this.numEpochs= Integer.parseInt(theView.getNumEpochs().getText());
            this.numLayers = Integer.parseInt(theView.getNumLayers().getValue().toString());
             System.out.println("Number of Epochs:"+theView.getNumEpochs().getText()+
                    "Number of Layers:"+theView.getNumLayers().getValue()+"Batch size:"+theView.getBatchSize().getText());
            });



        theView.getNextLayerBtn().setOnAction((ActionEvent event1) -> {
            try {

                inDataPoints.add(Integer.parseInt(theView.getInDataPoints().getText()));
                outDataPoints.add(Integer.parseInt(theView.getOutDataPoints().getText()));
                weightInit.add(theView.getWeightInit().getValue());
                activationFunction.add(theView.getActivationFunc().getValue());
                lossFunction.add(theView.getLossFunc().getValue());
            }catch (NumberFormatException numberFormatException) {

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Incorrect input specified!");
                alert.setHeaderText("Please enter values for each field");
                alert.show();
            }
                theView.getInDataPoints().setText("");
                theView.getOutDataPoints().setText("");
                theView.getActivationFunc().setValue(null);
                theView.getLossFunc().setValue(null);
                theView.getWeightInit().setValue(null);
                System.out.println("in Data pints"+ inDataPoints.get(0)+"\nActivationFunction:"+activationFunction.get(0));


            });

        theView.getResetBtn().setOnAction((ActionEvent event2) -> {
            for(int i = 0; i<=testCounter; i++) {
                theView.getAccSeries().getData().remove(i);
                theView.getRunTimeSeries().getData().remove(i);
            }

        });


        testCounter=1;
        theView.getOutputBtn().setOnAction((ActionEvent event) ->{

            try {

                ModelBuilder simulation = new ModelBuilder(this.batchSize, this.numLayers,this.numEpochs,
                        this.inDataPoints, this.outDataPoints, this.weightInit,this.activationFunction, this.lossFunction);
                theView.getAccuracyLbl().setText(""+simulation.getAccuracy());
                theView.getTrainLbl().setText(simulation.getRunTimeSeconds()+"");
                System.out.println("Run time: "+simulation.getRunTimeSeconds());
                System.out.println("Accuracy: "+ simulation.getAccuracy());

                theView.setAccuracy("Test "+testCounter,simulation.getAccuracy());

                //theView.setAccuracy("Test accuracy",simulation.getAccuracy());
                theView.setRuntime("Test"+testCounter,simulation.getRunTimeSeconds());
                testCounter++;




                //theView.getAccuracyChart().






            } catch (IOException e) {
                e.printStackTrace();
            }

        });

    }


    public ArrayList getInDataPoints() {
        return inDataPoints;
    }

    public ArrayList getOutDataPoints() {
        return outDataPoints;
    }

    public ArrayList getWeightInit() {
        return weightInit;
    }

    public ArrayList getActivationFunction() {
        return activationFunction;
    }

    public ArrayList getLossFunction() {
        return lossFunction;
    }

    public int getBatchSize() {
        return batchSize;
    }

    public int getNumEpochs() {
        return numEpochs;
    }

    public int getNumLayers() {
        return numLayers;
    }




}
