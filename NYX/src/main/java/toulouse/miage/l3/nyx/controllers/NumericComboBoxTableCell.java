package toulouse.miage.l3.nyx.controllers;

import javafx.scene.control.ComboBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

public class NumericComboBoxTableCell<S, T> extends TableCell<S, T> {
    private final ComboBox<T> comboBox;

    @SafeVarargs
    public NumericComboBoxTableCell(T... items) {
        this.comboBox = new ComboBox<>();
        comboBox.getItems().addAll(items);
        setGraphic(comboBox);
        setContentDisplay(ContentDisplay.GRAPHIC_ONLY);

        comboBox.setOnAction(event -> {
            commitEdit(comboBox.getSelectionModel().getSelectedItem());
        });
    }

    @Override
    public void updateItem(T item, boolean empty) {
        super.updateItem(item, empty);

        if (empty) {
            setGraphic(null);
        } else {
            comboBox.getSelectionModel().select(item);
            setGraphic(comboBox);
        }
    }

    public static <S, T> Callback<TableColumn<S, T>, TableCell<S, T>> forTableColumn(T... items) {
        return param -> new NumericComboBoxTableCell<>(items);
    }
}

