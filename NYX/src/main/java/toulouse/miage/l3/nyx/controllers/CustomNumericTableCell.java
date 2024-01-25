package toulouse.miage.l3.nyx.controllers;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import toulouse.miage.l3.nyx.core.model.Chaine;

public class CustomNumericTableCell extends TableCell<Chaine, Integer> {

    private final Button addButton;
    private final Button subtractButton;
    private final TextField inputField;

    public CustomNumericTableCell(TableColumn<Chaine, Integer> column) {
        addButton = new Button("+");
        subtractButton = new Button("-");
        inputField = new TextField();

        addButton.setOnAction(event -> incrementValue());
        subtractButton.setOnAction(event -> decrementValue());

        inputField.setOnAction(event -> commitEdit(Integer.parseInt(inputField.getText())));
        inputField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                cancelEdit();
            }
        });

        setGraphic((Node) createGraphic());
    }

    private void incrementValue() {
        int currentValue = getItem();
        setItem(currentValue + 1);
        commitEdit(currentValue + 1);
    }

    private void decrementValue() {
        int currentValue = getItem();
        setItem(currentValue - 1);
        commitEdit(currentValue - 1);
    }

    private HBox createGraphic() {
        HBox graphic = new HBox(addButton, subtractButton, inputField);
        graphic.setSpacing(5);
        graphic.setAlignment(Pos.CENTER);
        return graphic;
    }

    @Override
    public void updateItem(Integer item, boolean empty) {
        super.updateItem(item, empty);

        if (empty || item == null) {
            setGraphic(null);
        } else {
            inputField.setText(item.toString());
            setGraphic(createGraphic());
        }
    }
}