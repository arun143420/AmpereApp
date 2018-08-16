package com.example.arun.ampereplusapp.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.arun.ampereplusapp.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ApplianceEdit extends AppCompatActivity {
    String device_id, appliance_name, userid, appliance_id, txt_new_app_name;
    SharedPreferences preferences;
    Button edit_app_btn;
    EditText new_app_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appliance_edit);


        Intent it1 = getIntent();
        device_id = it1.getStringExtra("device_id_edit");
        appliance_name = it1.getStringExtra("appliance_name_edit");
        appliance_id = it1.getStringExtra("appliance_edit_id");
        new_app_name = findViewById(R.id.id_appliance_name_edit);
        new_app_name.setText(appliance_name);
        edit_app_btn = findViewById(R.id.id_add_edit_app);
        edit_app_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txt_new_app_name = new_app_name.getText().toString();


                if (txt_new_app_name.isEmpty()) {

                    new_app_name.setError("Required");

                } else {

                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
                    reference.child("appliance").child(appliance_id).child("appliance_name").setValue(txt_new_app_name);
                    new_app_name.setText("");
                    Toast.makeText(getBaseContext(), "Appliance Name Updated successfully" , Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getBaseContext(),Devices.class));
                    finish();
                }
            }

        });


    }
}
