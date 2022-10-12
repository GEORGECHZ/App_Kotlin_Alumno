package com.example.examlistaalumnos

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.examlistaalumnos.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    //Vincular las vistas con MainActivity
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Variable para recibir extras de otro Activity
        val parExtra = intent.extras
        val msj = parExtra?.getString("mensaje")
        val nom = parExtra?.getString("nombre")
        val cnta = parExtra?.getString("cuenta")
        val crro = parExtra?.getString("correo")
        val imgn = parExtra?.get("imagen")


        // getting the recyclerview by its id
        val recyclerview = findViewById<RecyclerView>(R.id.recyclerview)

        // this creates a vertical layout Manager
        recyclerview.layoutManager = LinearLayoutManager(this)

        // ArrayList of class ItemsViewModel
        val data = ArrayList<ItemsViewModel>()

        // This loop will create 20 Views containing
        // the image with the count of view
        data.add(ItemsViewModel("Ramón Hernández ", "20164277", "ramon2@ucol.mx", "https://assets.pokemon.com/assets/cms2/img/pokedex/full/133.png"))
        data.add(ItemsViewModel("George Chávez ", "20144146", "georgemichael_chavez@ucol.mx", "https://assets.pokemon.com/assets/cms2/img/pokedex/full/111.png"))
        data.add(ItemsViewModel("Miriam Hill ", "20171134", "miriam17@ucol.mx", "https://assets.pokemon.com/assets/cms2/img/pokedex/full/143.png"))
        data.add(ItemsViewModel("Marta López ", "20159864", "marta_lopez@ucol.mx", "https://assets.pokemon.com/assets/cms2/img/pokedex/full/135.png"))
        data.add(ItemsViewModel("Mauricio Islas ", "20182421", "mauricio22@ucol.mx", "https://assets.pokemon.com/assets/cms2/img/pokedex/full/125.png"))

        for (i in 1..10){
            data.add(
                ItemsViewModel(
                    "Gio Cárdenas ",
                    "20172752",
                    "gcardenas6@ucol.mx",
                    "https://assets.pokemon.com/assets/cms2/img/pokedex/full/180.png"
                )
            )
        }


        // This will pass the ArrayList to our Adapter
        val adapter = CustomAdapter(this,data)

        // Setting the Adapter with the recyclerview
        recyclerview.adapter = adapter
        if (!msj.isNullOrEmpty()){
            var nombre = parExtra?.getString("nombre")
            Toast.makeText(this,"$nombre",Toast.LENGTH_SHORT).show()
        }

        if (msj=="Nuevo"){
            val insertIndex: Int = data.count()
            data.add(insertIndex,
                ItemsViewModel(
                    "$nom",
                    "$cnta",
                    "$crro",
                    "$imgn"
                )
            )
            adapter.notifyItemInserted(insertIndex)
        }

        val fab: View = findViewById(R.id.floatingActionButton)
        fab.setOnClickListener {
            val intento1 = Intent(this,MainActivityNuevo::class.java)
            intento1.putExtra("valor1","Hola Mundo")
            startActivity(intento1)
        }

    }
}

