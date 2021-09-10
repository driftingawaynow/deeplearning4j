/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2020
 * Instructor: Prof. Brian King
 *
 * Name: Charlie Taylor
 * Section: 01 - 11:30am
 * Date: 11/14/2020
 * Time: 11:48 AM
 *
 * Project: csci205finalproject
 *
 * Description: A way to train models based on user input, based on the quickstart provided by DL4J found at
 * https://deeplearning4j.konduit.ai/getting-started/quickstart
 *
 * ****************************************
 */

package DL4JView;

import org.deeplearning4j.nn.conf.layers.Layer;
import org.deeplearning4j.nn.multilayer.*;
import org.deeplearning4j.nn.conf.*;
import org.deeplearning4j.nn.conf.layers.*;
import org.deeplearning4j.nn.weights.*;
import org.deeplearning4j.optimize.listeners.*;
import org.nd4j.evaluation.classification.*;
import org.deeplearning4j.datasets.iterator.impl.EmnistDataSetIterator;

import org.nd4j.linalg.learning.config.*; // for different updaters like Adam, Nesterovs, etc.
import org.nd4j.linalg.activations.Activation; // defines different activation functions like RELU, SOFTMAX, etc.
import org.nd4j.linalg.lossfunctions.LossFunctions; // mean squared error, multiclass cross entropy, etc.

import java.io.IOException;
import java.util.ArrayList;

/**
 * Class to create models based on user input from GUI, creates layers, builds a configuration file and
 * calculates run time and accuracy for each model.
 */
public class ModelBuilder {

    private static double runTimeSeconds = 0;
    private static double accuracy = 0;
    private static int batchSize;
    private static int numLayers;
    private static int numEpochs;
    private static ArrayList activationFunction;
    private static ArrayList lossFunction;
    private static ArrayList weightInit;
    private final ArrayList inDataPoints;
    private final ArrayList outDataPoints;
    private MultiLayerConfiguration conf;
    private ArrayList generatedLayers;
    private final int outputNum;


    /**
     * Initilization for ModelBuilder based on user parameters, generates layers based on user input, uses the layers
     * to create a configuration which is then used to train a model and report time to run, and accuracy of the model.
     *
     * @param batchSize
     * @param numLayers
     * @param numEpochs
     * @param inDataPoints
     * @param outDataPoints
     * @param weightInit
     * @param activationFunction
     * @param lossFunction
     * @throws IOException
     */
    public ModelBuilder(int batchSize, int numLayers, int numEpochs,
                        ArrayList inDataPoints, ArrayList outDataPoints, ArrayList weightInit,
                        ArrayList activationFunction, ArrayList lossFunction) throws IOException {
        this.inDataPoints = inDataPoints;
        this.outDataPoints = outDataPoints;
         this.batchSize = batchSize;
         this.numLayers = numLayers;
         this.numEpochs = numEpochs;
         this.activationFunction = activationFunction;
         this.lossFunction = lossFunction;
         this.weightInit = weightInit;

        EmnistDataSetIterator.Set emnistSet = EmnistDataSetIterator.Set.BALANCED;
        EmnistDataSetIterator emnistTrain = new EmnistDataSetIterator(emnistSet, this.batchSize, true);
        EmnistDataSetIterator emnistTest = new EmnistDataSetIterator(emnistSet, this.batchSize, false);

        // integer for reproducibility of a random number generator
        outputNum = EmnistDataSetIterator.numLabels(emnistSet);

        // Generate the layers based on user input
        generateLayers();

        // int outputNum = EmnistDataSetIterator.numLabels(emnistSet); // integer for reproducibility of a random number generator

        // Generate the configuration
        generateConf();


        // create the MLN using the configuration
        MultiLayerNetwork network = new MultiLayerNetwork(conf);
        network.init();

        // pass a training listener that reports score every 10 iterations
        int eachIterations = 10;
        network.addListeners(new ScoreIterationListener(eachIterations));

        // start timer for fitting model
        long startTime = System.nanoTime();

        // fit a dataset for multiple epochs
        network.fit(emnistTrain, this.numEpochs);

        // Calculate time to fit model
        long endTime = System.nanoTime();
        long runTime = (endTime - startTime);
        runTimeSeconds = (double)runTime / 1000000000;
        System.out.println("Runtime: " + runTimeSeconds + "s");

        // evaluate basic performance
        Evaluation eval = network.evaluate(emnistTest);
        System.out.println("Accuracy: " + eval.accuracy());
        accuracy = eval.accuracy();
        System.out.println("Precision: " + eval.precision());
        System.out.println("Recall: " + eval.recall());

        // evaluate ROC and calculate the Area Under Curve
        ROCMultiClass roc = network.evaluateROCMultiClass(emnistTest, 0);
        //roc.calculateAUC(classIndex);

        // optionally, you can print all stats from the evaluations
        System.out.print(eval.stats());
        System.out.print(roc.stats());

    }

    /**
     * Generate configuration based on the number of layers using the user inputs.
     */
    private void generateConf() {
        int rngSeed = 123;
        if(this.numLayers == 1) {
            conf = new NeuralNetConfiguration.Builder() // total output classes
                    .seed(rngSeed)
                    .updater(new Adam())
                    .l2(1e-4)
                    .list()
                    .layer((Layer) this.generatedLayers.get(0))
                    .build();
        } else if(this.numLayers == 2) {
            conf = new NeuralNetConfiguration.Builder() // total output classes
                    .seed(rngSeed)
                    .updater(new Adam())
                    .l2(1e-4)
                    .list()
                    .layer((Layer) this.generatedLayers.get(0))
                    .layer((Layer) this.generatedLayers.get(1))
                    .build();
        } else if(this.numLayers == 3) {
            conf = new NeuralNetConfiguration.Builder() // total output classes
                    .seed(rngSeed)
                    .updater(new Adam())
                    .l2(1e-4)
                    .list()
                    .layer((Layer) this.generatedLayers.get(0))
                    .layer((Layer) this.generatedLayers.get(1))
                    .layer((Layer) this.generatedLayers.get(2))
                    .build();
        }
    }

    /**
     * Generate an Arraylist to hold all of the layers based on user input
     */
    private void generateLayers() {
        generatedLayers = new ArrayList();
        for (int i = 0; i < this.numLayers; i++) {
            if(i == this.numLayers - 1) {
                OutputLayer outputLayer = new OutputLayer.Builder(filterLoss(this.lossFunction.get(i).toString()))
                        .nIn((Integer) this.inDataPoints.get(i))
                        .nOut(outputNum)
                        .activation(filterActivation(this.activationFunction.get(i).toString()))
                        .weightInit(filterWeight(this.weightInit.get(i).toString()))
                        .build();
                generatedLayers.add(outputLayer);
            }
            else {
                DenseLayer middleLayer = new DenseLayer.Builder()
                        .nIn((Integer) this.inDataPoints.get(i))
                        .nOut((Integer) this.outDataPoints.get(i))
                        .activation(filterActivation(this.activationFunction.get(i).toString()))
                        .weightInit(filterWeight(this.weightInit.get(i).toString()))
                        .build();
                generatedLayers.add(middleLayer);
            }
        }
    }

    /**
     * Filter the text string input of the Activation function and return the Activation function to be used
     *
     * @param sActivationFunction - String of activation function from GUI
     * @return - Activation function to be used
     */
    private Activation filterActivation(String sActivationFunction) {
        switch (sActivationFunction) {
            case "RELU":
                return Activation.RELU;
            case "SOFTMAX":
                return Activation.SOFTMAX;
            case "SIGMOID":
                return Activation.SIGMOID;
            case "SOFTSIGN":
                return Activation.SOFTSIGN;
            case "CUBE":
                return Activation.CUBE;
            case "TANH":
                return Activation.TANH;
            case "SOFTPLUS":
                return Activation.SOFTPLUS;
        }
        return null;
    }

    /**
     * Filter the text string input of the Weight Initialization and return the Weight Initialization to be used
     *
     * @param sWeightInit - String of weight initialization from the GUI
     * @return - Weight initialization to be used
     */
    private WeightInit filterWeight(String sWeightInit) {
        switch (sWeightInit) {
            case "XAVIER":
                return WeightInit.XAVIER;
            case "NORMAL":
                return WeightInit.NORMAL;
            case "ZERO":
                return WeightInit.ZERO;
            case "ONES":
                return WeightInit.ONES;
            case "RELU":
                return WeightInit.RELU;
        }
        return null;
    }

    /**
     * Filter the text string input of the Loss function and return the Loss function to be used
     *
     * @param sLossFunction - String of loss function from GUI
     * @return - loss function to be used
     */
    private LossFunctions.LossFunction filterLoss(String sLossFunction) {
        switch (sLossFunction) {
            case "NEGATIVELOSSLIKELIHOOD":
                return LossFunctions.LossFunction.NEGATIVELOGLIKELIHOOD;
            case "MSE":
                return LossFunctions.LossFunction.MSE;
            case "MEAN_ABSOLUTE_ERROR":
                return LossFunctions.LossFunction.MEAN_ABSOLUTE_ERROR;
            case "POISSON":
                return LossFunctions.LossFunction.POISSON;
            case "SQUARED_LOSS":
                return LossFunctions.LossFunction.SQUARED_LOSS;
        }
        return null;
    }

    public static double getRunTimeSeconds() {
        return runTimeSeconds;
    }

    public static double getAccuracy() {
        return accuracy;
    }
}
