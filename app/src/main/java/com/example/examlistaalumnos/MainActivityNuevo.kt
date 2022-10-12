package com.example.examlistaalumnos

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.examlistaalumnos.databinding.ActivityMainBinding
import com.example.examlistaalumnos.databinding.ActivityMainNuevoBinding

class MainActivityNuevo : AppCompatActivity() {
    //Vincular las vistas con MainActivity
    private lateinit var binding: ActivityMainNuevoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainNuevoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val parExtra = intent.extras
        val miVar = parExtra?.getString("valor1")

        binding.buttonAgregar.setOnClickListener(){
            val tNombre = binding.txtNombre.text
            val tCuenta = binding.txtNoCuenta.text
            val tCorreo = binding.txtCorreo.text
            val tImg = binding.txtImagen.text

            val intento2 = Intent(this,MainActivity::class.java)
            intento2.putExtra("mensaje","Nuevo")
            intento2.putExtra("nombre","$tNombre")
            intento2.putExtra("cuenta","$tCuenta")
            intento2.putExtra("correo","$tCorreo")
            intento2.putExtra("imagen","$tImg")
            startActivity(intento2)
        }

    }
}