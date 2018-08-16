package com.example.arun.ampereplusapp.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Login extends AppCompatActivity {

    private EditText eemail, epass;
    private Button loginbtn;
    private String email, pass;
    private UserBean bean;
    private SharedPreferences.Editor editor;
    private TextView signup;
    private ProgressBar loginbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        eemail = findViewById(R.id.idlogemail);
        epass = findViewById(R.id.idlogpass);
        loginbtn = findViewById(R.id.idloginbtn);
        signup = findViewById(R.id.idsignuptxt);
        loginbar = findViewById(R.id.login_bar);
        loginbar.setVisibility(View.INVISIBLE);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getBaseContext(), Signup.class));
            }
        });
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = eemail.getText().toString();
                pass = epass.getText().toString();

                if (email.isEmpty()) {
                    eemail.setError("required");

                } else if (pass.isEmpty()) {
                    epass.setError("required");

                }  else {
                    loginbar.setVisibility(View.VISIBLE);
                    FirebaseAuth auth = FirebaseAuth.getInstance();
                    auth.signInWithEmailAndPassword(email, pass)
                            .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                @Override
                                public void onSuccess(AuthResult authResult) {
                                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("user");
                                    reference.addChildEventListener(new ChildEventListener() {
                                        @Override
                                        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                                            bean = dataSnapshot.getValue(UserBean.class);
                                            if (email.equals(bean.getEmail()) && pass.equals(bean.getPass())) {
                                                editor = getSharedPreferences("spuser", Context.MODE_PRIVATE).edit();
                                                editor.putString("keyid", bean.getId());
                                                editor.putString("keyemail", bean.getEmail());
                                                editor.putString("keypass", bean.getPass());
                                                editor.commit();

                                                startActivity(new Intent(getBaseContext(), Devices.class));
                                                finish();
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
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    loginbar.setVisibility(View.INVISIBLE);
                                    Toast.makeText(getBaseContext(), "Email/Password Incorrect.", Toast.LENGTH_SHORT).show();

                                }
                            });


                }

            }
        });

    }
}
