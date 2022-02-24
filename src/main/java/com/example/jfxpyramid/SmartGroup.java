package com.example.jfxpyramid;

import com.interactivemesh.jfx.importer.obj.ObjModelImporter;
import javafx.scene.Group;
import javafx.scene.shape.MeshView;
import javafx.scene.transform.Rotate;

import java.net.URL;


public class SmartGroup extends Group{
    private Group model;
    String name;


    public SmartGroup(String name, String location){
        model = loadModel(getClass().getResource(name));
        if (location.equalsIgnoreCase("up")) {
            model.getTransforms().add(new Rotate(90, Rotate.Y_AXIS));
            model.getTransforms().add(new Rotate(-90, Rotate.X_AXIS));
            model.getTransforms().add(new Rotate(-20, Rotate.Y_AXIS));
            model.translateXProperty().set(0);
            model.translateYProperty().set(-4000);
        }


        if (location.equalsIgnoreCase("right")) {
            model.getTransforms().add(new Rotate(90, Rotate.Y_AXIS));
            model.getTransforms().add(new Rotate(-180, Rotate.X_AXIS));
            model.getTransforms().add(new Rotate(-20, Rotate.Y_AXIS));
            model.translateXProperty().set(4000);
            model.translateYProperty().set(0);
        }

        if (location.equalsIgnoreCase("down")) {
            model.getTransforms().add(new Rotate(90, Rotate.Y_AXIS));
            model.getTransforms().add(new Rotate(90, Rotate.X_AXIS));
            model.getTransforms().add(new Rotate(-20, Rotate.Y_AXIS));
            model.translateXProperty().set(0);
            model.translateYProperty().set(4000);
        }

        if (location.equalsIgnoreCase("left")) {
            model.getTransforms().add(new Rotate(90, Rotate.Y_AXIS));
            model.getTransforms().add(new Rotate(-20, Rotate.Y_AXIS));
            model.translateXProperty().set(-4000);
            model.translateYProperty().set(0);
        }

    }


    public Group getModel() {
        return model;
    }

    public void setModelName(String name){
        this.name = name;
    }

    private Group loadModel(URL url) {
        Group modelRoot = new Group();

        ObjModelImporter importer = new ObjModelImporter();
        importer.read(url);

        for (MeshView view : importer.getImport()) {
            modelRoot.getChildren().add(view);

        }

        return modelRoot;
    }
}
