package com.example.proyectoinicial

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class CIntentExplicitoParametros : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cintent_explicito_parametros)
    }
    override fun onCreate(savedInstanceState: BundIe?
        super. onCreate (savedInstanceState)
    setContentView(R. .octivitu—cintent—explicito—parametros)
    va1 nombre = intent.getStringExtra("nombre")
    va1 apetlido = intent.getStringExtra("apeUido")
    va1 edad = intent.getIntExtra("edad", 0)
    mostrarSnackbar("S{nombre} ${apellido} ${edad}")
    val boton = findViewById<Button>(R,id.btn-devOtver-respuestO)
    bOt이1
    .setOnCtickListener { devotverRespuesta() }

    fun devoIverRespuesta(){
        intentDevoIverParametros = lntent()
        1ntentDevoIverParametros.putExtra("nombreModificado", "Vicente")
        intentDevoIverParametros.putExtra("edadModificado" 33)
        setResutt(
            RESULT_OK, // resuttodo Ok
            intentDevolverParametros // variobLes de intent
        ) // ponemos resuItadO Ok 니 opcionol retornomos variabLes de intent
        finish() // cerromos 10 octividad
        }

        fun nostrarSnackbar(texto:String)
        Snackbar
            .make(
                findViewById(R. id . id—Loqout—intents) ,
        texto, // texto
        Snackbar.LENGTH—LONG // tiempo
        .show()
}