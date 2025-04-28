package com.librarywithjavafx.ui;

import com.librarywithjavafx.DAO.BookDAO;
import com.librarywithjavafx.DAO.BorrowRecordDAO;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;



public class BorrowForm {

    public void display(int bookId,Runnable afterSuccessCallback){
        Stage window = new Stage();
        window.setTitle("Borrow Book Form");

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20));
        grid.setHgap(10);
        grid.setVgap(10);

        Label nameLabel = new Label("Borrower Name:");
        TextField nameInput = new TextField();

        Label idNumberLabel = new Label("Borrower ID Number: ");
        TextField idNumberInput = new TextField();

        Button saveBtn = new Button("Save");
        Button cancelBtn = new Button("Cancel");

        saveBtn.setOnAction(e ->{
            String borrowerName = nameInput.getText();
            String borrowerIdNumber = idNumberInput.getText();

            if(borrowerName.isEmpty() || borrowerIdNumber.isEmpty()){
                showAlert(Alert.AlertType.WARNING, "Warning", "Name or ID Number cannot be empty!");
                return;
            }

            BorrowRecordDAO borrowRecordDAO = new BorrowRecordDAO();
            borrowRecordDAO.addBorrowRecord(bookId,borrowerName,borrowerIdNumber);

            BookDAO bookDAO = new BookDAO();
            bookDAO.updateBorrowStatus(bookId,true);

            showAlert(Alert.AlertType.INFORMATION, "Borrow Success", "Book has been borrowed successfully!");

            afterSuccessCallback.run();
            window.close();




        });

        cancelBtn.setOnAction(e -> window.close());

        grid.add(nameLabel, 0, 0);
        grid.add(nameInput, 1, 0);
        grid.add(idNumberLabel, 0, 1);
        grid.add(idNumberInput, 1, 1);
        grid.add(saveBtn, 0, 2);
        grid.add(cancelBtn, 1, 2);

        Scene scene = new Scene(grid, 350, 200);
        window.setScene(scene);
        window.show();
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
