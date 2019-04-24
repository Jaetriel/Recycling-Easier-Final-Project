package com.example.mapapplication;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;

public class ScanActivity extends AppCompatActivity {


    public DatabaseHelper db;
    SurfaceView cameraView;
    public boolean isScanned;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);
        db = new DatabaseHelper(this);
        isScanned = false;
        cameraView = (SurfaceView) findViewById(R.id.camera_preview);
        createCameraSource();
    }

    public void createCameraSource() {
        BarcodeDetector barcodeDetector = new BarcodeDetector.Builder(this).build();
        final CameraSource cameraSource = new CameraSource.Builder(this, barcodeDetector)
                .setAutoFocusEnabled(true)
                .setRequestedPreviewSize(1600, 1024)
                .build();

        cameraView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                if (ActivityCompat.checkSelfPermission(ScanActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.


                }
                try {

                    cameraSource.start(cameraView.getHolder());
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {

                cameraSource.stop();
            }
        });
        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {

            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {

                final SparseArray<Barcode> barcodes = detections.getDetectedItems();
                if (barcodes.size() > 0) {

                    Log.d("myTag", "barcodes > 0");
                    //check if item is already in DB
                    //if it is, display information about it
                    if (db.checkItem(barcodes.valueAt(0).displayValue)) {
                        if (!isScanned) {
                            isScanned = true;

                            Log.d("myTag", "Item already in DB with barcode: " + barcodes.valueAt(0).displayValue);
                            Intent intent = new Intent(ScanActivity.this, ItemDescriptionActivity.class);
                            intent.putExtra("barcode", barcodes.valueAt(0)); // get only latest barcode
                            startActivity(intent);
                            finish();

                        }
                    }
                    //if not, go to the add item window
                    else if (!db.checkItem(barcodes.valueAt(0).displayValue)) {
                        if (!isScanned) {
                            isScanned = true;
                            Intent intent = new Intent(ScanActivity.this, AddEntryActivity.class);
                            intent.putExtra("barcode", barcodes.valueAt(0)); // get only latest barcode
                            startActivity(intent);
                            finish();
                            Log.d("myTag", "Need to add item to the DB with barcode: " + barcodes.valueAt(0).displayValue);
                        }
                    }

                }
            }
        });
    }

}
