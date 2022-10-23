package com.example.examlistaalumnos

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.widget.Toast
import com.example.examlistaalumnos.databinding.ActivityMainBinding
import com.example.examlistaalumnos.databinding.ActivityMainNuevoBinding

class MainActivityNuevo : AppCompatActivity() {
    //Vincular las vistas con MainActivity
    private lateinit var binding: ActivityMainNuevoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainNuevoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Abrimos la conexion
        val dbConexion = DBHelperAlumno(this)

        val intento2 = Intent(this,MainActivity::class.java)

        //llamo las condiciones para editar los alumnos
        val parExtra = intent.extras
        val men = parExtra?.getString("mensaje")

        binding.buttonAgregar.setOnClickListener() {
            //Abrimos base de datos para escribir en ella
            val db = dbConexion.writableDatabase

            val tNombre = binding.txtNombre.text.toString()
            val tCuenta = binding.txtNoCuenta.text.toString()
            val tCorreo = binding.txtCorreo.text.toString()
            val tImg = binding.txtImagen.text.toString()

            //Alternativa 1 de insert
            //val sql = "INSERT INTO alumnos (nombre,cuenta,correo,imagen) VALUES ('$tNombre','$tCuenta','$tCorreo','$tImg')"
            //val resultado = db.execSQL(sql)

            //Alternativa 2de insert
            val newReg = ContentValues().apply {
                put(FeedReaderContract.FeedEntry.nombre, tNombre)
                put(FeedReaderContract.FeedEntry.cuenta, tCuenta)
                put(FeedReaderContract.FeedEntry.correo, tCorreo)
                put(FeedReaderContract.FeedEntry.imagen, tImg)
            }

            if (men == "editar"){
                val seleccion = "${FeedReaderContract.FeedEntry.cuenta} LIKE ?"
                val cue = parExtra?.getString("cuenta")
                val argumento = arrayOf("$cue")
                val resultado = db.update(FeedReaderContract.FeedEntry.nombreTabla,newReg,seleccion,argumento)
                Toast.makeText(this, "REGISTRO ACTUALIZADO CON EXITO", Toast.LENGTH_SHORT).show()
                startActivity(intento2)
            } else {
                val resultado = db.insert(FeedReaderContract.FeedEntry.nombreTabla, null, newReg)

                if (resultado.toInt() == -1) {
                    Toast.makeText(this, "ERROR AL INSERTAR", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "REGISTRO INSERTADO CON EXITO", Toast.LENGTH_SHORT).show()
                    startActivity(intento2)
                }
            }
        }


        /*
        val idAlum = parExtra?.getInt("idA")

        binding.buttonAgregar.setOnClickListener(){
            val tNombre = binding.txtNombre.text
            val tCuenta = binding.txtNoCuenta.text
            val tCorreo = binding.txtCorreo.text
            val tImg = binding.txtImagen.text
            val parExtra = intent.extras
            val men = parExtra?.getString("mensaje")

            val intento2 = Intent(this,MainActivity::class.java)
            intento2.putExtra("mensaje","Nuevo$men")
            intento2.putExtra("nombre","$tNombre")
            intento2.putExtra("cuenta","$tCuenta")
            intento2.putExtra("correo","$tCorreo")
            intento2.putExtra("imagen","$tImg")
            intento2.putExtra("idA",idAlum)
            startActivity(intento2)
        }
        */

        if (men=="editar") {
            val nam = parExtra?.getString("nombre")
            val cue = parExtra?.getString("cuenta")
            val cor = parExtra?.getString("correo")
            val ima = parExtra?.getString("imagen")
            binding.txtNombre.text = Editable.Factory.getInstance().newEditable(nam)
            binding.txtNoCuenta.text = Editable.Factory.getInstance().newEditable(cue)
            binding.txtCorreo.text = Editable.Factory.getInstance().newEditable(cor)
            binding.txtImagen.text = Editable.Factory.getInstance().newEditable(ima)
        }

    }
}