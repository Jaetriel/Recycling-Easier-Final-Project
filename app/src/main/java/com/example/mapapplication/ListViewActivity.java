package com.example.mapapplication;


import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.vision.barcode.Barcode;


public class ListViewActivity extends AppCompatActivity {

    //displays list view of DB and
    //has option to add entries
    DatabaseHelper db;
    //barcode passed from scanning
    Barcode barcode;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.db_list_view_layout);
        db = new DatabaseHelper(this);
        barcode = getIntent().getParcelableExtra("barcode");
        if(barcode != null)
            Log.d("myTag","Got the barcode: " + barcode.displayValue);



        //code for listview to display the whole DB
        ListView listView = (ListView) findViewById(R.id.listView);
        TextView emptyText = (TextView)findViewById(android.R.id.empty);
        Cursor data = db.getListContents();
        if(data.getCount()!= 0) {
            Log.d("myTag", "query was successful");
        }

        CustomCursorAdapter adapter = new CustomCursorAdapter(this,data);
        listView.setAdapter(adapter);
        Log.d("myTag", "adapter set successfully");
        listView.setEmptyView(emptyText);
        emptyText.setText("No items added yet!");



    }

    public void Click(View view)
    {
        String button_text;
        button_text =((Button)view).getText().toString();
        if(button_text.equals("Add New Entry")) {
            Intent click = new Intent(this, ScanActivity.class);
            startActivity(click);
        }
    }
}
