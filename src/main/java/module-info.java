module cs.cvut.fel.pjv.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    requires com.fasterxml.jackson.databind;
    requires java.desktop;
    requires java.logging;


    opens cs.cvut.fel.pjv.demo to javafx.fxml;
    exports cs.cvut.fel.pjv.demo;
    exports cs.cvut.fel.pjv.demo.controller;
    opens cs.cvut.fel.pjv.demo.controller to javafx.fxml;

    opens cs.cvut.fel.pjv.demo.view to com.google.gson;
    exports cs.cvut.fel.pjv.demo.editMode;
    opens cs.cvut.fel.pjv.demo.editMode to javafx.fxml;
    opens cs.cvut.fel.pjv.demo.view.characters to com.google.gson;

}