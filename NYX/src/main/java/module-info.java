module toulouse.miage.l3.nyx {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens toulouse.miage.l3.nyx to javafx.fxml;
    exports toulouse.miage.l3.nyx;
    exports toulouse.miage.l3.nyx.controllers;
    opens toulouse.miage.l3.nyx.controllers to javafx.fxml;
    exports toulouse.miage.l3.nyx.core.model;
    opens toulouse.miage.l3.nyx.core.model to javafx.fxml;
    exports toulouse.miage.l3.nyx.core.utils;
    opens toulouse.miage.l3.nyx.core.utils to javafx.fxml;
}