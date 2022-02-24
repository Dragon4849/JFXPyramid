package com.example.jfxpyramid;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;


import com.leapmotion.leap.*;

public class ModelLoadApp extends Application {


    private   Controller controller;
    private Group root;
    SmartGroup modelU;
    SmartGroup modelR;
    SmartGroup modelD;
    SmartGroup modelL;
    private float prevHandX;
    private float prevHandY;
    private final Scale scaleUp = new Scale(1.01,1.01,1.01);
    private final Scale scaleDn = new Scale(0.99,0.99,0.99);
    private Scene scene;
    private final DoubleProperty angleY = new SimpleDoubleProperty(0);
    private final String modelName = "/11803_Airplane_v1_l1.obj";

    // Possible model names
    // /11803_Airplane_v1_l1.obj
    // /12330_Statue_v1_L2.obj

    private void initHandsControl(Group groupU,Group groupR, Group groupD, Group groupL, Frame frame){
        float x = 0;
        float y = 0;

        if (!frame.hands().isEmpty()) {

            Hand userHand = frame.hands().get(0);
            x = userHand.palmPosition().getX() / 1000;
            y = userHand.palmPosition().getY()/ 2000;

            double deltaY = prevHandY - y;

            if (deltaY>0.001) {
                groupU.getTransforms().addAll(scaleDn);
                groupR.getTransforms().addAll(scaleDn);
                groupD.getTransforms().addAll(scaleDn);
                groupL.getTransforms().addAll(scaleDn);
            }

            if(deltaY<-0.001) {
                groupU.getTransforms().addAll(scaleUp);
                groupR.getTransforms().addAll(scaleUp);
                groupD.getTransforms().addAll(scaleUp);
                groupL.getTransforms().addAll(scaleUp);
            }

            Rotate yRotate = new Rotate(0, Rotate.Z_AXIS);
            groupU.getTransforms().addAll(yRotate);
            groupR.getTransforms().addAll(yRotate);
            groupD.getTransforms().addAll(yRotate);
            groupL.getTransforms().addAll(yRotate);
            yRotate.angleProperty().bind(angleY);

            double deltaX =  prevHandX-x;
            if (Math.abs(deltaX) > 0.05){
                deltaX = 0;
            }
            if (deltaX>0.0021){
                angleY.set(angleY.get() + deltaX/2);
            }
            if(deltaX<-0.0021){
                angleY.set(angleY.get() +deltaX/2);
            }
            System.out.println(angleY);
            prevHandX = x;
            prevHandY = y;
        }
    }
    private Scene createScene(String name) {

        modelU = new SmartGroup(name, "up");
        modelR = new SmartGroup(name, "right");
        modelD = new SmartGroup(name, "down");
        modelL = new SmartGroup(name, "left");

        root = new Group(modelU.getModel(), modelR.getModel(), modelD.getModel(), modelL.getModel());

        scene = new Scene(root,700, 700, true);

        PerspectiveCamera camera = new PerspectiveCamera();
        scene.setCamera(camera);

        camera.translateXProperty().set(-500);
        camera.translateYProperty().set(-300);
        camera.translateZProperty().set(-20000);

        scene.setFill(Color.BLACK);

        return scene;
    }

    @Override
    public void start(Stage stage) throws Exception {
        HandsTracking hands = new HandsTracking();
        controller = new Controller();
        stage.setScene(createScene(modelName));
        stage.show();
        controller.addListener(hands);

    }

    public static void main(String[] args) {
        launch(args);
    }

    private class HandsTracking extends Listener {


        @Override
        public void onInit(Controller controller) {
            System.out.println("Initialized");
        }

        public void onConnect(Controller controller) {
            System.out.println("Connected");
        }

        public void onDisconnect(Controller controller) {
            System.out.println("Disconnected");
        }

        public void onFrame(Controller controller) {
            Frame frame = controller.frame();
            Platform.runLater(() -> {
                initHandsControl(modelU.getModel(),modelR.getModel(),modelD.getModel(),modelL.getModel(), frame);


            });
        }
    }


}