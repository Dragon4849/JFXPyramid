module com.example.cubemaven {
    requires javafx.controls;
    requires javafx.fxml;
    requires jimObjModelImporterJFX;
    requires LeapJava;


    opens com.example.jfxpyramid to javafx.fxml;
    exports com.example.jfxpyramid;
}