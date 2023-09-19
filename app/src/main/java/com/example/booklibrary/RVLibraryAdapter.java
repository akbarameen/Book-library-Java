package com.example.booklibrary;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Locale;

public class RVLibraryAdapter extends RecyclerView.Adapter<RVLibraryAdapter.ViewHolder> {

    Context context;
    ArrayList<LibraryModel> libraryModelArrayList;

    public RVLibraryAdapter(Context context, ArrayList<LibraryModel> libraryModelArrayList) {
        this.context = context;
        this.libraryModelArrayList = libraryModelArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.book_row, parent, false);
        return new ViewHolder(view) ;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    // we could do like this too  but making object once and use it again and again is more authentic
    //  holder.txtBTitle.setText(libraryModelArrayList.get(position).getTitle());
    LibraryModel model = libraryModelArrayList.get(position);
    holder.txtBTitle.setText(model.getTitle().toLowerCase(Locale.ROOT));
    holder.txtBAuthor.setText(model.getAuthor());
    holder.txtBPages.setText(String.valueOf(model.getPages()));
    holder.txtBPages.setText(String.valueOf(model.getPages()));
//    holder.txtBCount.setText(String.valueOf(model.getId()));

    // Intent passing into details of each book
    holder.itemView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i = new Intent(context, BookDetails.class);
            i.putExtra("id", model.getId());
            i.putExtra("title", model.getTitle());
            i.putExtra("author", model.getAuthor());
            i.putExtra("pages", model.getPages());
            context.startActivity(i);

        }


    });

    //updating single row
    holder.editButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i = new Intent(context, AddBook.class);
            i.putExtra("id", model.getId());
            i.putExtra("title", model.getTitle());
            i.putExtra("author", model.getAuthor());
            i.putExtra("pages", model.getPages());

            context.startActivity(i);
        }
    });

    // deleting single row
    holder.deleteButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            AlertDialog.Builder alertDeleteDialog = new AlertDialog.Builder(context);
            alertDeleteDialog.setTitle("Delete ");
            alertDeleteDialog.setIcon(R.drawable.ic_baseline_delete_24);
            alertDeleteDialog.setMessage("Are you sure you want to delete  this book?");

            alertDeleteDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    // Making object of DbHelper class so that we can have access to the deleteBook method
                    DbHelper dbHelper = new DbHelper(context);
                // deleting single row
                        dbHelper.deleteBook(model.getId());
                        Toast.makeText(context, "data deleted", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(context, MainActivity.class);
                        context.startActivity(intent);
                        ((Activity)context).finish();


                }
            });

            alertDeleteDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            alertDeleteDialog.show();




        }
    });


    }

    @Override
    public int getItemCount() {
        return libraryModelArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtBTitle ,txtBAuthor, txtBPages, txtBCount;
        Button editButton, deleteButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtBTitle = itemView.findViewById(R.id.txtBTitle);
            txtBAuthor = itemView.findViewById(R.id.txtBAuthor);
            txtBPages = itemView.findViewById(R.id.txtBPages);
            editButton = itemView.findViewById(R.id.editButton);
            deleteButton = itemView.findViewById(R.id.deleteButton);


        }
    }
}
