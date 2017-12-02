package com.example.sauld.appconocemipais.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import com.example.sauld.appconocemipais.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static com.example.sauld.appconocemipais.Util.Util.goToMain;

public class Registro_Activity extends AppCompatActivity {

    private FirebaseAuth myAuth;
    private DatabaseReference myDatabaseReference;
    private FirebaseAuth.AuthStateListener mStateListener;
    private EditText editEmail;
    private EditText editPAss;
    private EditText editApe;
    private EditText editnom;
    private Intent intent;
    private ProgressDialog mProgressDialog;
    private View view;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_);
        inicializar();
        myAuth = FirebaseAuth.getInstance();
        mStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() != null) {
                    startActivity(goToMain(Registro_Activity.this, MainActivity.class));
                //    myAuth.signOut();

                }
            }
        };
        mProgressDialog = new ProgressDialog(this);
        Toolbar toolbar =(Toolbar) findViewById(R.id.toolbarR);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Registrarse");

        findViewById(R.id.btnRegistro).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view = v;
                capturandoDatos();
            }
        });

        findViewById(R.id.btnIngresar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(Registro_Activity.this, Login_activity.class);
                startActivity(intent);
            }
        });
    }

    private void createUser(final String email, final String pass, final String nombre, final String apelli) {
        mProgressDialog.setMessage(getResources().getText(R.string.cargando));
        mProgressDialog.show();
        myAuth.createUserWithEmailAndPassword(email, pass)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            myAuth.signInWithEmailAndPassword(email, pass)
                                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                            myDatabaseReference = FirebaseDatabase.getInstance()
                                                    .getReference().child("usuarios");
                                            FirebaseUser user = myAuth.getCurrentUser();
                                            DatabaseReference usersDB = myDatabaseReference.child(myAuth.getCurrentUser().getUid());
                                            usersDB.child("nombre").setValue(nombre);
                                            usersDB.child("apellido").setValue(apelli);
                                            usersDB.child("email").setValue(email);
                                            usersDB.child("password").setValue(pass);

                                        }
                                    });

                            mProgressDialog.dismiss();
                            Snackbar.make(view,
                                    getResources().getText(R.string.userCreate),
                                    Snackbar.LENGTH_LONG).show();
                        } else {
                            mProgressDialog.dismiss();
                            Snackbar.make(view,
                                    getResources().getText(R.string.error) + task.getException().getMessage(),
                                    Snackbar.LENGTH_LONG).show();

                        }
                    }
                });
    }

    private void inicializar() {
        editEmail =(EditText) findViewById(R.id.editEmailR);
        editPAss =(EditText) findViewById(R.id.editPassR);
        editnom =(EditText) findViewById(R.id.editNom);
        editApe =(EditText) findViewById(R.id.editApell);
    }

    private void capturandoDatos() {
        String email, pass, nombre, apellido;
        if (!editPAss.getText().toString().isEmpty() && !editEmail.getText().toString().isEmpty()
                && !editnom.getText().toString().isEmpty() && !editApe.getText().toString().isEmpty()) {
            email = editEmail.getText().toString();
            pass = editPAss.getText().toString();
            nombre = editnom.getText().toString();
            apellido = editApe.getText().toString();
            createUser(email, pass, nombre, apellido);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        myAuth.addAuthStateListener(mStateListener);

    }

}
