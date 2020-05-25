package com.devproject.miguelfagundez.permissions;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    // Permission code
    private static final int PERMISSION_CODE = 1001;
    int permissionGranted = PackageManager.PERMISSION_GRANTED;

    // Members
    private Button btnShowPermissions;
    private TextView showPermissions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupViews();
        setupListeners();

    }

    private void setupViews() {
        btnShowPermissions = findViewById(R.id.btnShowPermissions);
        showPermissions = findViewById(R.id.tvPermissionText);
    }

    private void setupListeners() {
        btnShowPermissions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkingPermissions()){
                    showPermissions.setText("Permissions Granted. Thank you!!");
                }else{
                    showPermissions.setText("Permissions Not Granted..");
                    requestMyPermissions();
                }
            }
        });
    }


    //*****************************************
    // Checking if permissions were granted
    //*****************************************
    private boolean checkingPermissions() {

        // if both permissions are granted, return true
        if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == permissionGranted
        && checkSelfPermission(Manifest.permission.CAMERA) == permissionGranted)
            return true;

        // if one permission or bot were not granted, then return false
        return false;
    }

    //**************************************
    // if permissions were not granted
    // then I request the permissions again
    //**************************************
    private void requestMyPermissions() {
        if(checkingPermissions()){

            //Permission were granted
            showPermissions.setText("Permissions Granted. Thank you!!");
        }else{

            // Request the permissions
            if (shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE) ||
                    shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)){
                // show a dialog
                new AlertDialog.Builder(this)
                        .setMessage("This app needs these permissions..")
                        .setCancelable(true)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, PERMISSION_CODE);
                            }
                        })
                        .show();
            }else{

                // I show this request again and again
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, PERMISSION_CODE);
            }
        }
    }

    //*****************************************
    // Checking the permission request result
    //*****************************************
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Checking our PERMISSION_CODE
        switch (requestCode){
            case PERMISSION_CODE:
                if (grantResults.length > 0 && grantResults[0] == permissionGranted){
                    // Permission granted
                    showPermissions.setText("Permissions Granted. Thank you!!");
                }else{
                    if(!shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE) ||
                            !shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)){
                        // Permission is not granted (Permanently)
                        new AlertDialog.Builder(this)
                                .setMessage("You have denied permanently these permissions, please go to setting to enable these permissions.")
                                .setCancelable(true)
                                .setPositiveButton("Go to Settings", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        goToApplicationSettings();
                                    }
                                })
                                .setNegativeButton("Cancel", null)
                                .show();

                    }
                }
                break;
        }

    }

    //*************************************************
    // if user denied permanently the permissions,
    //  he should go to setting to granted the permissions
    //*************************************************
    private void goToApplicationSettings() {
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivity(intent);
    }
}
