package com.example.admin.starbuzz;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class DrinkActivity extends Activity {
public final static String EXTRA_DRINKNO = "drinkNo";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink);
        int drinkNo = (Integer) getIntent().getExtras().get(EXTRA_DRINKNO);
//        Drink drink = Drink.drinks[drinkNo];
        SQLiteOpenHelper starbuzzDatabaseHelper = new StarbuzzDatabaseHelper();
        SQLiteDatabase db = starbuzzDatabaseHelper.getReadableDatabase();
        Cursor cursor = db.query("DRINK", new String [] {"NAME", "DESCRIPTION" , "IMAGE_RESOURCE_ID"}
        ,"_id = ?", new String[] {Integer.toString(drinkNo)}, null, null, null);
        ImageView imageView = (ImageView) findViewById(R.id.photo);
        imageView.setImageResource(drink.getImageResourceId());
        imageView.setContentDescription(drink.getDescription());

        TextView name = (TextView) findViewById(R.id.name);
        name.setText(drink.getName());

        TextView description = (TextView) findViewById(R.id.description);
        description.setText(drink.getDescription());
    }
}
