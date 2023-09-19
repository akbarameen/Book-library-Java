package com.example.booklibrary;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;

import java.util.Objects;

public class BookDetails extends AppCompatActivity {

    TextView detailBookTitle, detailBookAuthor ;
    String detail_b_Title, detail_b_Author, detail_b_Pages;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_book_details);
        // hiding actionbar
        Objects.requireNonNull(getSupportActionBar()).hide();


        //hooks
        detailBookTitle = findViewById(R.id.detailBookTitle);
//        detailBookTitle = findViewById(R.id.detailBookTitle);
        detailBookAuthor = findViewById(R.id.detailBookAuthor);

        // Calling method after getting and setting values in it.
        gettingAndSettingValues();

    }

    // Method for getting and setting values which we received by bundle passing(Intent) in editViews
    public void gettingAndSettingValues(){

        //  Getting values through Intent bundle Passing

        detail_b_Title = getIntent().getStringExtra("title");
        detail_b_Author = getIntent().getStringExtra("author");
        detail_b_Pages = String.valueOf(getIntent().getIntExtra("pages", 200));


        // Setting Values in those EditTextViews
        detailBookTitle.setText(detail_b_Title);
        detailBookAuthor.setText(detail_b_Author);
        //bPages.setText(detail_b_Pages);
    }
}