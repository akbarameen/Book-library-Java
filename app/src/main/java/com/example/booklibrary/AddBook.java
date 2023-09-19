package com.example.booklibrary;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Objects;

public class AddBook extends AppCompatActivity {
private EditText bTitle, bAuthor, bPages;
private DbHelper dbHelper;
String bookTitle, bookAuthor, bookPages;
int book_id;
//Button detailBtn;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_add_book);



        // hiding actionbar
        Objects.requireNonNull(getSupportActionBar()).hide();

        // initializing those editViews by finding View ID's
        bTitle = findViewById(R.id.bTitle);
        bAuthor = findViewById(R.id.bAuthor);
        bPages = findViewById(R.id.bPages);

        Button updateBtn = findViewById(R.id.updateBtn);
        Button deleteBtn = findViewById(R.id.deleteBtn);
//        detailBtn = findViewById(R.id.detailBtn);


        // Calling method after getting and setting values in it.
        gettingAndSettingValues();

        // Updating values by clicking on Update Button
        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Making object of DbHelper class so that we can have access to the updateBook method
                dbHelper = new DbHelper(AddBook.this);

                // UPDATING WHERE NAME = ?
                // dbHelper.updateBook( bookTitle, bTitle.getText().toString(), bAuthor.getText().toString(), Integer.parseInt(bPages.getText().toString()));

                // UPDATING WHERE ID = ?
                dbHelper.updateBook( book_id, bTitle.getText().toString(), bAuthor.getText().toString(), Integer.parseInt(bPages.getText().toString()));

                // after updating going back to mainActivity

                Toast.makeText(AddBook.this, "Book Updated", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(AddBook.this, MainActivity.class);
                startActivity(i);
            }
        });

        // deleting single row by clicking on delete button
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // CALLING CONFIRMATION METHOD
                confirmationDialog();

            }
        });

        //details button intent
//        detailBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(AddBook.this, BookDetails.class);
//                startActivity(i);
//            }
//        });

    }

    // Method for getting and setting values which we received by bundle passing(Intent) in editViews
    public void gettingAndSettingValues(){

        //  Getting values through Intent bundle Passing
        book_id = getIntent().getIntExtra("id", 1);
        bookTitle = getIntent().getStringExtra("title");
        bookAuthor = getIntent().getStringExtra("author");
        bookPages = String.valueOf(getIntent().getIntExtra("pages", 200));


        // Setting Values in those EditTextViews
        bTitle.setText(bookTitle);
        bAuthor.setText(bookAuthor);
        bPages.setText(bookPages);

    }

    // CONFIRMATION METHOD FOR DELETING SINGLE ROW DATA
    public void confirmationDialog(){

        AlertDialog.Builder alertDeleteDialog = new AlertDialog.Builder(AddBook.this);
        alertDeleteDialog.setTitle("Delete ");
        alertDeleteDialog.setIcon(R.drawable.ic_baseline_delete_24);
        alertDeleteDialog.setMessage("Are you sure you want to delete  this book?");

        alertDeleteDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                // Making object of DbHelper class so that we can have access to the deleteBook method
                dbHelper = new DbHelper(AddBook.this);

                // deleting single row
                dbHelper.deleteBook(book_id);

                // success message
                Toast.makeText(AddBook.this, "Book Deleted Successfully", Toast.LENGTH_SHORT).show();

                // going back to main activity and get refreshed data
                Intent intent = new Intent(AddBook.this, MainActivity.class);
                startActivity(intent);
                finish();


            }
        });

        alertDeleteDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        alertDeleteDialog.show();

    }
}