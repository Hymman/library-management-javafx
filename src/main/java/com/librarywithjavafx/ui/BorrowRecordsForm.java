package com.librarywithjavafx.ui;

import com.librarywithjavafx.DAO.BorrowRecordDAO;
import com.librarywithjavafx.model.BorrowRecord;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

public class BorrowRecordsForm {

    private TableView<BorrowRecord> table;

    public void display() {
        Stage window = new Stage();
        window.setTitle("Borrow Records List");

        table = new TableView<>();

        TableColumn<BorrowRecord, Integer> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<BorrowRecord, Integer> bookIdColumn = new TableColumn<>("Book ID");
        bookIdColumn.setCellValueFactory(new PropertyValueFactory<>("bookId"));

        TableColumn<BorrowRecord, String> nameColumn = new TableColumn<>("Borrower Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("borrowerName"));

        TableColumn<BorrowRecord, String> idNumberColumn = new TableColumn<>("ID Number");
        idNumberColumn.setCellValueFactory(new PropertyValueFactory<>("borrowerIdNumber"));

        TableColumn<BorrowRecord, Timestamp> borrowDateColumn = new TableColumn<>("Borrow Date");
        borrowDateColumn.setCellValueFactory(new PropertyValueFactory<>("borrowDate"));
        borrowDateColumn.setCellFactory(column -> new TableCell<BorrowRecord, Timestamp>() {
            @Override
            protected void updateItem(Timestamp item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(new SimpleDateFormat("dd.MM.yyyy HH:mm").format(item));
                }
            }
        });

        TableColumn<BorrowRecord, Timestamp> returnDateColumn = new TableColumn<>("Return Date");
        returnDateColumn.setCellValueFactory(new PropertyValueFactory<>("returnDate"));
        returnDateColumn.setCellFactory(column -> new TableCell<BorrowRecord, Timestamp>() {
            @Override
            protected void updateItem(Timestamp item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setText(null);
                } else if (item == null) {
                    setText("Not Returned");
                    setStyle("-fx-text-fill: red;"); // İade edilmemişse kırmızı yazı
                } else {
                    setText(new SimpleDateFormat("dd.MM.yyyy HH:mm").format(item));
                    setStyle(""); // Normal stil
                }
            }
        });




        table.getColumns().addAll(idColumn, bookIdColumn, nameColumn, idNumberColumn, borrowDateColumn, returnDateColumn);

        Button refreshButton = new Button("Refresh");
        refreshButton.setOnAction(e -> loadData());

        loadData();

        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(20));
        vbox.getChildren().addAll(table,refreshButton);

        Scene scene = new Scene(vbox, 800, 400);
        window.setScene(scene);
        window.show();
    }

    private void loadData() {
        BorrowRecordDAO dao = new BorrowRecordDAO();
        List<BorrowRecord> records = dao.getAllRecords();
        ObservableList<BorrowRecord> data = FXCollections.observableArrayList(records);
        table.setItems(data);
    }
}
