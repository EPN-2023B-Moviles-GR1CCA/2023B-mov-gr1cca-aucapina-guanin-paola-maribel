package com.example.pryIIB_cine

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.ComponentActivity
import com.google.firebase.Firebase
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth


class AuthActivity : ComponentActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)


        auth = Firebase.auth

        val editTextEmail = findViewById<EditText>(R.id.editTextEmail)
        val editTextPassword = findViewById<EditText>(R.id.editTextPassword)
        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val txtRegister = findViewById<TextView>(R.id.txtRegister)

        btnLogin.setOnClickListener {
            val email = editTextEmail.text.toString().trim()
            val password = editTextPassword.text.toString().trim()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                signIn(email, password)
            } else {
                // Manejar errores, por ejemplo, campos vacíos.
            }
        }

        txtRegister.setOnClickListener {
            // Redirigir a la pantalla de registro (implementar más adelante).
        }
    }


    private fun signIn(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Inicio de sesión exitoso, puedes redirigir a la próxima actividad.
                    Log.d(TAG, "signInWithEmail:success")
                    startActivity(Intent(this, HomeActivity::class.java))
                } else {
                    // Si falla, muestra un mensaje al usuario.
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    // Maneja el error, muestra un mensaje al usuario, etc.
                }
            }
    }

    companion object {
        private const val TAG = "AuthActivity"
    }
}

