package com.example.arun.ampereplusapp.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.example.arun.ampereplusapp.R;
import com.example.arun.ampereplusapp.bean.DeviceBean;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreateDevice extends AppCompatActivity {
    public EditText edevice_name, edevice_id, ist_device, ist_device_id;
    private Button add_devicebtn;
    private String device_name, device_id;
    private DeviceBean bean;
    private SharedPreferences preferences;
    private String userid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_device);

        edevice_name = findViewById(R.id.id_devicename);
        edevice_id = findViewById(R.id.id_deviceid);
        add_devicebtn = findViewById(R.id.id_add_device);
        add_devicebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                device_name = edevice_name.getText().toString();
                device_id = edevice_id.getText().toString();
                if (device_name.isEmpty()){
                    edevice_name.setError("required");
                }
                else if(device_id.isEmpty()){
                    edevice_id.setError("required");

                }
                else {

                    preferences = getSharedPreferences("spuser",MODE_PRIVATE);
                    userid = preferences.getString("keyid","");
                    bean = new  DeviceBean();
                    bean.setDevice_name(device_name);
                    bean.setDevice_id(device_id);
                    bean.setUser_id(userid);

                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("device");
                    bean.setId(reference.push().getKey());
                    reference.child(bean.getId()).setValue(bean)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    edevice_id.setText("");
                                    edevice_name.setText("");
                                    startActivity(new Intent(getBaseContext(),Devices.class));
                                    Toast.makeText(getBaseContext(), "Device Added Successfully.", Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getBaseContext(), "Try again.", Toast.LENGTH_SHORT).show();
                                }
                            });


                }
            }
        });
    }
}
