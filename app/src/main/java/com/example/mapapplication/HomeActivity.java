package com.example.mapapplication;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Locale;


public class HomeActivity extends AppCompatActivity {

    private TextView mTextMessage;
    private TextView mRed;
    private TextView mGreen;
    private TextView mBlue;
    DatabaseHelper db = new DatabaseHelper(this);
    private int CAMERA_PERMISSION_CODE = 1;
    private boolean permission = false;
    private String message;
    private String messageRed;
    private String messageGreen;
    private String messageBlue;

    public boolean checkCameraPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {

            // No explanation needed, we can request the permission.
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA},
                    CAMERA_PERMISSION_CODE);
            return false;
        } else {
            return true;
        }

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if(requestCode == CAMERA_PERMISSION_CODE)
        {
            // If request is cancelled, the result arrays are empty.
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                permission = true;
                Intent scan = new Intent(HomeActivity.this, ScanActivity.class);
                startActivity(scan);
            }

        } else {
            // permission denied, boo! Disable the
            // functionality that depends on this permission.
            Log.d("permission","permission denied");
        }
        return;
    }

    //function that takes a string and a number of substrings
    //using spannable makes substrings bold and italic
    public SpannableStringBuilder textBoldFunc(String text,String... textToBold)
    {

        SpannableStringBuilder builder = new SpannableStringBuilder(text);

        for(String textItem: textToBold)
        {
            if(textItem.length() > 0 && !textItem.trim().equals(""))
            {
                String testText = text.toLowerCase();
                String testTextToBold = textItem.toLowerCase();
                int startingIndex = testText.indexOf(testTextToBold);
                int endingIndex = startingIndex + testTextToBold.length();

                if(startingIndex >= 0 && endingIndex >= 0)
                {
                    builder.setSpan(new StyleSpan(Typeface.BOLD_ITALIC),startingIndex,endingIndex,0);
                }
            }
        }

        return builder;
    }

    public SpannableStringBuilder textColorFunc(String text,float red,float green,float blue,String... textToColor)
    {

        SpannableStringBuilder builder = new SpannableStringBuilder(text);

        for(String textItem: textToColor)
        {
            if(textItem.length() > 0 && !textItem.trim().equals(""))
            {
                String testText = text.toLowerCase();
                String testTextToBold = textItem.toLowerCase();
                int startingIndex = testText.indexOf(testTextToBold);
                int endingIndex = startingIndex + testTextToBold.length();

                if(startingIndex >= 0 && endingIndex >= 0)
                {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        builder.setSpan(new ForegroundColorSpan(Color.rgb(red,green,blue)),startingIndex,endingIndex,0);
                    }
                }
            }
        }

        return builder;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        mTextMessage = (TextView) findViewById(R.id.homeMessage);
        mRed = (TextView) findViewById(R.id.messageRed);
        mGreen = (TextView) findViewById(R.id.messageGreen);
        mBlue = (TextView) findViewById(R.id.messageBlue);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        message = "Thank you for using the application! To add a new item click 'Scan Code', to view the map and markers of the bins click 'View Map'!";

        messageRed = "Red marker - General Waste + Recycling Bin.";
        messageGreen = "Green marker - Single Recycling Bin.";
        messageBlue = "Blue marker - Recycling + Liquid Waste Bin.";

        mTextMessage.setText(textBoldFunc(message,"'Scan Code'","'View Map'"));
        mRed.setText(textColorFunc(messageRed,1.0f,0.0f,0.0f,"Red marker"));
        mGreen.setText(textColorFunc(messageGreen,0.0f,1.0f,0.0f,"Green marker"));
        mBlue.setText(textColorFunc(messageBlue,0.0f,0.0f,1.0f,"Blue marker"));


        if (!db.checkBin("0")) {
            //add bins to table of bins in DB
            db.addBin(0, 52.40509540663986, -1.500111222267151, "Oppos. ECG-27", 0.0f);
            db.addBin(1, 52.405215268958145, -1.5002242103219032, "Near ECG-32", 0.0f);
            db.addBin(2, 52.40509540663986, -1.499984823167324, "Behind ECG-24", 0.0f);
            db.addBin(3, 52.40530751772195, -1.4995144307613373, "Near entrance", 0.0f);
            db.addBin(4, 52.40544190193957, -1.499517783522606, "Near ECG-21", 240.0f);
            db.addBin(5, 52.405535786561025, -1.4995496347546577, "Social area", 120.0f);
            db.addBin(6, 52.40566382931747, -1.4996542409062386, "Near ECG-15", 120.0f);
            db.addBin(7, 52.40563233001568, -1.499744430184364, "In front of Starbucks", 240.0f);
            db.addBin(8, 52.40555889973785, -1.4998071268200874, "Exit near Starbucks", 120.0f);
            db.addBin(9, 52.40561903484909, -1.4998775348067284, "Near ECG-01", 120.0f);
            db.addBin(10, 52.405512673372115, -1.5002161636948586, "Near ECG-02", 120.0f);

        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    return true;
                case R.id.navigation_scan:
                    if(checkCameraPermission()){
                        Intent scan = new Intent(HomeActivity.this, ScanActivity.class);
                        startActivity(scan);
                        Log.d("camera","camera permission is true");
                    }
                    return true;
                case R.id.navigation_map:
                    Intent map = new Intent(HomeActivity.this,MapsActivity.class);
                    startActivity(map);
                    return true;
            }
            return false;
        }
    };



}
