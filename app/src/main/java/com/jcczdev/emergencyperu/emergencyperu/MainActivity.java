package com.jcczdev.emergencyperu.emergencyperu;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.jcczdev.emergencyperu.emergencyperu.adapter.CustomAdapter;

public class MainActivity extends AppCompatActivity {

    GridView gridView;
    private String selectedNumber = "";
    public static final int CALL_PHONE_PERMISSION_REQUEST_CODE = 0x1;

    public static int[] osImages = {
            R.mipmap.bomberos,
            R.mipmap.central_policial,
            R.mipmap.cruz_roja,
            R.mipmap.defensa_civil,
            R.mipmap.samu,
            R.mipmap.violencia_familiar
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final String[] PHONE_NUMBERS = getResources().getStringArray(R.array.emergency_numbers);
        gridView = (GridView) findViewById(R.id.customgrid);
        gridView.setAdapter(new CustomAdapter(this, getResources().getStringArray(R.array.numbers_labels), osImages));
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int itemIndex, long l) {
                Log.e("keypressed", "you pressed : " + itemIndex + " object.");
                Log.e("keypressed", "Phone number : " + PHONE_NUMBERS[itemIndex]);
                selectedNumber = PHONE_NUMBERS[itemIndex];
                askForPermission(Manifest.permission.CALL_PHONE, CALL_PHONE_PERMISSION_REQUEST_CODE);
            }
        });
    }

    private void askForPermission(String permission, int requestCode) {
        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                    Manifest.permission.CALL_PHONE)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                showMessageOKCancel("You need provide CALL PHONE access to make calls", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ActivityCompat.requestPermissions(MainActivity.this,
                                new String[]{Manifest.permission.CALL_PHONE},
                                CALL_PHONE_PERMISSION_REQUEST_CODE);
                    }
                });

            } else {

                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.CALL_PHONE},
                        CALL_PHONE_PERMISSION_REQUEST_CODE);
            }
        } else {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                startActivity(getCallIntent());
            } else {
                askForPermission(Manifest.permission.CALL_PHONE, CALL_PHONE_PERMISSION_REQUEST_CODE);
            }
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        switch (requestCode) {
            case CALL_PHONE_PERMISSION_REQUEST_CODE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                        startActivity(getCallIntent());
                    }
                } else {

                    Log.e("Permission error", "CALL_PHONE permission not granted by User");
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }

    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(MainActivity.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();

    }

    private Intent getCallIntent() {
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        Log.e("keypressed", "Number before calling: " + selectedNumber);
        callIntent.setData(Uri.parse("tel:" + selectedNumber));
        selectedNumber = "";
        Log.e("keypressed", "selectedNumber cleared.");
        return callIntent;
    }
}