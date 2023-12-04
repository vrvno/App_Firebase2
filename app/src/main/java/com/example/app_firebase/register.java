package com.example.app_firebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class register extends AppCompatActivity {

    Button btnRegistrar;
    String usuario,nombre,apellido,correo,contrasena;
    EditText etUsuario, etNombre, etApellido, etCorreo, etContrasena;
    private FirebaseFirestore mfirestore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mfirestore = FirebaseFirestore.getInstance();

        btnRegistrar = findViewById(R.id.btnRegistrar);
        etUsuario= findViewById(R.id.etUsuario);
        etNombre = findViewById(R.id.etNombre);
        etApellido = findViewById(R.id.etApellido);
        etContrasena = findViewById(R.id.etContrasena);
        etCorreo = findViewById(R.id.etCorreo);



        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usuario = etUsuario.getText().toString();
                nombre = etNombre.getText().toString();
                apellido = etApellido.getText().toString();
                correo = etCorreo.getText().toString();
                contrasena = etContrasena.getText().toString();


                if (TextUtils.isEmpty(usuario) || TextUtils.isEmpty(nombre) || TextUtils.isEmpty(apellido) || TextUtils.isEmpty(contrasena) || TextUtils.isEmpty(correo)) {
                    Toast.makeText(getApplicationContext(), "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show();
                } else if (!isValidEmail(correo)) {
                    Toast.makeText(getApplicationContext(), "Correo electrónico no válido", Toast.LENGTH_SHORT).show();
                } else {
                    // Los datos son válidos, procede con el registro
                    // Puedes enviar los datos al servidor o realizar otras acciones aquí
                    //Toast.makeText(getApplicationContext(),"funciono",Toast.LENGTH_SHORT).show();
                    crearUsuario(usuario,nombre,apellido,correo,contrasena);
                }
            }
        });
    }

    private void crearUsuario(String usuario, String nombre, String apellido, String correo, String contrasena) {
        Map<String, Object> map = new HashMap<>();
        map.put("usuario",usuario);
        map.put("nombre",nombre);
        map.put("apellido",apellido);
        map.put("correo",correo);
        map.put("contrasena",contrasena);
        mfirestore.collection("usuarios").add(map).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Toast.makeText(getApplicationContext(),"Usuario creado exitosamente",Toast.LENGTH_SHORT).show();
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(),"Error al crear usuario",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(emailRegex);
    }
}