package com.example.arun.ampereplusapp.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.arun.ampereplusapp.R;
import com.example.arun.ampereplusapp.adapter.ApplianceAdapter;
import com.example.arun.ampereplusapp.bean.ApplianceBean;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class Appliances extends AppCompatActivity {

    String device_name, device_id, keyid;
    TextView edevice_name, no_app;
    private SharedPreferences.Editor editor;
    private List<ApplianceBean> list;
    private ApplianceBean bean;
    private SharedPreferences preferences;
    private ListView appliances_list;
    private TextView add_app_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appliances);
        appliances_list = findViewById(R.id.appliances_id_grid);
        edevice_name = findViewById(R.id.device_head_name);
        no_app = findViewById(R.id.no_app);
        add_app_btn = findViewById(R.id.no_app_btn);

        add_app_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it1 = new Intent(getBaseContext(), Devices.class);
                startActivity(it1);
            }
        });
        Intent it = getIntent();
        device_name = it.getStringExtra("device_name");
        device_id = it.getStringExtra("device_id");
        edevice_name.setText(device_name);

        list = new ArrayList<>();

        SharedPreferences preferences = getSharedPreferences("spuser", MODE_PRIVATE);
        keyid = preferences.getString("keyid","");

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("appliance");
        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                ApplianceBean bean = dataSnapshot.getValue(ApplianceBean.class);
                if(keyid.equals(bean.getUser_id()) && device_id.equals(bean.getDevice_id())){
                    if (list.add(bean)) {
                        add_app_btn.setVisibility(View.GONE);
                        no_app.setVisibility(View.GONE);
                        appliances_list.setAdapter(new ApplianceAdapter(getBaseContext(), list));
                    }


                }
                else {
                    System.out.print("no data");
                }

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });





    }
}
