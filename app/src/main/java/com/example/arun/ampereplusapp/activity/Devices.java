package com.example.arun.ampereplusapp.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.arun.ampereplusapp.R;
import com.example.arun.ampereplusapp.adapter.DeviceAdapter;
import com.example.arun.ampereplusapp.bean.DeviceBean;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class Devices extends AppCompatActivity  {
    private SharedPreferences.Editor editor;
    private List<DeviceBean> list;
    private ListView device_list;
    private String keyid, keyemail, keypass,userid, ist_device_name,ist_device_id_name;
    private ProgressBar spinner;
    private TextView txt, head, head1;
    private ProgressBar bar;
    private EditText ist_device,ist_device_id;
    private Button istaddbtn;
    private DeviceBean bean;
    private SharedPreferences preferences;
    View hr1, hr;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_devices);



        list = new ArrayList<>();

        SharedPreferences preferences = getSharedPreferences("spuser", MODE_PRIVATE);
        keyid = preferences.getString("keyid","");
        keyemail = preferences.getString("keyemail","");
        keypass = preferences.getString("keypass","");
        txt = findViewById(R.id.load_text);
        txt.setVisibility(View.INVISIBLE);
        bar = findViewById(R.id.load_bar);



        head1 = findViewById(R.id.head1_text);
        hr = findViewById(R.id.hr_line);
        ist_device = findViewById(R.id.ist_device_name);
        ist_device_id = findViewById(R.id.ist_device_id);
        istaddbtn = findViewById(R.id.ist_btn);



        head = findViewById(R.id.headr_text);
        hr1 = findViewById(R.id.hr1_line);
        device_list = findViewById(R.id.id_devices);



        head.setVisibility(View.INVISIBLE);
        hr1.setVisibility(View.INVISIBLE);








        preferences = getSharedPreferences("spuser",MODE_PRIVATE);
        userid = preferences.getString("keyid", "");
        istaddbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ist_device_name = ist_device.getText().toString();
                ist_device_id_name = ist_device_id.getText().toString();

                if (ist_device_name.isEmpty() || ist_device_id_name.isEmpty()) {

                    ist_device.setError("Required");
                    ist_device_id.setError("Required");
                } else {


                    bean = new DeviceBean();
                    bean.setDevice_name(ist_device_name);
                    bean.setDevice_id(ist_device_id_name);
                    bean.setUser_id(userid);

                    ist_device.setVisibility(View.GONE);
                    ist_device_id.setVisibility(View.GONE);
                    istaddbtn.setVisibility(View.GONE);
                    head1.setVisibility(View.GONE);
                    hr.setVisibility(View.GONE);

                    head.setVisibility(View.VISIBLE);
                    hr1.setVisibility(View.VISIBLE);



                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("device");
                    bean.setId(reference.push().getKey());
                    reference.child(bean.getId()).setValue(bean)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(getBaseContext(), "First Device Added Successfully.", Toast.LENGTH_SHORT).show();
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


        //retrieve list from firebase
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("device");
        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                DeviceBean bean = dataSnapshot.getValue(DeviceBean.class);
                if(keyid.equals(bean.getUser_id())){

                    if (list.add(bean)) {
                        head.setVisibility(View.VISIBLE);
                        hr1.setVisibility(View.VISIBLE);
                        ist_device.setVisibility(View.GONE);
                        ist_device_id.setVisibility(View.GONE);
                        istaddbtn.setVisibility(View.GONE);
                        head1.setVisibility(View.GONE);
                        hr.setVisibility(View.GONE);
                        bar.setVisibility(View.GONE);
                        txt.setVisibility(View.INVISIBLE);
                        device_list.setAdapter(new DeviceAdapter(getBaseContext(), list));
                    }

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


















    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.mainmenu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.id_create_device:
                startActivity(new Intent(getBaseContext(), CreateDevice.class));
                break;
            case R.id.logout:
                editor = getSharedPreferences("spuser",MODE_PRIVATE).edit();
                editor.putString("keyid","");
                editor.putString("keyname","");
                editor.putString("keyemail","");
                editor.putString("keypass","");
                editor.commit();
                finish();
                startActivity(new Intent(getBaseContext(),Login.class));
                break;

        }
        return super.onOptionsItemSelected(item);
    }
}
