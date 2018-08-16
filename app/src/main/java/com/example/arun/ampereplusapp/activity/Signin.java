package com.example.arun.ampereplusapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.example.arun.ampereplusapp.R;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class Signin extends AppCompatActivity implements View.OnClickListener, GoogleApiClient.OnConnectionFailedListener {



    private GoogleApiClient googleApiClient;
    private SignInButton SignIn;
    private static final int REQ_CODE = 9001;
    private final  static int  RC_SIGN_IN= 1;
    GoogleApiClient mGoogleApiClient;
    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        SignIn = findViewById(R.id.sign_in_button);
        SignIn.setOnClickListener(this);


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

         mGoogleApiClient = new GoogleApiClient.Builder(this)
                 .enableAutoManage(this,this)
                 .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                    .build();



         mAuth = FirebaseAuth.getInstance();
         mAuthListner = new FirebaseAuth.AuthStateListener() {
             @Override
             public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                 if(firebaseAuth.getCurrentUser()!= null){
                     startActivity(new Intent(getBaseContext(),Devices.class));
                 }
             }
         };



    }




    @Override
    public void onClick(View view) {
        switch (view.getId())
        {

            case R.id.sign_in_button:
                signIn();
                break;



        }


    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    private void signIn(){
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }


    private void signOut(){



    }


    private void handleResult(GoogleSignInResult result){

        if(result.isSuccess()){

            GoogleSignInAccount account = result.getSignInAccount();
            String name = account.getDisplayName();
            String email = account.getEmail();
            String id = account.getId();
            firebaseAuthWithGoogle(account);


            updateUI(true);

        }
        else {
            updateUI(false);
        }




    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {

        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("TAG"   , "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(true);
                        } else {
                            // If sign in fails, display a message to the user.
                             Log.w( "  TAG", "signInWithCredential:failure", task.getException());
                            // Snackbar.make(getBaseContext()), "Authentication Failed.", Snackbar.LENGTH_SHORT).show();
                            updateUI(false);
                        }

                        // ...
                    }
                });


    }

    private void updateUI(boolean isLogin){

    if (isLogin){
        startActivity(new Intent(getBaseContext(),Devices.class));

    }
    else {


    }


    }



    @Override
    protected void onStart()
    {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListner);


    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN){
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleResult(result);


        }



    }


}
