package com.example.sauld.appconocemipais.Activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;

import com.example.sauld.appconocemipais.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import static com.example.sauld.appconocemipais.Util.Util.goToMain;

public class Login_activity extends AppCompatActivity {

    private FirebaseAuth myAuth;
    private View view;
    private Intent intent;
    private FirebaseAuth.AuthStateListener mStateListener;
    private ProgressDialog mProgressDialog;
    private EditText editPass, editEmail;
    private TextInputLayout inputName, inputPass;
    private SharedPreferences preference;
    private Switch aSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_activity);
        inicializar();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Iniciar secion");

        preference = getSharedPreferences("Preferences", Context.MODE_PRIVATE);

        mStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() != null) {
                    // Toast.makeText(Login_activity.this, "Estas Logeado", Toast.LENGTH_SHORT).show();

                    startActivity(goToMain(Login_activity.this, MainActivity.class));

                }
            }
        };
        findViewById(R.id.btnIngresar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editEmail.getText().toString();
                String pass = editPass.getText().toString();
                view = v;
                if (validarEdit(email, pass)) {
                    //   goToMain();
                    signin(email, pass);
                }
            }
        });

        findViewById(R.id.btnRegistro).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(Login_activity.this, Registro_Activity.class);
                startActivity(intent);
            }
        });
    }

    private void signin(String email, String pass) {
        mProgressDialog.setMessage(getResources().getText(R.string.cargando));
        mProgressDialog.show();
        myAuth.signInWithEmailAndPassword(email, pass)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            mProgressDialog.dismiss();
                            // Log.d("isSuces", "Estas logeado a" + myAuth.getCurrentUser().getUid());
                            //goToMain();
                            finish();
                        } else {
                            mProgressDialog.dismiss();
                            //  Log.d("TagErr", "Error " + task.getException().getMessage());
                            String error = task.getException().getMessage();
                            Snackbar.make(view,
                                    getResources().getText(R.string.error)
                                            + error,
                                    Snackbar.LENGTH_LONG).show();
                        }
                    }
                });
    }


    private boolean validarEdit(String email, String pass) {
        boolean validar = true;
        if (!validarEmail(email)) {
            inputName.setError("Este campo es obligatorio y deve ser un Email");
            validar = false;
        } else {
            inputName.setErrorEnabled(false);
        }
        if (!validarPsdd(pass)) {
            inputPass.setError("Se necesita mas 6 caracteres");
            validar = false;
        } else {
            inputPass.setErrorEnabled(false);
        }
        return validar;
    }

    private boolean validarEmail(String email) {

        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean validarPsdd(String pass) {

        return !TextUtils.isEmpty(pass) && pass.length() >= 6;
    }

    private void inicializar() {
        aSwitch = (Switch) findViewById(R.id.switch1);
        editEmail = (EditText) findViewById(R.id.editEmail);
        editPass = (EditText) findViewById(R.id.editPass);
        inputName = (TextInputLayout) findViewById(R.id.inputEmail);
        inputPass = (TextInputLayout) findViewById(R.id.inputPass);
        mProgressDialog = new ProgressDialog(Login_activity.this);
        mProgressDialog.setCancelable(false);
        myAuth = FirebaseAuth.getInstance();
    }

//    public void goToMain() {
//        intent = new Intent(Login_activity.this, MainActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        startActivity(intent);
//
//    }


    @Override
    public void onBackPressed() {
        startActivity(goToMain(this, MainActivity.class));

    }

    private void saveOnPreferences(String email, String pass) {

        if (aSwitch.isChecked()) {
            SharedPreferences.Editor editor = preference.edit();
            editor.putString("Email", email);
            editor.putString("Pass", pass);
            //  editor.commit();
            editor.apply();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        myAuth.addAuthStateListener(mStateListener);
    }
}
