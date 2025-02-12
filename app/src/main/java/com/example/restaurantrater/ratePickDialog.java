package com.example.restaurantrater;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

public class ratePickDialog extends DialogFragment {
    long restaurantID;

    public ratePickDialog() {
        // Get the last inserted Restaurant ID from DBHelper
        restaurantID = RestaurantDBHelper.getLastInsertedRestaurantID();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.activity_rate_dish, container, false);

        EditText dishNameInput = view.findViewById(R.id.editTextText6);
        EditText dishTypeInput = view.findViewById(R.id.editTextText7);
        RatingBar ratingBar = view.findViewById(R.id.ratingDish);
        Button saveDishButton = view.findViewById(R.id.buttonSaveDish);

        saveDishButton.setOnClickListener(v -> {
            String dishName = dishNameInput.getText().toString().trim();
            String dishType = dishTypeInput.getText().toString().trim();
            float rating = ratingBar.getRating();  // Gets float rating

            if (dishName.isEmpty() || dishType.isEmpty()) {
                Toast.makeText(getContext(), "Please enter all fields!", Toast.LENGTH_SHORT).show();
                return;
            }

            if (restaurantID == -1) {
                Toast.makeText(getContext(), "Error: No restaurant found!", Toast.LENGTH_SHORT).show();
                dismiss();  // Close the dialog if no valid restaurant ID
                return;
            }

            try{
            // Save dish in database
            RestaurantDBHelper dbHelper = new RestaurantDBHelper(getContext());
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();

                values.put("Name", dishName );
                values.put("Type", dishType);
                values.put("Rating", rating);
                values.put("RestaurantID", restaurantID);

                db.insert("Dish", null, values);


                db.close();
            }
            catch (Exception e){
                Log.d("DATABSE ERROR", "SOMETHING WENT WRONG");
            }

            Toast.makeText(getContext(), "Dish saved successfully!", Toast.LENGTH_SHORT).show();
            dismiss();  // Close the dialog
        });

        return view;
    }
}
