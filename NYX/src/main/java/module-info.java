module toulouse.miage.l3.nyx {
    requires javafx.controls;
    requires javafx.fxml;


    opens toulouse.miage.l3.nyx to javafx.fxml;
    exports toulouse.miage.l3.nyx;
}