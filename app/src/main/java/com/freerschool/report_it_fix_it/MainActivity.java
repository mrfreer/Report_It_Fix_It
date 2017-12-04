package com.freerschool.report_it_fix_it;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;

public class MainActivity extends AppCompatActivity {

    GoogleSignInOptions gso;
    GoogleSignInAccount account;
    SignInButton signInButton;
    @Override
    protected void onStart() {
        super.onStart();
        account = GoogleSignIn.getLastSignedInAccount(this);
        if(account == null){

        }
        else{
            signInButton.setVisibility(View.INVISIBLE);
            Log.v("setGoogle", account.getAccount().toString());
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        signInButton = findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_STANDARD);
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

    }

    public void fixIt(View view){
        Intent intent = new Intent(this, FixStuff.class);
        startActivity(intent);

    }

    public void viewFixIts(View view){
        Intent intent = new Intent(this, ViewFixIts.class);
        startActivity(intent);
    }

    public void googleLogin(View view){
        int RC_SIGN_IN = 0;
       GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
       Intent signInIntent = mGoogleSignInClient.getSignInIntent();
       startActivityForResult(signInIntent, RC_SIGN_IN);

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        //if(requestCode == RC_SIGN_IN){

        //}
    }
}
