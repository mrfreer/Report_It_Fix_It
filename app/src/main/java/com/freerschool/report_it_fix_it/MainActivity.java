package com.freerschool.report_it_fix_it;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class MainActivity extends AppCompatActivity {
    int RC_SIGN_IN = 9001;
    GoogleSignInOptions gso;
    GoogleSignInAccount account;
    SignInButton signInButton;
    GoogleSignInClient mGoogleSignInClient;
    Button sOut;

    @Override
    protected void onStart() {
        super.onStart();
        account = GoogleSignIn.getLastSignedInAccount(this);
        if(account == null){
            Log.v("report_it_logged", "nobody's logged in");
            sOut.setVisibility(View.INVISIBLE);
        }
        else{
            signInButton.setVisibility(View.INVISIBLE);
            Toast.makeText(this, "Welcome " + account.getGivenName().toString(), Toast.LENGTH_LONG).show();
            Log.v("setGoogle", account.getAccount().toString());
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        signInButton = findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_STANDARD);
        sOut = findViewById(R.id.btnSignOut);

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    public void signIn() {
        Log.v("signin_test_test", "do we get here?");
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();

        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    public void fixIt(View view){
        Intent intent = new Intent(this, FixStuff.class);
        intent.putExtra("UserName", account.getEmail());
        startActivity(intent);

    }

    public void viewFixIts(View view){
        Intent intent = new Intent(this, ViewFixIts.class);
        intent.putExtra("UserName", account.getEmail());
        startActivity(intent);
    }

    public void googleLogin(View view){

        Log.v("googleLogin", "get here?");
        signIn();

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data){
        Log.v("gethere??", requestCode + " ?");
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RC_SIGN_IN){
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
             account = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
            signInButton.setVisibility(View.INVISIBLE);
            Toast.makeText(getApplicationContext(), "Welcome " + account.getGivenName() + ".", Toast.LENGTH_LONG).show();

        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("failure_login", "signInResult:failed code=" + e.getStatusCode());
            Toast.makeText(getApplicationContext(), "Couldn't log in", Toast.LENGTH_LONG).show();
        }
    }


    public void signOutGoogle(View view) {

        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        signInButton.setVisibility(View.VISIBLE);
                        sOut.setVisibility(View.INVISIBLE);
                        // [START_EXCLUDE]
                        //updateUI(null);
                        // [END_EXCLUDE]
                    }
                });

        Log.v("testtesttest", "fun" + view.findViewById(0));

    }
    // [END signOut]

    // [START revokeAccess]
    private void revokeAccess() {
        mGoogleSignInClient.revokeAccess()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // [START_EXCLUDE]
                        //updateUI(null);
                        // [END_EXCLUDE]
                    }
                });
    }
}
