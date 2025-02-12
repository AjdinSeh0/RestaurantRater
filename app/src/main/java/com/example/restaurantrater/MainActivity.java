package com.example.restaurantrater;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class MainActivity extends AppCompatActivity {
    long restaurantID = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initRateButton();
        initSaveButton();

    }

    private void initRateButton(){
        Button button = findViewById(R.id.buttonDishRating);
        button.setOnClickListener(v ->{

            FragmentManager fm = getSupportFragmentManager();
            ratePickDialog ratePickDialog = new ratePickDialog();
            ratePickDialog.show(fm, "ratePick");
        });
    }



    private void initSaveButton() {
        Button saveButton = findViewById(R.id.buttonSave);
        EditText nameInput = findViewById(R.id.editTextText);
        EditText streetInput = findViewById(R.id.editTextText2);
        EditText cityInput = findViewById(R.id.editTextText3);
        EditText stateInput = findViewById(R.id.editTextText4);
        EditText zipInput = findViewById(R.id.editTextText5);

        saveButton.setOnClickListener(v -> {
            try {
                String name = nameInput.getText().toString().trim();
                String street = streetInput.getText().toString().trim();
                String city = cityInput.getText().toString().trim();
                String state = stateInput.getText().toString().trim();
                String zip = zipInput.getText().toString().trim();

                if (name.isEmpty() || street.isEmpty() || city.isEmpty() || state.isEmpty() || zip.isEmpty()) {
                    Log.d("DATABASE ERROR", "Empty fields detected!");
                    return;
                }

                RestaurantDBHelper dbHelper = new RestaurantDBHelper(this);
                SQLiteDatabase db = dbHelper.getWritableDatabase();

                ContentValues values = new ContentValues();
                values.put("Name", name);
                values.put("StreetAddress", street);
                values.put("City", city);
                values.put("State", state);
                values.put("ZipCode", zip);

                // Insert into database and get ID
                long insertedId = db.insert("Restaurant", null, values);
                db.close(); // Close database

                if (insertedId == -1) {
                    Log.d("DATABASE ERROR", "Failed to insert restaurant!");
                } else {
                    RestaurantDBHelper.setLastInsertedRestaurantID(insertedId); // Store last ID statically
                    Log.d("DATABASE SUCCESS", "Restaurant inserted with ID: " + insertedId);
                }

            } catch (Exception e) {
                Log.d("DATABASE ERROR", e.toString());
            }
        });
    }



}