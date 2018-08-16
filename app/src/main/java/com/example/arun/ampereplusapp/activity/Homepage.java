package com.example.arun.ampereplusapp.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.example.arun.ampereplusapp.R;

public class Homepage extends AppCompatActivity {
    private SharedPreferences preferences;
    private String keyid, keyemail, keypass;

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        preferences = getSharedPreferences("spuser",MODE_PRIVATE);
        keyid = preferences.getString("keyid","");
        keyemail = preferences.getString("keyemail","");
        keypass = preferences.getString("keypass","");

        if (keyid.isEmpty() || keyemail.isEmpty() || keypass.isEmpty()) {

            (new Handler()).postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(getBaseContext(), Login.class));
                    finish();
                }
            }, 5000);
        }
        else {
                (new Handler()).postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(getBaseContext(), Devices.class));
                    finish();
                }
            }, 1500);
        }
    }


}
