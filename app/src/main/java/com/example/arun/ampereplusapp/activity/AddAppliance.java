package com.example.arun.ampereplusapp.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.arun.ampereplusapp.R;
import com.example.arun.ampereplusapp.bean.ApplianceBean;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddAppliance extends AppCompatActivity {
    private EditText eappliance_name;
    private Button eappliance_btn;
    private String appliance_name, userid, device_id, device_name;
    SharedPreferences preferences;
    ApplianceBean bean;
    private TextView heading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_appliance);
        eappliance_name = findViewById(R.id.id_appliance_name);
        eappliance_btn = findViewById(R.id.id_add_appliance_btn);
        heading = findViewById(R.id.id_device_head);
        Intent it = getIntent();
        device_id = it.getStringExtra("device_id");
        device_name = it.getStringExtra("device_name");
        heading.setText(device_name);
        eappliance_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appliance_name = eappliance_name.getText().toString();
                if (appliance_name.isEmpty()){

                    eappliance_name.setError("required");
                }
                else {



                    preferences = getSharedPreferences("spuser", MODE_PRIVATE);
                    userid = preferences.getString("keyid", "");
                    bean = new ApplianceBean();
                    bean.setAppliance_name(appliance_name);
                    bean.setDevice_id(device_id);
                    bean.setUser_id(userid);

                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("appliance");
                    bean.setId(reference.push().getKey());
                    reference.child(bean.getId()).setValue(bean)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            eappliance_name.setText("");
                            Toast.makeText(getBaseContext(), "Appliance Added Successfully in "+device_name, Toast.LENGTH_SHORT).show();

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getBaseContext(), "Try again.", Toast.LENGTH_SHORT).show();

                        }
                    }) ;

                }
            }
        });



    }
}
