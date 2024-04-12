module cs.cvut.fel.pjv.demo {
    requires javafx.controls;
    requires javafx.fxml;


    opens cs.cvut.fel.pjv.demo to javafx.fxml;
    exports cs.cvut.fel.pjv.demo;
    exports cs.cvut.fel.pjv.demo.controller;
    opens cs.cvut.fel.pjv.demo.controller to javafx.fxml;
}