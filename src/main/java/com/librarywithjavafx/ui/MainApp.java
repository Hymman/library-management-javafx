package com.librarywithjavafx.ui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Library Management System");

        Button btnList = new Button("List Books");
        Button btnAdd = new Button("Add Book");
        Button btnUpdate = new Button("Update Book");
        Button btnDelete = new Button("Delete Book");

        // TEK ListBooksForm yaratıyoruz!
        ListBooksForm listBooksForm = new ListBooksForm();

        btnList.setOnAction(e -> {
            listBooksForm.display();
        });

        btnAdd.setOnAction(e -> {
            AddBookForm addBookForm = new AddBookForm();
            addBookForm.display();
            listBooksForm.loadData();
        });

        btnUpdate.setOnAction(e -> {
            UpdateBookForm updateBookForm = new UpdateBookForm();
            updateBookForm.display();
            listBooksForm.loadData();
        });

        btnDelete.setOnAction(e -> {
            DeleteBookForm deleteBookForm = new DeleteBookForm();
            deleteBookForm.display();
            listBooksForm.loadData();
        });

        Button btnViewBorrowRecords = new Button("View Borrow Records");

        btnViewBorrowRecords.setOnAction(e -> {
            BorrowRecordsForm borrowRecordsForm = new BorrowRecordsForm();
            borrowRecordsForm.display();
        });



        VBox root = new VBox(15);
        root.setStyle("-fx-padding: 20");

// btnList butonunu büyütüyoruz
        btnList.setStyle("-fx-font-size: 20px; -fx-padding: 15 30 15 30");


// Butonları root'a ekliyoruz
        root.getChildren().addAll(btnList, btnAdd, btnUpdate, btnDelete,btnViewBorrowRecords);

        Scene scene = new Scene(root, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.show();


    }

    public static void main(String[] args) {
        launch(args);
    }
}
