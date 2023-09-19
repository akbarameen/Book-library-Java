package com.example.booklibrary;

import static android.widget.Toast.makeText;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DbHelper extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "dbBookLibrary";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "tbl_books";
    private static  final String KEY_ID = "b_id";
    private static final String KEY_BOOK_TITLE = "b_title";
    private static final String KEY_BOOK_AUTHOR = "b_author";
    private static final String KEY_BOOK_PAGES = "b_pages";
    private static final String KEY_BOOK_DESCRIPTION = "b_description";


    public DbHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Creating the table
//        db.execSQL(" DROP TABLE IF EXISTS " + TABLE_NAME);

        db.execSQL(" Create table " + TABLE_NAME + " ( "
                        + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + KEY_BOOK_TITLE + " TEXT, "
                        + KEY_BOOK_AUTHOR + " TEXT, "
                        + KEY_BOOK_PAGES + " INTEGER );"
                );



    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL(" DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);

    }

    // Inserting data into the database
    public void addBook(String title, String author, int pages){
        // OPENING THE DATABASE
        SQLiteDatabase db = this.getWritableDatabase();

        // VALUES
        ContentValues values = new ContentValues();
        values.put(KEY_BOOK_TITLE, title);
        values.put(KEY_BOOK_AUTHOR, author);
        values.put(KEY_BOOK_PAGES, pages);
//        values.put(KEY_BOOK_DESCRIPTION, description);

        // Putting 3 parameters in insert method
       long result = db.insert(TABLE_NAME, null, values);
      // db.insert(TABLE_NAME, null, values);



        // closing the database
         db.close();

    }

    // For fetching the data from the database
    public ArrayList<LibraryModel> selectBook(){
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor =  db.rawQuery(" SELECT * FROM  " + TABLE_NAME , null  );
        ArrayList<LibraryModel> arrBooks = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                arrBooks.add(new LibraryModel(cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getInt(3)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return arrBooks;


    }


    // for updating

    public void updateBook( String orgBookName , String bookTitle, String BookAuthor, int BookPages){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_BOOK_TITLE, bookTitle);
        values.put(KEY_BOOK_AUTHOR, BookAuthor);
        values.put(KEY_BOOK_PAGES, BookPages);

        db.update(TABLE_NAME, values, KEY_BOOK_TITLE+ "=?", new String[]{orgBookName});
        db.close();

    }

    public void updateBook(int id, String bookTitle, String BookAuthor, int BookPages){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_BOOK_TITLE, bookTitle);
        values.put(KEY_BOOK_AUTHOR, BookAuthor);
        values.put(KEY_BOOK_PAGES, BookPages);


        db.update(TABLE_NAME, values, KEY_ID + "=" + id , null);
        db.close();


    }


    // Deleting Single book.
    public void deleteBook(int id) {

        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_NAME, KEY_ID + " =? ", new String[]{String.valueOf(id)});

        db.close();
    }


    // Deleting all data

    public void deleteAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(" DELETE FROM " + TABLE_NAME);
    }





}
