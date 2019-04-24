package com.example.mapapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.vision.barcode.Barcode;

public class ItemDescriptionActivity extends AppCompatActivity {

    DatabaseHelper db;
    Item item;
    //barcode passed from scanning
    Barcode barcode;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_description);

        db = new DatabaseHelper(this);
        barcode = getIntent().getParcelableExtra("barcode");
        Log.d("myTag","Got the barcode: " + barcode.displayValue);
        item = db.getItem(barcode.displayValue);
        if(item != null)
        {
            Log.d("myTag", "got item");
            Log.d("itemDesc", "item name is: " + item.getName());
            Log.d("itemDesc", "item type is: " + item.getType());
            Log.d("itemDesc", "item symbol is: " + item.getSymbol());
        }

        TextView itemName = (TextView) findViewById(R.id.itemName);
        TextView itemType = (TextView) findViewById(R.id.itemType);
        TextView itemSymbol = (TextView) findViewById(R.id.itemSymbol);


        String sName = item.getName();
        String sType = item.getType();
        String sSymbol = item.getSymbol();
        // Populate fields with extracted properties
        itemName.setText(sName);
        itemType.setText(sType);
        itemSymbol.setText(sSymbol);
    }

    public void loadDB(View View)
    {
        String button_text;
        button_text =((Button)View).getText().toString();
        if(button_text.equals("View All Items")) {
            Intent click = new Intent(this, ListViewActivity.class);
            startActivity(click);
        }
    }
}
