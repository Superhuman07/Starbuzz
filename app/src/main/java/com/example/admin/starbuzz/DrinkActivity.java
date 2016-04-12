package com.example.admin.starbuzz;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class DrinkActivity extends Activity {
public final static String EXTRA_DRINKNO = "drinkNo";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink);
        int drinkNo = (Integer) getIntent().getExtras().get(EXTRA_DRINKNO);
//        Drink drink = Drink.drinks[drinkNo];
        try {


            SQLiteOpenHelper starbuzzDatabaseHelper = new StarbuzzDatabaseHelper(this);
            SQLiteDatabase db = starbuzzDatabaseHelper.getWritableDatabase();
            Cursor cursor = db.query("DRINK", new String[]{"NAME", "DESCRIPTION", "IMAGE_RESOURCE_ID", "FAVOURITE"}
                    , "_id = ?", new String[]{Integer.toString(drinkNo)}, null, null, null);

            if(cursor.moveToFirst())
            {
                // get the drink details from the cursor
                String nameText = cursor.getString(0);
                String descriptionText = cursor.getString(1);
                int photoId = cursor.getInt(2);
                boolean isFavourite = (cursor.getInt(3)==1);
                // Populate the drink name
                TextView name = (TextView) findViewById(R.id.name);
                name.setText(nameText);

                // Populate the drink description
                TextView description = (TextView) findViewById(R.id.description);
                description.setText(descriptionText);

                // Populate the drink image
                ImageView photo =(ImageView) findViewById(R.id.photo);
                photo.setImageResource(photoId);
                photo.setContentDescription(nameText);

                // Populate the favourite checkbox

                CheckBox favourite = (CheckBox) findViewById(R.id.favourite);
                favourite.setChecked(isFavourite);
            }
            cursor.close();
            db.close();
        }
        catch (SQLiteException e)
        {
            Toast toast = Toast.makeText(this, "Database unavailable ", Toast.LENGTH_SHORT);
            toast.show();
        }
        /*ImageView imageView = (ImageView) findViewById(R.id.photo);
        imageView.setImageResource(drink.getImageResourceId());
        imageView.setContentDescription(drink.getDescription());

        TextView name = (TextView) findViewById(R.id.name);
        name.setText(drink.getName());

        TextView description = (TextView) findViewById(R.id.description);
        description.setText(drink.getDescription());*/


    }

    // Update database when the checkbox is clicked
    public void onFavouriteClicked(View view){
        int drinkNo = (Integer) getIntent().getExtras().get(EXTRA_DRINKNO);
        CheckBox favourite = (CheckBox) findViewById(R.id.favourite);

        ContentValues drinkValues = new ContentValues();

        drinkValues.put("FAVOURITE", favourite.isChecked());

        SQLiteOpenHelper starbuzzDataHelper = new StarbuzzDatabaseHelper(DrinkActivity.this);

        try{
            SQLiteDatabase db = starbuzzDataHelper.getWritableDatabase();
            db.update("DRINK", drinkValues, "_id = ?", new String[]{ Integer.toString(drinkNo)});
            db.close();

        }
        catch (SQLiteException e)
        {
            Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}
