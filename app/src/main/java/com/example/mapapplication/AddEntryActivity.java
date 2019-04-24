package com.example.mapapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.barcode.Barcode;

import java.util.ArrayList;
import java.util.List;

public class AddEntryActivity extends Activity implements OnItemSelectedListener {

    Spinner typeSpinner;
    Spinner symbolSpinner;
    Barcode barcode;
    EditText itemName;// name of the item
    String itemType;
    String itemSymbol;
    DatabaseHelper db = new DatabaseHelper(this);
    Button addButton;
    private TextView barcodeResult;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_entry);

        symbolSpinner = (Spinner) findViewById(R.id.symbol_spinner);
        //spinner for selecting package material
        typeSpinner = (Spinner) findViewById(R.id.type_spinner);
        //list of all materials to be added to a spinner
        List<String> types = new ArrayList<String>();
        types.add(Item.ItemType.METAL.toString());
        types.add(Item.ItemType.GLASS.toString());
        types.add(Item.ItemType.PAPER.toString());
        types.add(Item.ItemType.PLASTIC.toString());
        types.add(Item.ItemType.MIXED.toString());
        ArrayAdapter<String> typeAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,types);
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(typeAdapter);

        //spinner for selecting recycling options
        List<String> symbols = new ArrayList<String>();
        //list of all the symbols to be added to a spinner
        symbols.add(Item.ItemSymbol.WIDELY.toString());
        symbols.add(Item.ItemSymbol.CHECK.toString());
        symbols.add(Item.ItemSymbol.NONRECYCLABLE.toString());
        ArrayAdapter<String> symbolAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,symbols);
        symbolAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        symbolSpinner.setAdapter(symbolAdapter);


        symbolSpinner.setOnItemSelectedListener(this);
        typeSpinner.setOnItemSelectedListener(this);
        itemName = (EditText) findViewById(R.id.itemName);
        addButton = (Button) findViewById(R.id.addButton);
        barcode = getIntent().getParcelableExtra("barcode");

        //sets the first position as default value if spinner isn't clicked
        typeSpinner.setSelection(0);
        symbolSpinner.setSelection(0);

    }



    //takes the barcode+name+values from spinners and makes an entry to the DB
    public void addEntry(View view)
    {
        String itemID = barcode.displayValue;
        Log.d("myTag", "Barcode to add is: " + itemID);
        String name = itemName.getText().toString();
        db.addItem(itemID,name,itemType,itemSymbol);
        Toast.makeText(AddEntryActivity.this,"Entry added successfully",
                Toast.LENGTH_LONG).show();
        Intent back = new Intent(this, HomeActivity.class);
        startActivity(back);
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        if(parent.getId() == R.id.type_spinner)
        {
            itemType = parent.getSelectedItem().toString();
        }
        else if(parent.getId() == R.id.symbol_spinner)
        {
            parent.setSelection(0);
            itemSymbol = parent.getSelectedItem().toString();
        }
//test
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {


    }
}
