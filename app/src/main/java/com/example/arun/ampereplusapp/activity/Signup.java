package com.example.arun.ampereplusapp.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.arun.ampereplusapp.R;
import com.example.arun.ampereplusapp.bean.UserBean;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Signup extends AppCompatActivity {

    private Button regbtn;
    private EditText eusername, eemail, econtact, epass;
    private String username, email, contact, pass;
    private UserBean bean;
    private TextView login;
    private ProgressBar signinbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        login = findViewById(R.id.idlogintxt);

        eusername = findViewById(R.id.idusername);
        eemail = findViewById(R.id.idemail);
        econtact = findViewById(R.id.idphone);
        epass = findViewById(R.id.idpass);
        regbtn = findViewById(R.id.idsignup);
        signinbar = findViewById(R.id.sign_bar);
        signinbar.setVisibility(View.INVISIBLE);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getBaseContext(), Login.class));
            }
        });
        regbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                username = eusername.getText().toString();
                pass = epass.getText().toString();
                email = eemail.getText().toString();
                contact = econtact.getText().toString();

                if (username.isEmpty()) {
                    eusername.setError("required");


                } else if (pass.isEmpty()) {
                    epass.setError("required");


                } else if (email.isEmpty()) {
                    eemail.setError("required");


                } else if (contact.isEmpty()) {
                    econtact.setError("required");

                } else if (pass.length() < 8) {
                    epass.setError("Must be 8 Characters long");

                } else if (contact.length() < 10) {
                    econtact.setError("Must be 10 Characters long");

                }
                else {
                    signinbar.setVisibility(View.VISIBLE);
                    bean = new UserBean();
                    bean.setUsername(username);
                    bean.setContact(contact);
                    bean.setEmail(email);
                    bean.setPass(pass);

                    FirebaseAuth auth = FirebaseAuth.getInstance();
                    auth.createUserWithEmailAndPassword(email, pass)
                            .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                @Override
                                public void onSuccess(AuthResult authResult) {
                                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("user");
                                    bean.setId(reference.push().getKey());

                                    reference.child(bean.getId()).setValue(bean)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    Toast.makeText(getBaseContext(), "you're successfully registered.", Toast.LENGTH_SHORT).show();
                                                    startActivity(new Intent(getBaseContext(), Login.class));
                                                    finish();
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {

                                                }
                                            });

                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getBaseContext(), "Email Already Registred.", Toast.LENGTH_SHORT).show();
                                    signinbar.setVisibility(View.INVISIBLE);
                                }
                            });


                }
            }
        });
    }
}
