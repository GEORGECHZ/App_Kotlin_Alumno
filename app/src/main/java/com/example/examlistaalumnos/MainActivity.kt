package com.example.examlistaalumnos

import android.content.Intent
import android.os.Bundle
import android.provider.BaseColumns
import android.view.MenuItem
import android.view.View
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.examlistaalumnos.databinding.ActivityMainBinding
import com.google.android.gms.dtdi.core.Extra

class MainActivity : AppCompatActivity() {
    //Vincular las vistas con MainActivity
    private lateinit var binding: ActivityMainBinding

    // ArrayList of class ItemsViewModel
    val data = ArrayList<ItemsViewModel>()

    private lateinit var adapter : CustomAdapter
    var idAlumno: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Establecemos la conexion
        val dbConexion = DBHelperAlumno(this)

        //Abrir base de datos
        val db = dbConexion.readableDatabase

        val cursor = db.rawQuery("SELECT * FROM ${FeedReaderContract.FeedEntry.nombreTabla}",null)

        if (cursor.moveToFirst()){
            do {
                idAlumno = cursor.getInt(cursor.getColumnIndexOrThrow(BaseColumns._ID))
                var itemNom = cursor.getString(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.nombre))
                var itemCue = cursor.getString(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.cuenta))
                var itemCorr = cursor.getString(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.correo))
                var itemImg = cursor.getString(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.imagen))

                data.add(ItemsViewModel(itemNom,itemCue,itemCorr,itemImg))
            }while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        dbConexion.close()

        //Variable para recibir extras de otro Activity
        val parExtra = intent.extras
        val msj = parExtra?.getString("mensaje")
        val nom = parExtra?.getString("nombre")
        val cnta = parExtra?.getString("cuenta")
        val crro = parExtra?.getString("correo")
        val imgn = parExtra?.getString("imagen")


        // getting the recyclerview by its id
        val recyclerview = findViewById<RecyclerView>(R.id.recyclerview)

        // this creates a vertical layout Manager
        recyclerview.layoutManager = LinearLayoutManager(this)


        // This loop will create 20 Views containing
        // the image with the count of view
        //data.add(ItemsViewModel("Ramón Hernández ", "20164277", "ramon2@ucol.mx", "https://images.contentstack.io/v3/assets/blt731acb42bb3d1659/blt64edf2d3729b57c1/5db05fd656458c6b3fc17519/RiotX_ChampionList_kayn.jpg?quality=90&width=250"))
        //data.add(ItemsViewModel("George Chávez ", "20144146", "georgemichael_chavez@ucol.mx", "https://images.contentstack.io/v3/assets/blt731acb42bb3d1659/blt570145160dd39dca/5db05fa8347d1c6baa57be25/RiotX_ChampionList_aatrox.jpg?quality=90&width=250"))
        //data.add(ItemsViewModel("Miriam Hill ", "20171134", "miriam17@ucol.mx", "https://images.contentstack.io/v3/assets/blt731acb42bb3d1659/blta62eaac6ad26a4f5/5db05fe7adc8656c7d24e7ea/RiotX_ChampionList_neeko.jpg?quality=90&width=250"))
        //data.add(ItemsViewModel("Marta López ", "20159864", "marta_lopez@ucol.mx", "https://images.contentstack.io/v3/assets/blt731acb42bb3d1659/blta2cba784d9fad4b8/5db05fce89fb926b491ed817/RiotX_ChampionList_jinx.jpg?quality=90&width=250"))
        //data.add(ItemsViewModel("Mauricio Islas ", "20182421", "mauricio22@ucol.mx", "https://images.contentstack.io/v3/assets/blt731acb42bb3d1659/bltd18587f31803441d/5db060226e8b0c6d038c5cc6/RiotX_ChampionList_zoe.jpg?quality=90&width=250"))

        // This will pass the ArrayList to our Adapter
        adapter = CustomAdapter(this,data)

        // Setting the Adapter with the recyclerview
        recyclerview.adapter = adapter

        adapter.setOnItemClickListener(object :CustomAdapter.ClickListener{
            override fun onItemClick(view: View, position: Int) {
                //Toast.makeText(this@MainActivity,"Click en el item: $position", Toast.LENGTH_LONG).show()
                itemOptiomsMenu(position)
            }
        })

        if (!msj.isNullOrEmpty()){
            var nombre = parExtra?.getString("nombre")
            Toast.makeText(this,"$nombre",Toast.LENGTH_SHORT).show()
        }
        val  updateIndex: Int = intent.getIntExtra("idA",0)

        if (msj=="Nuevonull"){
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
        } else if (msj == "Nuevoeditar"){
            data.set(updateIndex,
                ItemsViewModel(
                    "$nom",
                    "$cnta",
                    "$crro",
                    "$imgn"
                )
            )
            adapter.notifyItemChanged(updateIndex)
        }


        binding.floatingActionButton.setOnClickListener() {
            val intento1 = Intent(this,MainActivityNuevo::class.java)
            //intento1.putExtra("valor1","Hola Mundo")
            startActivity(intento1)
        }

    }

    private fun itemOptiomsMenu(position: Int) {
        val popupMenu = PopupMenu(this,binding.recyclerview[position].findViewById(R.id.textViewOptions))
        popupMenu.inflate(R.menu.options_menu)
        //Para cambiarnos de activity
        val intento2 = Intent(this,MainActivityNuevo::class.java)
        val dbConexion = DBHelperAlumno(this)

        //Abrir base de datos
        val db = dbConexion.writableDatabase
        //Implementar el click en el item
        popupMenu.setOnMenuItemClickListener(object : PopupMenu.OnMenuItemClickListener{
            override fun onMenuItemClick(item: MenuItem?): Boolean {
                when(item?.itemId){
                    R.id.borrar -> {
                        val seleccion = "${FeedReaderContract.FeedEntry.cuenta} LIKE ?"
                        val argumento = arrayOf("${data[position].text1}")
                        val borrar = db.delete(FeedReaderContract.FeedEntry.nombreTabla,seleccion,argumento)
                        data.removeAt(borrar)
                        finish()
                        startActivity(getIntent())
                        return true
                    }
                    R.id.edit -> {
                        //Tomamos los datos del alumno, en la posición de la lista donde hicieron click
                        val nombre = data[position].text
                        val cuenta = data[position].text1
                        val correo = data[position].text2
                        val image = data[position].image
                        //En position tengo el indice del elemento en la lista
                        val idAlum: Int = position
                        intento2.putExtra("mensaje","editar")
                        intento2.putExtra("nombre",nombre)
                        intento2.putExtra("cuenta",cuenta)
                        intento2.putExtra("correo",correo)
                        intento2.putExtra("imagen",image)
                        //Pasamos por extras el idAlum para poder saber cual editar de la lista (ArrayList)
                        intento2.putExtra("idA",idAlum)
                        startActivity(intento2)
                        return true
                    }
                }
                return false
            }
        })
        popupMenu.show()
    }

}

