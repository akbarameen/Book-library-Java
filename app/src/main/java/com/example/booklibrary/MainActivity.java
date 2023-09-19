package com.example.booklibrary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    FloatingActionButton btnOpenDialog;
    EditText edtBookTitle, edtBookAuthor, edtBookPages, edtBookDescription;
    Button btnAction, singleDelete;

    // calling variables for displaying Recycler View
    RecyclerView RVBookLibrary;
    ArrayList<LibraryModel> libraryModelArrayList;
    RVLibraryAdapter customAdapter;
    DbHelper dbHelper;
    ImageView noDataImage;
    TextView noDataText;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // hiding actionbar
        Objects.requireNonNull(getSupportActionBar()).hide();

        noDataImage = findViewById(R.id.noDataImage);
        noDataText = findViewById(R.id.noDataText);
//        singleDelete = findViewById(R.id.singleDelete);

        displayData();
        addData();


    }
    // for adding data through custom alertBox
    public void addData(){
        btnOpenDialog = findViewById(R.id.btnOpenDialog);

        btnOpenDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Making object of dbHelper class
                DbHelper db = new DbHelper(MainActivity.this);

                // making object of Dialog class for creating custom alertBox
                Dialog dialog = new Dialog(MainActivity.this);
                dialog.setContentView(R.layout.add_update_lay);

                // these fields are inside dialog box that why we have to put dialog.findViewById
                edtBookTitle = dialog.findViewById(R.id.edtBookTitle);
                edtBookAuthor = dialog.findViewById(R.id.edtBookAuthor);
                edtBookPages = dialog.findViewById(R.id.edtBookPages);

                btnAction = dialog.findViewById(R.id.btnAction);

                btnAction.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // Inserting data
                        if (!edtBookTitle.getText().toString().equals("" ) && !edtBookAuthor.getText().toString().equals("") && !edtBookPages.getText().toString().equals("")){
                            String title = edtBookTitle.getText().toString();
                            String author = edtBookAuthor.getText().toString();
                            int pages = Integer.parseInt(edtBookPages.getText().toString());


                            db.addBook(title, author, pages);

                            Toast.makeText(MainActivity.this, "Added Successfully", Toast.LENGTH_SHORT).show();

                            // calling display method again to refresh the data
                            displayData();
                            customAdapter.notifyItemInserted(libraryModelArrayList.size()-1);
                            RVBookLibrary.scrollToPosition(libraryModelArrayList.size()-1);
                            dialog.dismiss();
                        }else{
                            Toast.makeText(MainActivity.this, "All Fields are required*", Toast.LENGTH_SHORT).show();
                        }

                    }
                });

                dialog.show();



            }

        });
    }

    // for displaying data form the database
    public void displayData(){
        RVBookLibrary = findViewById(R.id.RVBook);
        libraryModelArrayList = new ArrayList<>();
        dbHelper = new DbHelper(this);


        libraryModelArrayList = dbHelper.selectBook();

        if (libraryModelArrayList.isEmpty()) {
            noDataImage.setVisibility(View.VISIBLE);
            noDataText.setVisibility(View.VISIBLE);

        }
        else {
            customAdapter = new RVLibraryAdapter(this, libraryModelArrayList);
            RVBookLibrary.setLayoutManager(new LinearLayoutManager(this));
            RVBookLibrary.setAdapter(customAdapter);
            noDataImage.setVisibility(View.INVISIBLE);
            noDataText.setVisibility(View.INVISIBLE);
        }
    }

    // for deleting all data through menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.my_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()== R.id.deleteAll) {

            AlertDialog.Builder alertDeleteDialog = new AlertDialog.Builder(MainActivity.this);
            alertDeleteDialog.setTitle("Delete ");
            alertDeleteDialog.setIcon(R.drawable.ic_baseline_delete_24);
            alertDeleteDialog.setMessage("Are you sure, you want to delete all data?");

            alertDeleteDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    // Making object of DbHelper class so that we can have access to the deleteBook method
                    DbHelper helper = new DbHelper(MainActivity.this);
                    helper.deleteAllData();

                    Toast.makeText(MainActivity.this, "Deleted successfully", Toast.LENGTH_SHORT).show();
                    // for refreshing the data
                    Intent intent = new Intent(MainActivity.this, MainActivity.class);
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
        return super.onOptionsItemSelected(item);
    }


    // CONFIRMATION METHOD FOR DELETING SINGLE ROW DATA
//    public void confirmationDialog(){
//
//        AlertDialog.Builder alertDeleteDialog = new AlertDialog.Builder(MainActivity.this);
//        alertDeleteDialog.setTitle("Delete ");
//        alertDeleteDialog.setIcon(R.drawable.ic_baseline_delete_24);
//        alertDeleteDialog.setMessage("Are you sure you want to delete  this book?");
//
//        alertDeleteDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//
//                // Making object of DbHelper class so that we can have access to the deleteBook method
//                dbHelper = new DbHelper(MainActivity.this);
//
//                // deleting single row
//                dbHelper.deleteBook(book_id);
//
//                // success message
//                Toast.makeText(MainActivity.this, "Book Deleted Successfully", Toast.LENGTH_SHORT).show();
//
//                // going back to main activity and get refreshed data
//                Intent intent = new Intent(MainActivity.this, MainActivity.class);
//                startActivity(intent);
//                finish();
//
//
//            }
//        });
//
//        alertDeleteDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//
//            }
//        });
//        alertDeleteDialog.show();
//    }
}