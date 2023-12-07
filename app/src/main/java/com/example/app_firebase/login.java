package com.example.app_firebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.auth.FirebaseAuthCredentialsProvider;
import com.google.firebase.ktx.Firebase;


public class login extends AppCompatActivity {
    Button btnEnviar, btnRegistro;
    EditText etUsuarioLogin = findViewById(R.id.etUsuarioLogin);;
    EditText etContrasenaLogin =findViewById(R.id.etUsuarioLogin);

    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnEnviar = findViewById(R.id.btnEnviar);
        btnRegistro = findViewById(R.id.btnRegistro);

        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usuario = etUsuarioLogin.getText().toString();
                String contrasena = etContrasenaLogin.getText().toString();

                if (usuario.isEmpty() || contrasena.isEmpty()) {
                    Toast.makeText(login.this,"Los campos no deben estar vacíos", Toast.LENGTH_SHORT).show();
                } else {
                    loginUser(usuario,contrasena);
                }
            }
        });

        btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(login.this,register.class);
                startActivity(intent);
            }
        });
    }

    private void loginUser(String usuario, String contrasena) {
        mAuth.signInWithEmailAndPassword(usuario,contrasena).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    finish();
                    startActivity(new Intent(login.this,MainActivity.class));
                    Toast.makeText(login.this,"Bienvenido",Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(login.this,"Error",Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(login.this,"Error al autenticar",Toast.LENGTH_SHORT).show();
            }
        });
    }
}