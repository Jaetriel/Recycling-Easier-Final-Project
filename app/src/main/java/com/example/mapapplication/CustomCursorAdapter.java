package com.example.mapapplication;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

public class CustomCursorAdapter extends CursorAdapter {



    public CustomCursorAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
        Log.d("myTag","cursor initialized");
    }


    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        // Find fields to populate in inflated template
        TextView itemName = (TextView) view.findViewById(R.id.itemName);
        TextView itemType = (TextView) view.findViewById(R.id.itemType);
        TextView itemSymbol = (TextView) view.findViewById(R.id.itemSymbol);
        // Extract properties from cursor
        String sID = cursor.getString(cursor.getColumnIndex("_id"));
        String sName = cursor.getString(cursor.getColumnIndex("Name"));
        String sType = cursor.getString(cursor.getColumnIndex("Type"));
        String sSymbol = cursor.getString(cursor.getColumnIndex("Symbol"));
        // Populate fields with extracted properties
        itemName.setText(sName);
        itemType.setText(sType);
        itemSymbol.setText(sSymbol);
        Log.d("myTag","bindView completed");
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.custom_list_row_item, parent, false);
    }
}
