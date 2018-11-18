package com.android.bignerdranch.shiftmark.Auth;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.bignerdranch.shiftmark.MainActivity;
import com.android.bignerdranch.shiftmark.R;
import com.android.bignerdranch.shiftmark.data.DataBase.DBEditor;
import com.android.bignerdranch.shiftmark.data.ModelMonth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

public class AuthorizeActivity extends AppCompatActivity {
    GoogleSignInOptions gso;
    private GoogleSignInClient mGoogleSignInClient;
    private FirebaseAuth mAuth;

    Button btnGoogle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authorize);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        btnGoogle = findViewById(R.id.btn_google);
        mAuth = FirebaseAuth.getInstance();
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    public void loginGoogle(View view) {
        Intent signIn = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signIn,1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);

                //finish();
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w("ssss", "Google sign in failed", e);
                // ...
                Toast.makeText(this,"Что-то пошло не так",Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            readFromFirebase();
                        } else {
                            btnGoogle.setText("Не вошел");
                        }
                    }
                });
    }

    private void readFromFirebase(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference(MainActivity.REF);
        Log.println(Log.ASSERT,"TAG", "начало метода риддата");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.println(Log.ASSERT,"TAG", "Проверка есть ли данные в базе");
                if(!dataSnapshot.hasChild(FirebaseAuth.getInstance().getUid())) {
                    Log.println(Log.ASSERT,"TAG", "Не успешно");
                    return;
                }
                Log.println(Log.ASSERT,"TAG", "Успешно");
                Gson gson = new Gson();
                DBEditor editor = new DBEditor(getBaseContext());
                for (DataSnapshot ch:dataSnapshot.child(FirebaseAuth.getInstance().getUid()).getChildren()){
                    Log.println(Log.ASSERT,"TAG", "Элемент отдан в базу");
                    ModelMonth m = gson.fromJson(ch.getValue(String.class),ModelMonth.class);
                    editor.addList(m.getDays());
                }
                showDial();
                //onDestroy();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getBaseContext(),databaseError.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
        Log.println(Log.ASSERT,"TAG", "конец метода (недостижим при нормальных условиях)");
    }

    private void showDial(){
        Log.println(Log.ASSERT,"TAG", "Создание диалогового окна");
        DestroyDialog dial = new DestroyDialog();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment prev = getFragmentManager().findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        // Create and show the dialog.
        dial.show(ft,"destroy");
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(mAuth.getCurrentUser()!=null)
        this.finish();
    }

    @Override
    public void onBackPressed() {

    }
}
