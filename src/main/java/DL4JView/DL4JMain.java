package DL4JView;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import javafx.event.ActionEvent;

public class DL4JMain extends Application {

    private DL4JView theView;
    private DL4JController theController;

    @Override
    public void init() throws Exception {
        super.init();
        this.theView = new DL4JView();


    }

    @Override
    public void start(Stage primaryStage) {


        this.theController = new DL4JController(theView);
        Scene scene = new Scene(theView.getRoot(), 1000, 800, Color.DARKGRAY);
        primaryStage.setTitle("MNIST Neural Network Simulation");
        primaryStage.setScene(scene);
        primaryStage.show();



        /**
        theView.getResetBtn().setOnAction((ActionEvent event ) ->{
            System.out.println(theView.getNumEpochs().getText()+""+theView.getNumLayers());
            System.out.println("Layer 1"+theView.getLayers().get(1));


        });

        System.out.println("");

         **/

    }

    public static void main(String[] args) {
        launch(args);
    }

}
